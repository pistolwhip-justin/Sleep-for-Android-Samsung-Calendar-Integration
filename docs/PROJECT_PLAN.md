# 🗺️ Project Plan

**Updated:** 2026-11-19 | **Protocol:** 1.1.7

## 🎯 Objective
A standalone Android companion that reads events exposed through Android Calendar Provider (including Samsung Calendar) and adjusts an existing Sleep as Android alarm. No Tasker or Google Calendar dependency.

## Phase 1 — Minimal alarm adjustment
**Scope**
- One Quick Settings tile toggles automatic modifications.
- First timed event of current local day; ignore all-day events.
- Target = event start minus 2 hours 15 minutes.
- Move only the next enabled Sleep as Android alarm, and only if it is later than target.
- If already at or earlier than target, do nothing.
- No settings screen, travel calculation, bedtime prompt, or Modes and Routines actions.

**Supporting behavior**
- Calendar read permission only; persist toggle state.
- Re-evaluate after tile activation, calendar changes, local date/time/time-zone changes, and reboot.
- No continuous polling or sensitive calendar data in logs.
- Fail safely if permissions/provider/alarm unavailable.

**Readiness gate**
1. Demonstrate supported third-party write access to an existing Sleep as Android alarm (currently blocked; see DEC-001).
2. Test Samsung Calendar event reading and all-day exclusion.
3. Unit-test target calculation and earlier-only guard.
4. Verify correct alarm update without changing repeat rules, smart wake, label, sound, or other settings.
5. Automated tests and CI build pass.
6. Physical-device test tile, reboot persistence, event changes, alarm modification, and Sleep as Android smart-wake behavior.
7. Update README and docs.
8. Publish immutable, version-specific GitHub Release with tag pinned to build commit; provide verified in-chat APK, direct asset URL, and Release page.

**Status: BLOCKED.** A calendar preview or tile that cannot modify the alarm is not a completed Phase 1 release.

## Phase 2 — Transit time
Calculate transit time between a configured sleep location and first event. Before implementation decide routing provider, location/privacy permissions, network/offline behavior, and fallbacks for missing event locations. Gate: route calculations and privacy behavior tested; Phase 1 regression tests pass.

## Phase 3 — Settings and visual design
Add settings screen with AMOLED true-black styling and Material 3. Gate: accessibility, persistence, migration, and target One UI device checks; README screenshots updated.

## Phase 4 — Bedtime confirmation
At bedtime, notify with proposed adjustment and let the user confirm or decline. Gate: notification permission/channel, timing, duplicate, timeout, and missed-notification cases tested; no changes without confirmation.

## Phase 5 — Samsung Modes and Routines
Expose explicit actions (candidate actions: enable, disable, evaluate now) for Samsung Modes and Routines. Gate: integration verified on physical Samsung device and exact setup documented.

## Cross-phase rules
Phases are readiness-gated. Do not begin later phases without authorization. Update README every phase. Never overwrite a release; fixes use a new patch version. Report implementation, build, tests, publication, installation, and device verification separately.
