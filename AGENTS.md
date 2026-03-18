# AGENTS.md — Kai Project Guide for AI Assistants

## Project Overview

**Kai** is an open-source, cross-platform AI assistant with persistent memory. It runs on Android, iOS, Windows, macOS, Linux, and Web. The local directory is `hyper-claw`, but the project name is **Kai**.

- **Repository:** https://github.com/SimonSchubert/Kai
- **Language:** Kotlin Multiplatform (+ Swift for iOS shell)
- **UI:** Jetpack Compose Multiplatform + Material 3
- **Min JDK:** 21
- **Build System:** Gradle 9.2.1 with Kotlin DSL + Version Catalog

## Architecture

### Pattern: MVVM + Repository + DI (Koin)

```
UI (Compose) → ViewModel → DataRepository (interface) → Requests / Stores
                                  ↓
                    RemoteDataRepository (implementation)
                          ↓              ↓             ↓
                    Requests (HTTP)   AppSettings   ConversationStorage
                    ToolExecutor      MemoryStore    TaskStore
                    HeartbeatManager  McpServerManager  EmailStore
```

### Multi-Platform Strategy

Kotlin `expect`/`actual` declarations in `Platform.kt` abstract platform differences:
- HTTP client engines, secure storage, file directories, available tools, URL opening, image compression, TTS

### Multi-Provider LLM Abstraction

Three API format families in `Requests.kt`:
- **OpenAI-Compatible** — 14 services (OpenAI, Groq, DeepSeek, Mistral, xAI, etc.)
- **Gemini** — Google's format
- **Anthropic** — Claude's format

Service definitions are a sealed class (`Service`) with 18 subclasses. Users can configure multiple services in priority order with automatic fallback.

## Module Structure

```
hyper-claw/
├── composeApp/          ← Main shared module (all business logic + UI)
│   └── src/
│       ├── commonMain/  ← Shared Kotlin code (~83 files)
│       ├── commonTest/  ← Unit tests (13 files)
│       ├── androidMain/ ← Android platform implementations
│       ├── desktopMain/ ← Desktop (JVM) implementations + main()
│       ├── desktopTest/ ← Desktop-only tests
│       ├── iosMain/     ← iOS implementations + MainViewController
│       └── wasmJsMain/  ← Web/WasmJS implementations + main()
├── androidApp/          ← Thin Android shell (Activity, Application, ProGuard)
├── screenshotTests/     ← Compose screenshot tests (Paparazzi)
├── iosApp/              ← Xcode project + SwiftUI wrapper
├── docs/                ← MkDocs feature documentation
├── fastlane/            ← Play Store deployment
├── flatpak/             ← Flathub packaging
├── aur/                 ← Arch Linux AUR packaging
└── .github/workflows/   ← CI/CD pipelines
```

## Key Source Packages

All under `composeApp/src/commonMain/kotlin/com/inspiredandroid/kai/`:

| Package | Purpose |
|---------|---------|
| `data/` | Core data layer — DataRepository, models, stores, settings |
| `email/` | IMAP/SMTP email client |
| `mcp/` | Model Context Protocol client + server management |
| `network/` | HTTP requests layer |
| `network/dtos/anthropic/` | Anthropic API DTOs |
| `network/dtos/gemini/` | Gemini API DTOs |
| `network/dtos/openaicompatible/` | OpenAI-compatible API DTOs |
| `network/tools/` | Tool interface and schema definitions |
| `splinterlands/` | Blockchain game integration |
| `tools/` | Tool implementations (web search, memory, scheduling, etc.) |
| `ui/chat/` | Chat screen, ViewModel, composables |
| `ui/settings/` | Settings screen, ViewModel |

## Key Files

| File | Role |
|------|------|
| `App.kt` | Root composable — Koin, Coil, navigation, theming |
| `AppModule.kt` | Koin dependency injection graph |
| `data/DataRepository.kt` | Central interface (~121 lines) for all app operations |
| `data/RemoteDataRepository.kt` | DataRepository implementation — orchestrates tool-calling loop (up to 15 iterations) |
| `data/Service.kt` | Sealed class with 18 LLM service definitions |
| `data/AppSettings.kt` | Settings persistence via multiplatform-settings |
| `data/ConversationStorage.kt` | Encrypted conversation persistence |
| `data/Conversation.kt` | Conversation + Message data models |
| `data/MemoryStore.kt` | Persistent memory system (categories: GENERAL, LEARNING, ERROR, PREFERENCE) |
| `data/TaskStore.kt` | Scheduled tasks with cron support |
| `data/HeartbeatManager.kt` | Autonomous background self-checks (30min interval, 8am–10pm) |
| `network/Requests.kt` | HTTP layer — handles 3 API format families |
| `network/tools/Tool.kt` | Tool interface (schema + execute) |
| `mcp/McpServerManager.kt` | MCP server lifecycle management |
| `Platform.kt` | `expect` declarations for platform abstraction |

## Entry Points

| Platform | File |
|----------|------|
| Desktop | `composeApp/src/desktopMain/.../main.kt` |
| Android | `androidApp/src/main/.../MainActivity.kt` + `KaiApplication.kt` |
| iOS | `composeApp/src/iosMain/.../MainViewController.kt` |
| Web | `composeApp/src/wasmJsMain/.../main.kt` |

## Key Dependencies

| Library | Purpose | Version |
|---------|---------|---------|
| Compose Multiplatform | UI framework | 1.10.2 |
| Ktor | HTTP client | 3.4.1 |
| Koin | Dependency injection | 4.2.0 |
| kotlinx-serialization | JSON (de)serialization | 1.10.0 |
| kotlinx-datetime | Date/time handling | 0.7.1 |
| multiplatform-settings | Key-value persistence | 1.3.0 |
| Coil 3 | Image loading | 3.4.0 |
| Compottie | Lottie animations | 2.1.0 |
| BouncyCastle | Encryption (JVM/Android) | 1.83 |
| Turbine | Flow testing | — |

## Build Commands

```bash
# Code formatting
./gradlew spotlessApply

# Run unit tests
./gradlew :composeApp:desktopTest

# Update screenshot tests
./gradlew :screenshotTests:updateScreenshots

# Desktop
./gradlew packageDmg          # macOS
./gradlew packageMsi          # Windows
./gradlew packageDeb          # Linux DEB
./gradlew packageRpm          # Linux RPM

# Android
./gradlew :androidApp:assembleFossRelease        # FOSS APK
./gradlew :androidApp:bundlePlayStoreRelease     # Play Store AAB

# Web
# Webpack-based, outputs WasmJS app
```

## CI/CD

- **test.yml** — On push to `main` + PRs: spotlessApply, updateScreenshots, desktopTest, auto-commit changes
- **release.yml** — On `v*` tags: builds all platforms in parallel, creates GitHub Release, uploads to Play Store (Fastlane), publishes to WinGet
- **aur.yml** — AUR publishing
- **static.yml** — MkDocs + Wasm web app deployment

## Testing

- **Unit tests:** `composeApp/src/commonTest/` (13 files) — ViewModels, Settings, DTOs, Serialization
- **Desktop tests:** `composeApp/src/desktopTest/` — HiveCrypto
- **Screenshot tests:** `screenshotTests/` — Paparazzi-based Compose screenshots
- **Test double:** `FakeDataRepository` implements `DataRepository` for testing

## Feature Documentation

Detailed feature specs live in `docs/features/`:

| File | Topic |
|------|-------|
| `chat.md` | Chat and conversations |
| `multi-service.md` | Multi-service fallback |
| `tools.md` | Tool system (execution flow, safety guards, platform tools) |
| `mcp.md` | MCP server support |
| `memories.md` | Memory system |
| `heartbeat.md` | Autonomous heartbeat |
| `tasks.md` | Task scheduling |
| `daemon.md` | Daemon/background mode |
| `splinterlands.md` | Splinterlands integration |
| `settings-export-import.md` | Settings export/import |

## Android Product Flavors

- **`foss`** — Default/F-Droid build (no Play Store APIs)
- **`playStore`** — Google Play build (includes review APIs)

## Conventions

- Code formatting enforced by **Spotless + ktlint**
- Centralized versions in `gradle/libs.versions.toml`
- Feature docs should be updated when modifying related logic (see `CLAUDE.md`)
- Conversations are stored with local encryption
- Memory entries have categories and hit counts; entries with 5+ hits can be promoted to the system prompt
- Tool execution has a 15-iteration safety cap and per-tool timeouts
- The app supports 57 locales — string resources are in `composeApp/src/commonMain/composeResources/`
