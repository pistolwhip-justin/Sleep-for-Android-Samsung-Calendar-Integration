# 💤 Sleep for Android – Samsung Calendar Integration

&nbsp;&nbsp;![Status](https://img.shields.io/badge/status-research%20%26%20planning-orange)&nbsp;&nbsp;![Platform](https://img.shields.io/badge/platform-Android-green)&nbsp;&nbsp;![License](https://img.shields.io/badge/license-WTFPL-blue)

A standalone Android companion intended to adjust an existing Sleep as Android alarm using Samsung Calendar events exposed through Android's Calendar Provider. No Tasker dependency and no Google Calendar dependency.

## 🎯 Phase 1

A Quick Settings tile enables or disables automatic modifications. When enabled:

1. Find the first timed event on the current local calendar day.
2. Ignore all-day events.
3. Calculate the target alarm time as **2 hours 15 minutes before** that event.
4. Move only the next enabled Sleep as Android alarm earlier, and only if its current time is later than the target.
5. If the alarm is already at or earlier than the target, leave it unchanged. Never create or replace an alarm.

Disabling stops future automatic changes; it does not restore an earlier alarm time.

> 🚧 **Current status: blocked before implementation/release.** Sleep as Android documents an alarm Content Provider, but a 2025 developer report says writes require the signature-protected com.urbandroid.sleep.WRITE permission. A separately signed third-party app cannot assume it can modify alarm records. A supported write mechanism must be verified before we claim Phase 1 works or publish a functional APK. See docs/DECISIONS.md.

## 🧭 Documentation

- [Project plan and future phases](docs/PROJECT_PLAN.md)
- [Requirements and acceptance tests](docs/REQUIREMENTS.md)
- [Technical decisions and research](docs/DECISIONS.md)
- [AI agent instructions](AGENTS.md)

## 🛣️ Roadmap

- **Phase 1:** Quick Settings toggle and fixed 2h15m earlier-alarm rule. Blocked pending supported alarm-write access.
- **Phase 2:** Calculate transit time between sleep location and first event.
- **Phase 3:** Settings screen with AMOLED true-black and Material 3.
- **Phase 4:** Bedtime notification asking whether to apply the change.
- **Phase 5:** App actions for Samsung Modes and Routines.

Later phases are planned only and are not part of Phase 1 scope.

## 🧪 Build & release

There is no installable APK yet. Once the integration blocker is resolved, builds will use dedicated version-specific GitHub Actions workflows and immutable GitHub Release assets. APK delivery will follow the global development protocol, including an actual verified in-chat attachment.

## 🔧 Development

Read AGENTS.md and the current [Development Protocols & Assets](https://github.com/pistolwhip-justin/development-protocols-n-assets) before making changes. Never claim an alarm was modified unless the write and resulting time are verified on a physical device.

## 📜 License

Licensed under the [DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE (WTFPL)](LICENSE), Version 2.

<a href="http://www.wtfpl.net/"><img src="http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-1.png" width="88" height="31" alt="WTFPL" /></a>

## ⚠️ Disclaimer

Independent companion project; not affiliated with or endorsed by Urbandroid or Samsung. Alarm behavior must be validated on a physical device before relying on it.

## 🙏 Acknowledgments

- [Sleep as Android Content Provider API](https://sleep.urbandroid.org/docs/devs/content_provider_api.html)
- [Sleep as Android Intent API](https://sleep.urbandroid.org/docs/devs/intent_api.html)
- [Android Calendar Provider](https://developer.android.com/identity/providers/calendar-provider)

---

Last updated: 2026-11-19
