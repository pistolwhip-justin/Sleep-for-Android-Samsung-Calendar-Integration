# 💤 Sleep for Android – Samsung Calendar Integration

&nbsp;&nbsp;![Status](https://img.shields.io/badge/status-Phase%201%20test%20build-blue)&nbsp;&nbsp;![Platform](https://img.shields.io/badge/platform-Android-green)&nbsp;&nbsp;![License](https://img.shields.io/badge/license-WTFPL-blue)

A standalone Android companion that reads events exposed through Android's Calendar Provider (including Samsung Calendar) and proposes an earlier Sleep as Android alarm. No Tasker or Google Calendar dependency.

## 🎯 Phase 1 — Calendar-driven alarm creation

A Quick Settings tile enables or disables automatic evaluation. When enabled:

1. Find the earliest timed event on the current local calendar day.
2. Ignore all-day events.
3. Calculate the target alarm time as **2 hours 15 minutes before** that event.
4. Read the next enabled Sleep as Android alarm through its documented Content Provider.
5. If the existing alarm is already at or earlier than the target, leave it unchanged.
6. If it is later, request creation of a new Sleep as Android alarm using Android's standard ACTION_SET_ALARM integration.
7. Use the receiving alarm app's new-alarm defaults. Where supported by the available read data and Android intent contract, copy repeat days, label/message, and vibration; do not claim exact duplication of sound, smart-wake, or other proprietary settings.

The existing alarm is not edited or deleted. A created alarm may therefore coexist with the original. The app must never silently disable or delete the original. The user must confirm the alarm creation in Sleep as Android when its UI is presented.

## ⚠️ Test-build limitations

- This is an experimental integration path, not a verified replacement for modifying an existing alarm.
- Alarm-provider read access, ACTION_SET_ALARM handling, duplicate behavior, defaults, and repeat-day mapping require physical-device verification.
- If the existing alarm cannot be read reliably, no alarm is created.
- Calendar event details remain on-device; logs must not contain event titles, descriptions, attendees, or locations.
- A successful APK build does not prove alarm behavior.

## 🧭 Documentation
- [Project plan and future phases](docs/PROJECT_PLAN.md)
- [Requirements and acceptance tests](docs/REQUIREMENTS.md)
- [Technical decisions and research](docs/DECISIONS.md)
- [AI agent instructions](AGENTS.md)

## 🛣️ Roadmap
- **Phase 1:** Quick Settings toggle, calendar lookup, earlier-only comparison, and new-alarm creation request.
- **Phase 2:** Calculate transit time between sleep location and first event.
- **Phase 3:** Settings screen with AMOLED true-black and Material 3.
- **Phase 4:** Bedtime notification asking whether to apply the adjustment.
- **Phase 5:** App actions callable from Samsung Modes and Routines.

Later phases are planned only and are not part of Phase 1.

## 🧪 Build, test & release
Android builds use GitHub Actions and version-specific workflows. Test APKs are debug-signed and intended for sideloading. Each release is immutable; fixes require a new patch version.

The release workflow runs automated tests and assembles the APK. Physical-device verification remains a separate acceptance gate.

## 🔧 Development
Read AGENTS.md and the current [Development Protocols & Assets](https://github.com/pistolwhip-justin/development-protocols-n-assets) before making changes. Never claim an alarm was created or adjusted unless the resulting alarm is verified on a physical device.

## 📜 License
Licensed under the [DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE (WTFPL)](LICENSE), Version 2.

<a href="http://www.wtfpl.net/"><img src="http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-1.png" width="88" height="31" alt="WTFPL" /></a>

## ⚠️ Disclaimer
Independent companion project; not affiliated with or endorsed by Urbandroid or Samsung. Alarm behavior must be validated on a physical device before relying on it.

## 🙏 Acknowledgments
- [Sleep as Android Content Provider API](https://sleep.urbandroid.org/docs/devs/content_provider_api.html)
- [Sleep as Android Intent API](https://sleep.urbandroid.org/docs/devs/intent_api.html)
- [Android AlarmClock API](https://developer.android.com/reference/android/provider/AlarmClock)
- [Android Calendar Provider](https://developer.android.com/identity/providers/calendar-provider)

---
Last updated: 2026-11-19
