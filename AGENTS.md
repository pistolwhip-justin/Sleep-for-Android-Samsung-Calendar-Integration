# 🤖 Agent Instructions

Read the current global development protocols before changing this repository:
https://github.com/pistolwhip-justin/development-protocols-n-assets/blob/main/DEVELOPMENT_PROTOCOLS.md (currently v1.1.7).

Also read README.md and docs/PROJECT_PLAN.md, docs/REQUIREMENTS.md, and docs/DECISIONS.md.

## Project rules
- This is an independent Android companion, not a fork of Sleep as Android. Preserve attribution and do not imply affiliation.
- No Tasker or Google Calendar dependency.
- Phase 1 is a Quick Settings enable/disable tile, calendar lookup, 135-minute lead, earlier-only comparison, and user-visible new-alarm request.
- Ignore all-day events; use the earliest timed event of the current local day.
- Read the next enabled Sleep as Android alarm through the documented provider. If it cannot be read reliably, fail safely.
- Do not attempt provider writes, undocumented intents, Accessibility automation, or hidden alarm creation.
- If the existing alarm is later than target, use Android ACTION_SET_ALARM addressed to Sleep as Android. Never edit, delete, disable, or silently replace the existing alarm.
- Allow Sleep as Android's own new-alarm defaults as the minimum. Copy only metadata that is reliably readable and supported by documented intent extras. Do not claim exact duplication of ringtone, smart-wake, or proprietary settings.
- Keep the receiving app's user-visible confirmation flow. A new alarm may coexist with the original.
- Never claim functionality until tested on a physical device.
- Calendar content stays on-device; logs must not contain event titles, descriptions, attendees, or locations.
- Update README and phase documentation during every phase.
- APK release must follow global protocol Section 7.1: dedicated version workflow, immutable release, tag pinned to build commit, automated tests, verified direct asset URL, and actual in-chat APK attachment.
