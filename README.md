# Native Kotlin Android Boilerplate

This repository is a reusable Native Kotlin Android boilerplate for the single-system mobile pipeline. It replaces the previous React Native/Expo implementation.

## Stack

- Kotlin
- Android SDK
- Gradle (Kotlin DSL)
- Kotest (unit tests), Android Lint + Ktlint, Dependency Audit, License Compliance

## Structure

```text
app/
  src/
    main/
      java/com/implementsprint/mobile/
      res/
        values/
    androidTest/
    test/
  build.gradle.kts
build.gradle.kts
settings.gradle.kts
gradle.properties
```

## Commands

- `./gradlew assembleDebug`: Build debug APK
- `./gradlew assembleRelease`: Build release APK
- `./gradlew unitTest`: Run Kotlin unit tests (Kotest)
- `./gradlew lintAndStyleCheck`: Run Android lint + Kotlin style checks
- `./gradlew dependencyAudit`: Run dependency vulnerability scan (OWASP)
- `./gradlew licenseCompliance`: Run third-party license compliance check
- `./gradlew check`: Run all verification gates above
- `./gradlew connectedAndroidTest`: Run instrumented tests

## Android SDK Setup (Template Consumer Guide)

Because this is a reusable template for multiple tribes, do not commit a machine-specific `local.properties` file.

Use one of these setup options in each consuming repository:

1. Local development (recommended)
  - Copy `local.properties.example` to `local.properties`
  - Set `sdk.dir` to your local Android SDK path
2. CI or ephemeral environments
  - Set either `ANDROID_HOME` or `ANDROID_SDK_ROOT`
  - Install Android SDK components before running Gradle checks

This keeps the template portable while allowing `unitTest`, `lintAndStyleCheck`, and other Android Gradle tasks to resolve SDK dependencies.

### GitHub Actions Example

This template already includes a ready workflow at `.github/workflows/ci-verify.yml`.

Use this pattern in consuming repositories to make SDK discovery explicit in CI:

```yaml
name: Kotlin Mobile Verify

on:
  pull_request:
  push:
    branches: [main]

jobs:
  verify:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v5

      - name: Setup JDK
        uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: "17"

      - name: Setup Android SDK
        uses: android-actions/setup-android@v3

      - name: Export Android SDK variables
        run: |
          echo "ANDROID_HOME=${ANDROID_HOME}" >> "$GITHUB_ENV"
          echo "ANDROID_SDK_ROOT=${ANDROID_HOME}" >> "$GITHUB_ENV"

      - name: Run verification gates
        run: ./gradlew check --no-daemon
```

## CI/CD Pipeline

The template uses a workflow caller at `.github/workflows/mobile-pipeline-caller.yml` that delegates execution to the central orchestrator workflow (`master-pipeline-mobile.yml` in `central-workflow`).

### Action Required: Update Repository Variables

Since this repository is now a native Kotlin Android project, you **must** update the `MOBILE_SINGLE_SYSTEMS_JSON` repository variable in your GitHub settings.

**Recommended value for Kotlin Android:**

```json
{
  "name": "mobile-kotlin",
  "dir": ".",
  "mobile_stack": "kotlin",
  "gradle_task": "assembleRelease bundleRelease",
  "enable_android_build": true,
  "enable_ios_build": false,
  "version_stream": "mobile-kotlin"
}
```

This configuration ensures the central workflow detects the project type as Kotlin/Android and executes release packaging tasks so branch main can publish production-ready Android artifacts.
