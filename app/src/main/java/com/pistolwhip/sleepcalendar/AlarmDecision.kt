package com.pistolwhip.sleepcalendar

data class AlarmDecision(val shouldCreate: Boolean, val reason: String)
object AlarmDecision {
    fun evaluate(existingAlarmMillis: Long, targetAlarmMillis: Long, nowMillis: Long): AlarmDecision {
        if (existingAlarmMillis <= 0L) return AlarmDecision(false, "The next enabled Sleep as Android alarm could not be read.")
        if (targetAlarmMillis <= nowMillis) return AlarmDecision(false, "The calculated target time has already passed.")
        if (existingAlarmMillis <= targetAlarmMillis) return AlarmDecision(false, "The existing alarm is already at or earlier than the target.")
        return AlarmDecision(true, "A new earlier alarm can be requested.")
    }
}
