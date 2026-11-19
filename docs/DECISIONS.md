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

Use Android's standard AlarmClock.ACTION_SET_ALARM integration, addressed to the Sleep as Android package. The app reads the next enabled alarm via the documented provider, compares its scheduled time with the calendar-derived target, and requests a new alarm only when the existing one is later.

The original alarm remains untouched. A created alarm may coexist with it. Do not assume that the action edits/replaces an existing alarm.

**Duplication policy**
- Minimum: allow Sleep as Android to apply its own defaults for a newly created alarm.
- v0.1.1 best-effort copies message and vibration only when those values are readable.
- Repeat-day mapping is deferred until the provider's day-of-week encoding is confirmed.
- Do not claim exact duplication of ringtone, smart-wake window, snooze, or proprietary configuration.
- The receiver's UI/confirmation remains visible; no silent/background alarm creation.
- If the next alarm cannot be read reliably, fail without creating a new alarm.

References:
- [Android AlarmClock API](https://developer.android.com/reference/android/provider/AlarmClock)
- [Sleep as Android Tasker/automation forum discussion](https://forum.urbandroid.org/t/tasker-better-alarm-creation-deletion/5063)
- [Sleep as Android Content Provider API](https://sleep.urbandroid.org/docs/devs/content_provider_api.html)

## DEC-006 — v0.1.1 implementation and release boundary
**Status:** Implemented as an experimental test build; not Phase 1 complete.

- App ID: com.pistolwhip.sleepcalendar; system-visible label: Sleep Calendar Alarm.
- Quick Settings tile persists enabled/disabled state and opens MainActivity. Evaluation is currently manual from the app button.
- Calendar lookup uses CalendarContract.Instances and excludes all-day instances.
- Alarm comparison uses the provider's alarmtime value; seconds are normalized to milliseconds when the numeric value is below the millisecond epoch range. This assumption must be verified on-device.
- ACTION_SET_ALARM is package-restricted to com.urbandroid.sleep and leaves the receiver's UI visible.
- CI: GitHub Actions build succeeded; five AlarmDecision unit tests passed.
- Release: v0.1.1 debug-signed prerelease, built from commit f6530554522724e96c5b36b9d84e504dc26f7429; tag points directly to that commit. APK SHA-256: 400ab6d4c1a2481ffad6601625bae2636d72da8b296a73c60b58a94f6d6d3e9c.
- Not verified: installation, provider read permission, alarmtime semantics, Sleep as Android intent handling, resulting defaults, duplicate behavior, or original-alarm preservation on physical hardware.

## DEC-002 — Calendar source
**Accepted:** Read instances from Android Calendar Provider rather than Samsung's private database. Recurring events are expanded as instances; inspect the all-day property.
- [Android Calendar Provider](https://developer.android.com/identity/providers/calendar-provider)
- [CalendarContract.Instances](https://developer.android.com/reference/android/provider/CalendarContract.Instances)

## DEC-003 — Fixed adjustment rule
**Accepted:** Ignore all-day events; select earliest timed event for current local day; target is 135 minutes before its start. Only request a new alarm when the next enabled alarm is later than target.

## DEC-004 — Disable behavior
**Accepted:** Tile off stops future evaluations but does not remove an already created alarm.
