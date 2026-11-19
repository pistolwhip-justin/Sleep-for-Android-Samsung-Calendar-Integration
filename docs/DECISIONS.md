# 🧠 Technical Decisions & Research

**Updated:** 2026-11-19

## DEC-001 — Do not modify existing alarms directly
**Status:** Superseded for Phase 1 implementation path; retained as technical constraint.

Sleep as Android documents an alarm Content Provider at content://com.urbandroid.sleep.alarmclock/alarm with CRUD operations. Third-party write access remains ambiguous: the official API documentation is not explicit about the write permission, while a 2025 developer report says writes require com.urbandroid.sleep.WRITE, reportedly signature-protected. No direct provider update will be attempted.

Evidence:
- [Sleep as Android Content Provider API](https://sleep.urbandroid.org/docs/devs/content_provider_api.html)
- [Developer report: write permission](https://forum.urbandroid.org/t/update-documentation-regarding-the-content-provider-api/14766)
- [Sleep as Android Intent API](https://sleep.urbandroid.org/docs/devs/intent_api.html)

## DEC-005 — Create a new earlier alarm instead of modifying the existing one
**Status:** Accepted for Phase 1 test implementation; device verification pending.

Use Android's standard AlarmClock.ACTION_SET_ALARM integration, which Sleep as Android has publicly indicated it supports. The app reads the next enabled alarm via the documented provider, compares its scheduled time with the calendar-derived target, and requests a new alarm only when the existing one is later.

The original alarm remains untouched. A created alarm may coexist with it. Do not assume that the action edits/replaces an existing alarm.

**Duplication policy**
- Minimum: allow Sleep as Android to apply its own defaults for a newly created alarm.
- Best effort: copy repeat days, message/label, and vibration only if reliably available from the read provider and supported by Android's intent extras.
- Do not claim exact duplication of ringtone, smart-wake window, snooze, or proprietary configuration.
- The receiver's UI/confirmation must remain visible; no silent/background alarm creation.
- If the next alarm cannot be read reliably, fail without creating a new alarm.

References:
- [Android AlarmClock API](https://developer.android.com/reference/android/provider/AlarmClock)
- [Sleep as Android Tasker/automation forum discussion](https://forum.urbandroid.org/t/tasker-better-alarm-creation-deletion/5063)
- [Sleep as Android Content Provider API](https://sleep.urbandroid.org/docs/devs/content_provider_api.html)

## DEC-002 — Calendar source
**Accepted:** Read instances from Android Calendar Provider rather than Samsung's private database. Recurring events are expanded as instances; inspect the all-day property.
- [Android Calendar Provider](https://developer.android.com/identity/providers/calendar-provider)
- [CalendarContract.Instances](https://developer.android.com/reference/android/provider/CalendarContract.Instances)

## DEC-003 — Fixed adjustment rule
**Accepted:** Ignore all-day events; select earliest timed event for current local day; target is 135 minutes before its start. Only request a new alarm when the next enabled alarm is later than target.

## DEC-004 — Disable behavior
**Accepted:** Tile off stops future evaluations but does not remove an already created alarm.
