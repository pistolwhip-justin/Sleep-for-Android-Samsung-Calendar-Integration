# 🤖 Agent Instructions

Read the current global development protocols before changing this repository:
https://github.com/pistolwhip-justin/development-protocols-n-assets/blob/main/DEVELOPMENT_PROTOCOLS.md (v1.1.7)

Also read README.md and docs/PROJECT_PLAN.md, docs/REQUIREMENTS.md, and docs/DECISIONS.md.

## Project rules
- This is an independent Android companion, not a fork of Sleep as Android. Preserve attribution and do not imply affiliation.
- No Tasker or Google Calendar dependency.
- Phase 1 is only a Quick Settings enable/disable tile and fixed 2h15m earlier-alarm behavior.
- Ignore all-day events; use the earliest timed event of the current local day.
- Only move the next enabled Sleep as Android alarm earlier. Never delay, create, replace, or disable an alarm.
- Do not implement alarm writes until a supported third-party write mechanism is verified. See DEC-001.
- Never bypass this blocker with Accessibility automation or undocumented intents.
- Do not claim functionality until tested on a physical device.
- Calendar content stays on-device; logs must not contain event titles, descriptions, attendees, or locations.
- Update README and phase documentation during every phase.
- APK release must follow global protocol Section 7.1, including a version-specific workflow, immutable Release asset, source tag verification, and actual in-chat APK attachment.
