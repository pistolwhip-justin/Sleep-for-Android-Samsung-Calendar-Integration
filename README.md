# 💤 Sleep for Android – Samsung Calendar Integration

&nbsp;&nbsp;![Status](https://img.shields.io/badge/status-test%20APK%20available-blue)&nbsp;&nbsp;![Platform](https://img.shields.io/badge/platform-Android-green)&nbsp;&nbsp;![License](https://img.shields.io/badge/license-WTFPL-blue)

A standalone Android companion that reads events exposed through Android's Calendar Provider (including Samsung Calendar) and requests an earlier Sleep as Android alarm. No Tasker or Google Calendar dependency.

## 🎯 Phase 1 — Calendar-driven alarm creation

The test build implements the core calculation and a new-alarm request:

1. Find the earliest timed event on the current local calendar day.
2. Ignore all-day events.
3. Calculate the target alarm time as **2 hours 15 minutes before** that event.
4. Read the next enabled Sleep as Android alarm through its Content Provider.
5. If the existing alarm is already at or earlier than the target, leave it unchanged.
6. If it is later, request a new Sleep as Android alarm using Android's ACTION_SET_ALARM integration.
7. Let Sleep as Android apply its new-alarm defaults. The current build also best-effort copies the existing message and vibration flag when available.

The existing alarm is never edited, disabled, or deleted. A new alarm may coexist with the original. Exact duplication of repeat days, sound, smart-wake, snooze, or proprietary settings is not implemented or guaranteed.

## 🧪 Current test build — v0.1.1

- [GitHub Release v0.1.1](https://github.com/pistolwhip-justin/Sleep-for-Android-Samsung-Calendar-Integration/releases/tag/v0.1.1)
- [Download APK](https://github.com/pistolwhip-justin/Sleep-for-Android-Samsung-Calendar-Integration/releases/download/v0.1.1/Sleep-Calendar-Alarm-v0.1.1-debug.apk)
- Debug-signed APK for sideload testing; not a Play Store/production release.
- CI build succeeded and all 5 unit tests passed.

**What this build currently does:** the Quick Settings tile stores the enabled/disabled state and opens the app. The app's **Evaluate calendar and alarm** button performs the lookup and launches Sleep as Android's confirmation UI when an earlier alarm is needed. It does not yet run automatically in the background when the tile is enabled.

**Physical-device verification is still required:** confirm Calendar Provider access, Sleep as Android alarm-provider read access, ACTION_SET_ALARM handling, new-alarm defaults, duplicate behavior, and that the original alarm remains unchanged.

## 🧭 Documentation
- [Project plan and future phases](docs/PROJECT_PLAN.md)
- [Requirements and acceptance tests](docs/REQUIREMENTS.md)
- [Technical decisions and research](docs/DECISIONS.md)
- [AI agent instructions](AGENTS.md)

## 🛣️ Roadmap
- **Phase 1:** Complete tile-driven evaluation and validate calendar-to-alarm creation on a physical Samsung device.
- **Phase 2:** Calculate transit time between sleep location and first event.
- **Phase 3:** Settings screen with AMOLED true-black and Material 3.
- **Phase 4:** Bedtime notification asking whether to apply the adjustment.
- **Phase 5:** App actions callable from Samsung Modes and Routines.

Later phases are planned only and are not part of Phase 1.

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
