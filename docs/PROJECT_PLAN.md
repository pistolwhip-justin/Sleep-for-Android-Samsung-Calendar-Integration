# 🗺️ Project Plan

**Updated:** 2026-11-19 | **Protocol:** 1.1.7

## 🎯 Objective
A standalone Android companion reads events from Android Calendar Provider (including Samsung Calendar) and requests an earlier Sleep as Android alarm. No Tasker or Google Calendar dependency.

## Phase 1 — Quick Settings + create-earlier-alarm request

**Target behavior**
- One Quick Settings tile enables/disables automatic evaluation.
- Select the earliest timed event today; exclude all-day events.
- Target = event start minus exactly 135 minutes.
- Read the next enabled Sleep as Android alarm using its documented Content Provider.
- If current alarm time is at or earlier than target, do nothing.
- If current alarm is later, request a new alarm through Android ACTION_SET_ALARM.
- Never edit, delete, or disable the existing alarm.
- Use Sleep as Android's new-alarm defaults as the minimum supported behavior.
- Best-effort copy only metadata that is reliably readable and supported by Android intent extras. Exact duplication of repeat days, ringtone, smart-wake, snooze, and proprietary preferences is not guaranteed.

**Implemented in v0.1.1**
- Android application shell and persistent Quick Settings enabled/disabled tile.
- Runtime Calendar permission request and CalendarContract.Instances query for today's timed events.
- Fixed 135-minute target calculation.
- Read-only query of Sleep as Android's alarm Content Provider and earlier-only guard.
- ACTION_SET_ALARM request explicitly addressed to Sleep as Android; receiver confirmation UI remains visible.
- Existing alarm is never written, deleted, or disabled.
- Best-effort message and vibration extras when provider values are available.
- Debug APK built through GitHub Actions; five unit tests passed.

**Not yet implemented / verified**
- The tile currently toggles the enabled state and opens the app; the user must tap **Evaluate calendar and alarm**. Tile-triggered automatic evaluation is not implemented yet.
- Repeat-day mapping is not implemented because the provider's day-of-week encoding has not been verified.
- Physical-device read permission, ACTION_SET_ALARM receiver behavior, defaults, and duplicate behavior remain unverified.
- No background observers, calendar-change receiver, date/time receiver, or reboot automation yet.

**Readiness gates**
1. Complete tile-driven evaluation and permission handling.
2. Verify provider read access and the meaning/units of its alarmtime field on the target Sleep as Android version.
3. Verify ACTION_SET_ALARM resolves to Sleep as Android and creates the intended new alarm with its defaults.
4. Confirm original alarm remains enabled and unchanged and duplicate behavior is understood.
5. Verify message/vibration extras; determine whether repeat days can safely be copied.
6. Test no-event, all-day-only, no-alarm, denied-permission, and inaccessible-provider paths.
7. Physical-device installation and behavior tests on the target Samsung phone.
8. Update README and docs with actual device results before declaring Phase 1 complete.

**Build/release status:** v0.1.1 is published as an immutable prerelease. CI build and 5 unit tests passed. APK is debug-signed. Physical-device acceptance is outstanding.

## Phase 2 — Transit time
Calculate transit time between a configured sleep location and first event. Decide routing provider, location/privacy permissions, network/offline behavior, and missing-location fallbacks. Gate: route calculations/privacy behavior tested and Phase 1 regression tests pass.

## Phase 3 — Settings and visual design
Add settings screen with AMOLED true-black styling and Material 3. Gate: accessibility, persistence, migration, and target One UI checks.

## Phase 4 — Bedtime confirmation
Notify at bedtime with proposed adjustment and let the user confirm or decline. Gate: permission/channel, timing, duplicate, timeout, and missed-notification cases tested; no change without confirmation.

## Phase 5 — Samsung Modes and Routines
Expose explicit actions (enable, disable, evaluate now). Gate: integration verified on physical Samsung device and setup documented.

## Cross-phase rules
Phases are readiness-gated. Do not start later phases without authorization. Update README and docs every phase. Never overwrite a release; fixes use a new patch version. Report implementation, build, tests, publication, installation, and device verification separately.
