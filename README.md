# Card Deck

A simple Android app (Kotlin + Jetpack Compose) that shuffles a standard 52-card deck
and lets you pick cards one at a time.

- **Pick card** (or tap the deck): the top card moves to the discard pile and is shown face up.
  A picked card never comes back from the same deck.
- **Reset & shuffle**: puts all cards back and reshuffles.
- The discard pile is listed below, newest first.

## Build

Open the folder in Android Studio, or from the command line (Android SDK required):

    ./gradlew assembleDebug      # APK in app/build/outputs/apk/debug/
    ./gradlew installDebug       # install on a connected device/emulator
    ./gradlew test               # unit tests for the deck logic

## CI

Every push and pull request runs the unit tests and builds a debug APK on GitHub Actions.
Download it from the run's **Artifacts** section (`CardDeck-debug-apk`).
