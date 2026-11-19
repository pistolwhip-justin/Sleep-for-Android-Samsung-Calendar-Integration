# 🗺️ Project Plan

**Updated:** 2026-11-19 | **Protocol:** 1.1.7

## 🎯 Objective
A standalone Android companion reads events from Android Calendar Provider (including Samsung Calendar) and requests an earlier Sleep as Android alarm. No Tasker or Google Calendar dependency.

## Phase 1 — Quick Settings + create-earlier-alarm request

**Scope**
- One Quick Settings tile enables/disables automatic evaluation.
- Select the earliest timed event today; exclude all-day events.
- Target = event start minus exactly 135 minutes.
- Read the next enabled Sleep as Android alarm using its documented Content Provider.
- If current alarm time is at or earlier than target, do nothing.
- If current alarm is later, request a new alarm through Android ACTION_SET_ALARM.
- Never edit, delete, or disable the existing alarm.
- Use Sleep as Android's new-alarm defaults as the minimum supported behavior.
- Best-effort duplication of repeat days, message/label, and vibration only where reliably available and supported by the intent API. Do not claim to duplicate sound, smart-wake window, or proprietary preferences.

**Important behavior**
- A new alarm can coexist with the existing alarm. The app must not silently remove the old alarm.
- The user must be shown/allowed to confirm the alarm request; no hidden background alarm creation.
- Do not create an alarm if the current Sleep as Android alarm cannot be read reliably or if no supported receiver is available.
- Calendar permission is requested at runtime; the toggle persists.
- Re-evaluate on tile activation and explicit user request. Calendar/time/reboot automation is deferred until basic device behavior is proven.
- No event titles, descriptions, attendees, or locations in logs.

**Phase 1 readiness gates**
1. Automated tests pass for event selection, all-day exclusion, 135-minute calculation, and earlier-only guard.
2. CI builds a debug APK from committed source.
3. APK installs on target Samsung device.
4. Verify Calendar Provider access and Sleep as Android alarm-provider read access.
5. Verify ACTION_SET_ALARM resolves to Sleep as Android and creates the intended alarm with its defaults.
6. Verify repeat-day/message/vibration copying where supported; document unsupported fields.
7. Confirm original alarm remains unchanged and duplicate behavior is understood.
8. Test no-event, all-day-only, no-alarm, denied-permission, and inaccessible-provider paths.
9. Update README and docs with actual test results.
10. Publish immutable versioned Release and verified APK attachment under global protocol Section 7.1.

**Status:** Phase 1 test implementation authorized; physical-device behavior remains unverified until tested.

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
