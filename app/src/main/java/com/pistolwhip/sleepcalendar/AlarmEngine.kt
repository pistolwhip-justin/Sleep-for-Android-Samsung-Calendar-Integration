package com.pistolwhip.sleepcalendar

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.AlarmClock
import android.provider.CalendarContract
import java.util.Calendar

data class AlarmSnapshot(val scheduledAtMillis: Long, val message: String?, val vibrate: Boolean?)
sealed class EvaluationResult {
    data class Ready(val targetMillis: Long, val intent: Intent) : EvaluationResult()
    data class NoAction(val message: String) : EvaluationResult()
}

class AlarmEngine(private val context: Context) {
    fun evaluate(): EvaluationResult {
        val eventStart = firstTimedEventToday() ?: return EvaluationResult.NoAction("No timed calendar event was found today.")
        val target = eventStart - 135L * 60L * 1000L
        val existing = readNextAlarm() ?: return EvaluationResult.NoAction("Could not reliably read the next enabled Sleep as Android alarm. No alarm was created.")
        val decision = AlarmDecision.evaluate(existing.scheduledAtMillis, target, System.currentTimeMillis())
        if (!decision.shouldCreate) return EvaluationResult.NoAction(decision.reason)
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            setPackage(SLEEP_PACKAGE)
            putExtra(AlarmClock.EXTRA_HOUR, hourOf(target))
            putExtra(AlarmClock.EXTRA_MINUTES, minuteOf(target))
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
            existing.message?.takeIf { it.isNotBlank() }?.let { putExtra(AlarmClock.EXTRA_MESSAGE, it) }
            existing.vibrate?.let { putExtra(AlarmClock.EXTRA_VIBRATE, it) }
        }
        if (intent.resolveActivity(context.packageManager) == null) return EvaluationResult.NoAction("Sleep as Android did not expose ACTION_SET_ALARM. No alarm was created.")
        return EvaluationResult.Ready(target, intent)
    }

    private fun firstTimedEventToday(): Long? {
        val start = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        val end = (start.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, 1) }
        val uri = CalendarContract.Instances.CONTENT_URI.buildUpon().appendPath(start.timeInMillis.toString()).appendPath(end.timeInMillis.toString()).build()
        val projection = arrayOf(CalendarContract.Instances.BEGIN, CalendarContract.Instances.ALL_DAY)
        return context.contentResolver.query(uri, projection, null, null, CalendarContract.Instances.BEGIN + " ASC")?.use { cursor ->
            while (cursor.moveToNext()) {
                val allDay = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.Instances.ALL_DAY)) != 0
                if (!allDay) return@use cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Instances.BEGIN))
            }
            null
        }
    }

    private fun readNextAlarm(): AlarmSnapshot? {
        val uri = Uri.parse("content://com.urbandroid.sleep.alarmclock/alarm")
        val projection = arrayOf("enabled", "alarmtime", "message", "vibrate")
        return try {
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                var best: AlarmSnapshot? = null
                while (cursor.moveToNext()) {
                    val enabledIndex = cursor.getColumnIndex("enabled")
                    if (enabledIndex >= 0 && cursor.getInt(enabledIndex) == 0) continue
                    val timeIndex = cursor.getColumnIndex("alarmtime")
                    if (timeIndex < 0) return@use null
                    var alarmTime = cursor.getLong(timeIndex)
                    if (alarmTime in 1..999_999_999_999L) alarmTime *= 1000L
                    if (alarmTime <= System.currentTimeMillis()) continue
                    val messageIndex = cursor.getColumnIndex("message")
                    val vibrateIndex = cursor.getColumnIndex("vibrate")
                    val snapshot = AlarmSnapshot(
                        alarmTime,
                        if (messageIndex >= 0 && !cursor.isNull(messageIndex)) cursor.getString(messageIndex) else null,
                        if (vibrateIndex >= 0 && !cursor.isNull(vibrateIndex)) cursor.getInt(vibrateIndex) != 0 else null
                    )
                    if (best == null || snapshot.scheduledAtMillis < best.scheduledAtMillis) best = snapshot
                }
                best
            }
        } catch (_: SecurityException) { null } catch (_: IllegalArgumentException) { null }
    }
    private fun hourOf(time: Long): Int = Calendar.getInstance().apply { timeInMillis = time }.get(Calendar.HOUR_OF_DAY)
    private fun minuteOf(time: Long): Int = Calendar.getInstance().apply { timeInMillis = time }.get(Calendar.MINUTE)
    companion object { private const val SLEEP_PACKAGE = "com.urbandroid.sleep" }
}
