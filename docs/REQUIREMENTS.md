# 📋 Requirements

**Status:** Phase 1 experimental implementation; physical-device validation pending. **Updated:** 2026-11-19

- **RQ-001 Standalone:** Android app; Tasker is not required.
- **RQ-002 Calendar:** Read Samsung Calendar events through Android Calendar Provider; no Google Calendar API/account dependency.
- **RQ-003 Tile:** One Quick Settings tile stores the enabled/disabled state persistently. Current v0.1.1 opens the app after toggling; automatic tile-triggered evaluation remains pending.
- **RQ-004 First event:** Earliest timed event in current local day; all-day events excluded.
- **RQ-005 Lead:** Target = event start minus exactly 135 minutes.
- **RQ-006 Earlier only:** Read the next enabled Sleep as Android alarm. If it is at or earlier than target, do nothing. If later, request a new alarm at target.
- **RQ-007 Preserve original:** Never edit, delete, disable, or silently replace the existing alarm.
- **RQ-008 New-alarm defaults:** A newly created alarm must at minimum use Sleep as Android's own new-alarm defaults. v0.1.1 best-effort copies message and vibration where available. Repeat-day, ringtone, smart-wake, and proprietary settings are not duplicated or guaranteed.
- **RQ-009 User-visible creation:** Use Android ACTION_SET_ALARM; show the receiving app's UI/confirmation. Do not attempt hidden/background creation.
- **RQ-010 Safe failure:** If calendar permission, alarm-provider read access, next-alarm data, or a supported alarm receiver is unavailable, make no change.
- **RQ-011 Disable:** Turning tile off stops future evaluations; it does not remove previously created alarms.
- **RQ-012 Privacy:** Calendar data stays on-device. Logs omit titles, descriptions, attendees, and locations. No analytics.
- **RQ-013 Evaluation:** v0.1.1 supports manual evaluation from the app UI. Tile-triggered evaluation and automatic event/time/reboot observers remain future Phase 1 work.

## 🧪 Acceptance cases
| Case | Input | Expected |
|---|---|---|
| A | Event 09:00; existing alarm 07:30 | Request new alarm at 06:45 |
| B | Event 09:00; existing alarm 06:30 | Leave unchanged |
| C | All-day event plus timed event 10:00 | Ignore all-day; target 07:45 |
| D | No timed event today | No change |
| E | No enabled/readable Sleep alarm | No change; explain why |
| F | Tile disabled | No evaluation |
| G | Calendar permission denied | No change; explain permission |
| H | Multiple alarms | Compare the next enabled scheduled occurrence only |
| I | Event moves later/earlier | Recalculate; never request a later alarm |
| J | Existing alarm remains after new alarm request | Original remains enabled and unchanged |
| K | Sleep as Android unavailable as ACTION_SET_ALARM receiver | No change; explain unsupported receiver |
| L | New alarm uses default sound/settings | Expected minimum; verify on device |
| M | Message/vibration metadata available | Best-effort copy using supported intent extras |

## Automated tests in v0.1.1
Five unit tests passed for: create when existing alarm is later; preserve earlier alarm; preserve equal-time alarm; reject a past target; reject unreadable alarm. Calendar-provider selection and real Sleep as Android integration are not covered by unit tests.

## Excluded from Phase 1
Transit calculation; configurable lead; settings screen/theme; bedtime prompt; Modes and Routines actions; Tasker; Google Calendar; editing/deleting/disabling existing alarms; claiming exact duplication of proprietary alarm settings.
