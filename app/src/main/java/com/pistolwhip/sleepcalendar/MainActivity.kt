package com.pistolwhip.sleepcalendar

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var status: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER_HORIZONTAL; setPadding(32, 48, 32, 32) }
        val title = TextView(this).apply { text = "Sleep Calendar Alarm"; textSize = 24f }
        status = TextView(this).apply {
            text = "Reads today's first timed calendar event and requests a new earlier Sleep as Android alarm. The original alarm is never edited or deleted."
            textSize = 16f; setPadding(0, 24, 0, 24)
        }
        val evaluate = Button(this).apply { text = "Evaluate calendar and alarm"; setOnClickListener { evaluateNow() } }
        val enable = Button(this).apply {
            text = "Enable Quick Settings automation"
            setOnClickListener { getSharedPreferences(PREFS, MODE_PRIVATE).edit().putBoolean(ENABLED, true).apply(); status.text = "Enabled. Add the Sleep Calendar Alarm tile to Quick Settings, then tap it to evaluate." }
        }
        val disable = Button(this).apply {
            text = "Disable automation"
            setOnClickListener { getSharedPreferences(PREFS, MODE_PRIVATE).edit().putBoolean(ENABLED, false).apply(); status.text = "Disabled. Previously created alarms are not removed." }
        }
        layout.addView(title); layout.addView(status); layout.addView(evaluate); layout.addView(enable); layout.addView(disable)
        setContentView(layout)
        if (checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) requestPermissions(arrayOf(Manifest.permission.READ_CALENDAR), REQUEST_CALENDAR)
    }
    private fun evaluateNow() {
        if (checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CALENDAR), REQUEST_CALENDAR); status.text = "Calendar permission is required."; return
        }
        try {
            when (val result = AlarmEngine(this).evaluate()) {
                is EvaluationResult.NoAction -> status.text = result.message
                is EvaluationResult.Ready -> {
                    val time = java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(java.util.Date(result.targetMillis))
                    status.text = "Requesting a new Sleep as Android alarm for " + time + ". Confirm it in Sleep as Android. The original remains unchanged."
                    startActivity(result.intent)
                }
            }
        } catch (_: SecurityException) {
            status.text = "Calendar or Sleep as Android alarm read permission denied. No alarm was created."
        } catch (e: Exception) {
            status.text = "Evaluation failed safely: " + e.javaClass.simpleName + ". No alarm was created."
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALENDAR && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) Toast.makeText(this, "Calendar access granted. Tap Evaluate to continue.", Toast.LENGTH_SHORT).show()
        else if (requestCode == REQUEST_CALENDAR) status.text = "Calendar permission denied. No alarm was created."
    }
    companion object { const val PREFS = "sleep_calendar_alarm"; const val ENABLED = "enabled"; private const val REQUEST_CALENDAR = 410 }
}
