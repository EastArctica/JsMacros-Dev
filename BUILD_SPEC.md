# Build and Packaging Specification

This document defines the naming, formats, channels, and publishing rules for:

* Base Minecraft mod jars
* Extension jars
* Devkit bundles (web docs, TypeScript, Python)
* GitHub Actions outputs
* Modrinth versions

This spec applies to the existing project structure. It does not change how the Gradle projects are organized internally; it only defines how artifacts must be named and published.

---

## 1. Terminology

**MODID**
Canonical mod identifier:

```text
jsmacros
```

**EXT_DIR**
Directory where extensions are loaded at runtime:

```text
config/jsMacros/extensions
```

Note: this uses `jsMacros` (case-sensitive) in the path.

**MODVER**
Semantic version of the mod API and feature set.
Format: `MAJOR.MINOR.PATCH`
Example: `1.3.0`

**MCVER**
Target Minecraft version.
Examples: `1.21`, `1.20.4`

**LOADER**
Mod loader identifier.
Values: `fabric`, `neoforge`

**CHANNEL**
Release channel.
Values:

* `dev`
* `alpha`
* `beta`
* `release`

**DATE**
Build date used for nightly alpha builds.
Format: `YYYYMMDD`
Example: `20251211`

**BUILD**
Short git commit hash used in dev builds.
Example: `a1b2c3d`

**EXTID**
Extension identifier.
Examples:

```text
graal
graalpy
graaljs
```

---

## 2. Channels and release process

There are four channels with distinct behaviors and publishing rules.

### 2.1 dev

* Purpose: continuous builds from every push for quick testing.
* Trigger:

  * Every push to the repository (on branches selected by CI configuration).
* Published to:

  * GitHub Releases (one dev release per pushed commit).
* Not published to:

  * Modrinth.
* Requirements for dev GitHub Releases:

  * Marked as pre-release.
  * Include a note in the description stating that alpha, beta, and release builds are available on Modrinth.
  * Include a direct link to the project’s Modrinth versions page.

### 2.2 alpha (nightly)

* Purpose: automated nightly builds for early testers.
* Trigger:

  * Scheduled workflow at **3:00 AM America/New_York** every day.
* Published to:

  * GitHub Releases.
  * Modrinth (`version_type = alpha`).
* Considered unstable, but public.

### 2.3 beta

* Purpose: feature-complete builds under testing before release.
* Trigger:

  * GitHub Actions workflow run manually by a developer.
* Published to:

  * GitHub Releases.
  * Modrinth (`version_type = beta`).

### 2.4 release

* Purpose: stable builds recommended for general use.
* Trigger:

  * GitHub Actions workflow run manually by a developer.
* Published to:

  * GitHub Releases.
  * Modrinth (`version_type = release`).

All Modrinth publishing is handled automatically by GitHub Actions via the Modrinth API.

---

## 3. Version identifiers

### 3.1 Modrinth `version_number`

Modrinth versions are **loader-specific**, so `version_number` includes the loader.

* Release
  `MODVER+LOADER`
  Examples:
  `1.3.0+fabric`
  `1.3.0+neoforge`

* Beta
  `MODVER-beta.N+LOADER`
  Examples:
  `1.3.0-beta.1+fabric`
  `1.3.0-beta.1+neoforge`

* Alpha nightly
  `MODVER-alpha.DATE+LOADER`
  Examples:
  `1.3.0-alpha.20251211+fabric`
  `1.3.0-alpha.20251211+neoforge`

Each `(MCVER, MODVER, CHANNEL, LOADER)` combination is a distinct Modrinth version.

Dev builds are not published to Modrinth and do not have a Modrinth `version_number`.

### 3.2 Gradle `version`

Gradle’s project `version` is used in artifact names and should follow:

* Release
  `MODVER`
  Example: `1.3.0`

* Beta
  `MODVER-beta.N`
  Example: `1.3.0-beta.1`

* Alpha nightly
  `MODVER-alpha.DATE`
  Example: `1.3.0-alpha.20251211`

* Dev
  `MODVER-dev-BUILD`
  Example: `1.3.0-dev-a1b2c3d`

This `version` is reused in the file naming patterns below.

---

## 4. Git tags

Releases are defined **per Minecraft version**. Each supported `MCVER` has its own independent sequence of tags.

Tags encode the mod version and the Minecraft version. A single tag does not cover multiple Minecraft versions.

### 4.1 Tag formats

* Release:
  `vMODVER+MCVER`
  Example: `v1.3.0+1.21`

* Beta:
  `vMODVER-beta.N+MCVER`
  Example: `v1.3.0-beta.1+1.21`

* Alpha nightly:
  `vMODVER-alpha.DATE+MCVER`
  Example: `v1.3.0-alpha.20251211+1.21`

* Dev:
  Dev releases may use automatically generated tags (for example `vMODVER-dev-BUILD+MCVER` or similar). The exact dev tag pattern is an implementation detail as long as the artifact naming rules in this document are followed.

GitHub Actions uses these tags to determine `MODVER`, `MCVER`, `CHANNEL`, and any suffix values (`N`, `DATE`, `BUILD`) needed for artifact naming and Modrinth publishing.

---

## 5. Base mod jars

Base mod jars are the files that go into the `mods` directory.

### 5.1 File format

* File type: `.jar`
* Content: compiled mod for a specific `MCVER` and `LOADER`.

### 5.2 File name pattern

Pattern:

```text
MODID-MCVER-LOADER-VERSION.jar
```

Where:

* `MODID` = `jsmacros`
* `MCVER` is the Minecraft version
* `LOADER` is `fabric` or `neoforge`
* `VERSION` is the Gradle `version` (section 3.2)

Examples:

* Release
  `jsmacros-1.21-fabric-1.3.0.jar`
  `jsmacros-1.21-neoforge-1.3.0.jar`

* Beta
  `jsmacros-1.21-fabric-1.3.0-beta.1.jar`

* Alpha nightly
  `jsmacros-1.21-fabric-1.3.0-alpha.20251211.jar`

* Dev
  `jsmacros-1.21-fabric-1.3.0-dev-a1b2c3d.jar`

Gradle must be configured so that base jars follow this exact naming pattern.

---

## 6. Extension jars

Extensions are optional runtime additions loaded from `config/jsMacros/extensions`.

### 6.1 Runtime location

At runtime, extension jars must be located at:

```text
config/jsMacros/extensions/EXTFILE.jar
```

Where `EXTFILE` is the full extension jar name.

### 6.2 File format

* File type: `.jar`
* Content: extension code for a specific `MCVER` and `LOADER`.

### 6.3 File name pattern

Pattern:

```text
MODID-ext-EXTID-MCVER-LOADER-VERSION.jar
```

Where:

* `MODID` = `jsmacros`
* `EXTID` is an identifier like `graal`, `graalpy`, `graaljs`
* `MCVER`, `LOADER`, and `VERSION` are as defined above.

Examples:

* Release
  `jsmacros-ext-graal-1.21-fabric-1.3.0.jar`

* Alpha nightly
  `jsmacros-ext-graalpy-1.21-neoforge-1.3.0-alpha.20251211.jar`

Gradle must be configured so that extension jars follow this pattern.

---

## 7. Devkit zip

The devkit bundles developer-facing assets (docs and language bindings) for a given `MCVER`, `LOADER`, and `VERSION`.

### 7.1 File format

* File type: `.zip`

### 7.2 File name pattern

Devkits are fully named and follow a pattern similar to extensions:

```text
MODID-devkit-MCVER-LOADER-VERSION.zip
```

Where:

* `MODID` = `jsmacros`
* `MCVER`, `LOADER`, `VERSION` as above.

Examples:

* Release
  `jsmacros-devkit-1.21-fabric-1.3.0.zip`

* Alpha nightly
  `jsmacros-devkit-1.21-fabric-1.3.0-alpha.20251211.zip`

* Dev
  `jsmacros-devkit-1.21-fabric-1.3.0-dev-a1b2c3d.zip`

### 7.3 Internal directory layout

The devkit zip must contain the following top-level directories:

```text
web/
typescript/
python/
```

The layout beneath these directories is not further constrained here.

### 7.4 Publishing

Devkits are produced and uploaded for **all channels**:

* dev:

  * Attached to the dev GitHub Release for that commit.
* alpha:

  * Attached to the GitHub Release.
  * Uploaded to Modrinth as an additional file for the corresponding version.
* beta:

  * Attached to the GitHub Release.
  * Uploaded to Modrinth as an additional file.
* release:

  * Attached to the GitHub Release.
  * Uploaded to Modrinth as an additional file.

---

## 8. Extensions pack zip

The extensions pack zip bundles multiple extension jars and places them into the correct path relative to the game directory.

### 8.1 File format

* File type: `.zip`

### 8.2 File name pattern

Pattern:

```text
MODID-extensions-MCVER-LOADER-VERSION.zip
```

Where `VERSION` is the same as the Gradle `version`.

Examples:

* Release
  `jsmacros-extensions-1.21-fabric-1.3.0.zip`

* Alpha nightly
  `jsmacros-extensions-1.21-neoforge-1.3.0-alpha.20251211.zip`

### 8.3 Internal directory layout

Inside the zip:

```text
config/jsMacros/extensions/
    jsmacros-ext-EXTID-MCVER-LOADER-VERSION.jar
    jsmacros-ext-OTHEREXT-MCVER-LOADER-VERSION.jar
    ...
```

Unzipping into the game directory must place extensions under `config/jsMacros/extensions`.

### 8.4 Publishing

The extensions pack zip is **required** for Modrinth publishing:

* dev:

  * Attached to the dev GitHub Release.
* alpha:

  * Attached to the GitHub Release.
  * Uploaded to Modrinth as an additional file.
* beta:

  * Attached to the GitHub Release.
  * Uploaded to Modrinth as an additional file.
* release:

  * Attached to the GitHub Release.
  * Uploaded to Modrinth as an additional file.

---

## 9. GitHub Actions expectations

GitHub Actions is responsible for building artifacts and publishing both GitHub Releases and Modrinth versions.

### 9.1 Dev builds (on every push)

* Trigger:

  * Every push to the repository (on branches selected by CI configuration).
* Outputs per `MCVER` / `LOADER` combination:

  * Base mod jar:

    * `jsmacros-MCVER-LOADER-VERSION.jar`
  * Extension jars:

    * `jsmacros-ext-EXTID-MCVER-LOADER-VERSION.jar`
  * Devkit:

    * `jsmacros-devkit-MCVER-LOADER-VERSION.zip`
  * Extensions pack:

    * `jsmacros-extensions-MCVER-LOADER-VERSION.zip`
* Publishing:

  * A dev GitHub Release (pre-release) is created or updated for that commit.
  * All artifacts are attached to that dev Release.
  * The Release description:

    * States that alpha, beta, and release builds are available on Modrinth.
    * Includes a link to the Modrinth versions page for the project.

No Modrinth publishing occurs for dev builds.

### 9.2 Alpha nightly builds

* Trigger:

  * Scheduled run daily at 3:00 AM America/New_York.
* Outputs:

  * Same artifact set and naming rules as dev builds, with `CHANNEL = alpha` and `VERSION` using the alpha pattern.
* Publishing:

  * GitHub Release created for the alpha tag corresponding to `vMODVER-alpha.DATE+MCVER`.
  * All artifacts attached.
  * Modrinth versions created or updated for each `LOADER`:

    * `version_number` includes `+fabric` or `+neoforge`.
    * Files attached as defined in section 10.

### 9.3 Beta builds

* Trigger:

  * Manually invoked GitHub Actions workflow.
* Outputs:

  * Same artifact set, with `CHANNEL = beta`.
* Publishing:

  * GitHub Release created for the beta tag corresponding to `vMODVER-beta.N+MCVER`.
  * All artifacts attached.
  * Modrinth versions created or updated per `LOADER` as in section 10.

### 9.4 Release builds

* Trigger:

  * Manually invoked GitHub Actions workflow.
* Outputs:

  * Same artifact set, with `CHANNEL = release`.
* Publishing:

  * GitHub Release created for the release tag corresponding to `vMODVER+MCVER`.
  * All artifacts attached.
  * Modrinth versions created or updated per `LOADER` as in section 10.

---

## 10. Modrinth publishing

All Modrinth publishing is handled by GitHub Actions.

### 10.1 Loader-specific versions

Because Fabric and NeoForge jars differ:

* Each `(MCVER, MODVER, CHANNEL, LOADER)` combination is a **separate Modrinth version`.
* `version_number` always includes the loader suffix (for example: `1.3.0+fabric`).

### 10.2 Common fields

For each Modrinth version:

* `version_number`:

  * `MODVER+LOADER`
  * `MODVER-beta.N+LOADER`
  * `MODVER-alpha.DATE+LOADER`
* `version_type`:

  * `alpha`, `beta`, or `release`.
* `game_versions`:

  * Array containing `MCVER`, for example `["1.21"]`.
* `loaders`:

  * `["fabric"]` or `["neoforge"]` for the base jar.

### 10.3 Attached files per Modrinth version

For a given `(MCVER, MODVER, CHANNEL, LOADER)`:

**Base mod jar**

* File name:
  `jsmacros-MCVER-LOADER-VERSION.jar`
* Marked as the main file for that Modrinth version, with `loaders` set to the appropriate loader.

**Extension jars**

* File names:
  `jsmacros-ext-EXTID-MCVER-LOADER-VERSION.jar`
* Attached as additional files.
* Each file description must state clearly:
  “Place in `config/jsMacros/extensions/` (not in `mods/`).”

**Devkit**

* File name:
  `jsmacros-devkit-MCVER-LOADER-VERSION.zip`
* Attached as an additional file.
* Intended for developers.

**Extensions pack**

* File name:
  `jsmacros-extensions-MCVER-LOADER-VERSION.zip`
* Attached as an additional file.
* Description should clarify that unzipping into the game directory installs all extensions under `config/jsMacros/extensions`.

The extensions pack zip is mandatory for all alpha, beta, and release Modrinth versions.

Dev, alpha, beta, and release builds all produce a devkit; only alpha, beta, and release are published to Modrinth.
