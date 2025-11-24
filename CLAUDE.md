# JsMacros Backports Project

This is a backports branch of JsMacros for Minecraft 1.21.11-beta.2, providing scripting capabilities for Minecraft through JavaScript and other programming languages.

## Project Overview

JsMacros is a Minecraft mod that allows users to write scripts to interact with the game world, chat, and various game mechanics. This particular branch is an active migration from Unimined to Architectury for better cross-platform compatibility.

## Project Structure

The project follows a multi-module Gradle structure using Architectury for cross-platform compatibility:

### Core Modules

- **`common/`** - Shared codebase used by all mod loaders
  - Core functionality and API implementations
  - Event systems
  - Script execution engine
  - Library helpers and utilities
  - Located at: `common/src/main/java/xyz/wagyourtail/jsmacros/`

- **`fabric/`** - Fabric mod loader implementation
  - Fabric-specific mod integration
  - Access widener configuration
  - Mod dependencies and includes
  - Located at: `fabric/src/main/java/`

- **`neoforge/`** - NeoForge mod loader implementation
  - NeoForge-specific mod integration
  - Current status: Extensions temporarily disabled
  - Located at: `neoforge/src/main/java/`

### Extension System

- **`extension/`** - Language extensions for multi-language script support
  - `extension/graal/` - GraalVM-based language runtime
  - `extension/graal/js/` - JavaScript language support
  - `extension/graal/python/` - Python language support
  - Other language extensions can be added here

### Supporting Modules

- **`site/`** - Documentation and website generation
- **`buildSrc/`** - Custom build logic and documentation generators
  - TypeScript definition generator (`tsdoclet`)
  - Python documentation generator (`pydoclet`)
  - Web documentation generator (`webdoclet`)

## Build System

### Gradle Configuration
- **Root Build**: Uses Kotlin DSL (`build.gradle.kts`)
- **Java Version**: 21 (required for compilation and runtime)
- **Multi-Module**: Architectury plugin for cross-platform builds
- **Version Catalog**: Centralized dependency management in `gradle/libs.versions.toml`

### Key Dependencies

#### Libraries
- **Guava**: Google's core libraries
- **Gson**: JSON parsing
- **Prism4j**: Syntax highlighting for multiple languages
- **NV WebSocket**: WebSocket client
- **Javassist**: Java bytecode manipulation
- **ASM**: Low-level bytecode manipulation

### Build Commands

```bash
# Build all modules
./gradlew buildAll

# Clean all modules
./gradlew cleanAll

# Create distribution
./gradlew createDist

# Build specific loader
./gradlew :fabric:build
./gradlew :neoforge:build

# Build extensions
./gradlew :extension:build
```

## Important Notes

### Build Performance
- **Avoid cleaning caches** - The build process is very time-consuming
- Incremental builds are heavily recommended
- Extension compilation can take significant time due to GraalVM dependencies

### Current Development Status
Based on recent commits and git status:
- Working on removing Forge support (deleted forge/ module)
- We are actively working on switching from Forge to NeoForge
- Fabric support is the primary focus with full extension support

### File Structure Notes
- `META-INF/` contains mod metadata and configuration
- `docs/` contains language-specific documentation
- `run/` directory for development environment configurations
- Configuration files use `.toml` format where applicable

### Extension Development
Extensions follow a specific structure:
- Each extension has its own `build.gradle.kts`
- Must include `jsmacros.extension.json` manifest
- Subprojects can be defined in `subprojects.txt` files
- Extensions are included in the final mod JAR for distribution

### Testing
- JUnit 5 for unit testing
- Test configuration in each module
- Integration tests should be run against both mod loaders

## Version Information
- **Project Version**: 2.1.1
- **Minecraft Target**: 1.21.11-beta.2
- **Java Requirement**: 21
- **Mod Loaders**: Fabric (primary), NeoForge (secondary)