# 📋 Requirements

**Status:** Phase 1 defined; blocked by DEC-001. **Updated:** 2026-11-19

- **RQ-001 Standalone:** Android app; Tasker is not required.
- **RQ-002 Calendar:** Read Samsung Calendar events through Android Calendar Provider; no Google Calendar API/account dependency.
- **RQ-003 Tile:** One Quick Settings tile enables/disables automatic modification; state persists.
- **RQ-004 First event:** Earliest timed event in current local day; all-day events excluded.
- **RQ-005 Lead:** Target = event start minus exactly 135 minutes.
- **RQ-006 Earlier only:** If next enabled Sleep as Android alarm is later than target, move it earlier. If already at or earlier than target, do nothing.
- **RQ-007 Preserve:** Do not create/replace/delete alarms or alter unrelated alarm settings, repeat days, label, sound, vibration, or smart-wake window.
- **RQ-008 Fail safe:** If permissions, provider, or supported write access are unavailable, make no change.
- **RQ-009 Disable:** Turning tile off stops future changes; it does not restore a previous time.
- **RQ-010 Privacy:** Calendar data stays on-device. Logs omit titles, descriptions, attendees, and locations. No analytics.
- **RQ-011 Re-evaluation:** Re-evaluate on activation, calendar change, date/time/time-zone change, and reboot. No permanent polling service.

## 🧪 Acceptance cases

| Case | Input | Expected |
|---|---|---|
| A | Event 09:00; alarm 07:30 | Move to 06:45 |
| B | Event 09:00; alarm 06:30 | Leave unchanged |
| C | All-day event plus timed event 10:00 | Ignore all-day; target 07:45 |
| D | No timed event today | No change |
| E | No enabled Sleep alarm | No change |
| F | Tile disabled | No change |
| G | Write access unavailable | No change; explain blocker |
| H | Multiple enabled alarms | Only next scheduled occurrence eligible |
| I | Event moves later/earlier | Recalculate; never move alarm later |
| J | Event removed after prior adjustment | Do not restore or move alarm later |

## Excluded from Phase 1
Transit calculation; configurable lead; settings screen/theme; bedtime prompt; Modes and Routines actions; Tasker; Google Calendar; creating, deleting, disabling, or replacing alarms.
