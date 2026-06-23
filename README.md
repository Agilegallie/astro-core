# ASTRO-CORE

Android UI prototype for the ASTRO-CORE asteroid / NEO observation app, built from Google Stitch exports.

## Screens

- **Home** — NEO dashboard, network status, local observation, recent sightings
- **Capture** — viewfinder HUD, telescope sync, shutter capture
- **Observation Analysis** — verification form, stats grid, submit uplink flow
- **Report Transmitted** — pipeline stepper, reference ID, payload specs

## Quick start

1. Open `/Users/brettgallie/astro-core` in Android Studio
2. Install **Android 17 (API 37)** SDK — Tools → SDK Manager → Android Cinnamon Bun Preview
3. Let Gradle sync
4. Run on a Pixel device or emulator (API 28+)

## Android 17 target

| Setting | Value |
|---------|-------|
| compileSdk | 37 |
| targetSdk | 37 |
| minSdk | 28 |

## Flow

`Home → Quick Capture → Capture (shutter) → Analysis → Submit → Report Transmitted → Dashboard`

## Stack

- Kotlin 2.2, Jetpack Compose, Material3, Navigation Compose
- Android Gradle Plugin 9.2.x, Gradle 9.4.x
- Mock data only (no camera, GPS, or network in v1)
- Compose gradient backgrounds for space imagery
- System Monospace for label/data typography

## Design source

Stitch project: Deep Space Research Initiative / ASTRO-CORE
