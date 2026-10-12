# بازی‌خانه

مجموعه‌ای آفلاین از بازی‌های کوچک فارسی برای اندروید (Kotlin + Jetpack Compose).

## افزودن بازی جدید
1. فایلی در `app/src/main/java/com/bazikhaneh/game/games/` بساز و یک `GameInfo` تعریف کن.
2. آن را در `core/GameRegistry.kt` به فهرست اضافه کن.

## ساخت APK
با هر push روی main، گیت‌هاب فایل app-debug.apk را در Artifacts می‌سازد.
