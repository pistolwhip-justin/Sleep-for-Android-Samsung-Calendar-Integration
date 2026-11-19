# 📋 Requirements

**Status:** Phase 1 test implementation. **Updated:** 2026-11-19

- **RQ-001 Standalone:** Android app; Tasker is not required.
- **RQ-002 Calendar:** Read Samsung Calendar events through Android Calendar Provider; no Google Calendar API/account dependency.
- **RQ-003 Tile:** One Quick Settings tile enables/disables automatic evaluation; state persists.
- **RQ-004 First event:** Earliest timed event in current local day; all-day events excluded.
- **RQ-005 Lead:** Target = event start minus exactly 135 minutes.
- **RQ-006 Earlier only:** Read the next enabled Sleep as Android alarm. If it is at or earlier than target, do nothing. If later, request a new alarm at target.
- **RQ-007 Preserve original:** Never edit, delete, disable, or silently replace the existing alarm.
- **RQ-008 New-alarm defaults:** A newly created alarm must at minimum use Sleep as Android's own new-alarm defaults. Best-effort copy repeat days, message/label, and vibration only when available and supported. Sound, smart-wake, and proprietary settings are not guaranteed to be duplicated.
- **RQ-009 User-visible creation:** Use Android ACTION_SET_ALARM; show the receiving app's UI/confirmation. Do not attempt hidden/background creation.
- **RQ-010 Safe failure:** If calendar permission, alarm-provider read access, next-alarm data, or a supported alarm receiver is unavailable, make no change.
- **RQ-011 Disable:** Turning tile off stops future evaluations; it does not remove previously created alarms.
- **RQ-012 Privacy:** Calendar data stays on-device. Logs omit titles, descriptions, attendees, and locations. No analytics.
- **RQ-013 Evaluation:** Phase 1 supports evaluation from tile/app interaction; automatic event/time/reboot observers are deferred until core behavior is validated.

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
| M | Repeat/message/vibration metadata available | Copy only if intent contract supports it |

## Excluded from Phase 1
Transit calculation; configurable lead; settings screen/theme; bedtime prompt; Modes and Routines actions; Tasker; Google Calendar; editing/deleting/disabling existing alarms; claiming exact duplication of proprietary alarm settings.
