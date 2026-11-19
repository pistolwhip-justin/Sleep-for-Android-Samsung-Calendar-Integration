package com.pistolwhip.sleepcalendar

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AlarmDecisionTest {
    private val now = 1_800_000_000_000L
    private val target = now + 3_600_000L
    @Test fun createsWhenExistingAlarmIsLater() { assertTrue(AlarmDecision.evaluate(target + 1000L, target, now).shouldCreate) }
    @Test fun leavesEarlierAlarmAlone() { assertFalse(AlarmDecision.evaluate(target - 1000L, target, now).shouldCreate) }
    @Test fun leavesEqualAlarmAlone() { assertFalse(AlarmDecision.evaluate(target, target, now).shouldCreate) }
    @Test fun rejectsPastTarget() { assertFalse(AlarmDecision.evaluate(target + 1000L, now - 1L, now).shouldCreate) }
    @Test fun rejectsUnreadableAlarm() { assertFalse(AlarmDecision.evaluate(0L, target, now).shouldCreate) }
}
