# 🧠 Technical Decisions & Research

**Updated:** 2026-11-19

## DEC-001 — Alarm write access is a release blocker
**Status:** Open blocker. **Decision:** Do not claim or release alarm modification until a supported third-party write method is verified.

Sleep as Android documents an alarm provider at content://com.urbandroid.sleep.alarmclock/alarm and says it supports CRUD. Its documentation specifies com.urbandroid.sleep.READ, but does not clearly explain write access. A developer report dated 2025-08-22 says write operations fail unless the caller has com.urbandroid.sleep.WRITE, a signature-protected permission granted only to apps signed with Sleep as Android's key. A third-party app cannot assume it can obtain that permission.

**Evidence**
- [Sleep as Android Content Provider API](https://sleep.urbandroid.org/docs/devs/content_provider_api.html)
- [Developer report: write permission is signature-protected](https://forum.urbandroid.org/t/update-documentation-regarding-the-content-provider-api/14766)
- [Sleep as Android Intent API](https://sleep.urbandroid.org/docs/devs/intent_api.html)

**Alternatives**
1. Direct provider update: blocked unless vendor grants supported write access.
2. Public Intent API: documented actions include tracking, disable-by-label, snooze and dismiss; no documented set-existing-alarm-time action.
3. Android ACTION_SET_ALARM: may create/enable a system alarm; does not guarantee modifying the existing Sleep as Android alarm and violates preservation requirement.
4. Accessibility automation: rejected as fragile, broad-permission, and undocumented.

**Next action:** Ask Urbandroid whether a public API, permission grant, or supported broadcast exists to change an existing alarm's next occurrence while preserving alarm configuration. Until then, do not implement a workaround or publish a functional APK.

## DEC-002 — Calendar source
**Accepted:** Read instances from Android Calendar Provider rather than Samsung's private database. Recurring events are expanded as instances; inspect the all-day property.
- [Android Calendar Provider](https://developer.android.com/identity/providers/calendar-provider)
- [CalendarContract.Instances](https://developer.android.com/reference/android/provider/CalendarContract.Instances)

## DEC-003 — Fixed adjustment rule
**Accepted:** Ignore all-day events; select earliest timed event for current local day; target is 135 minutes before its start. Move only the next enabled Sleep as Android alarm and only when its current time is later than target.

## DEC-004 — Disable behavior
**Accepted:** Tile off stops future automatic modifications but does not restore a previous alarm time, avoiding overwriting a user's later manual change.
