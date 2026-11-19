package com.pistolwhip.sleepcalendar

data class AlarmDecisionResult(val shouldCreate: Boolean, val reason: String)
object AlarmDecision {
    fun evaluate(existingAlarmMillis: Long, targetAlarmMillis: Long, nowMillis: Long): AlarmDecisionResult {
        if (existingAlarmMillis <= 0L) return AlarmDecisionResult(false, "The next enabled Sleep as Android alarm could not be read.")
        if (targetAlarmMillis <= nowMillis) return AlarmDecisionResult(false, "The calculated target time has already passed.")
        if (existingAlarmMillis <= targetAlarmMillis) return AlarmDecisionResult(false, "The existing alarm is already at or earlier than the target.")
        return AlarmDecisionResult(true, "A new earlier alarm can be requested.")
    }
}
