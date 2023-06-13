# android-enhanced-video-player
Enhanced Video Player for Android built on top of Exoplayer compliant with Android Jetpack Compose

[![](https://jitpack.io/v/profusion/android-enhanced-video-player.svg)](https://jitpack.io/#profusion/android-enhanced-video-player)

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Setup](#setup)
  - [Pre-requirements](#pre-requirements)
  - [Install Node](#install-node)
  - [Install Ruby Dependencies](#install-ruby-dependencies)
  - [Install JS Dependencies](#install-js-dependencies)
  - [Install Ktlint to IDE](#install-ktlint-to-ide)
- [Running the project](#running-the-project)
- [Documentation](#documentation)

## Setup

### Pre-requirements

For the javascript runtime, we recommend [Node.js](https://nodejs.org/en/).

We **strongly** suggest you to use [NVM \(Node Version Manager\)](https://github.com/nvm-sh/nvm) and also doing [this configuration](https://github.com/nvm-sh/nvm#deeper-shell-integration).

We **strongly** suggest you to use [rbenv (Managing Ruby environment)](https://github.com/rbenv/rbenv).

To ensure compatibility with [Android Gradle plugin](https://developer.android.com/studio/releases#android_gradle_plugin_and_android_studio_compatibility), it is mandatory to use Android Studio Flamingo or any subsequent version.

We use yarn for dependency management, install instructions are available at https://classic.yarnpkg.com/lang/en/.

### Install Node

```console
$ nvm install
$ nvm use
```

Every time you use the repo you will need to use `nvm use` in your terminal to commit

### Install Ruby Dependencies

```console
$ rbenv install
$ bundle install
```

This will install Ruby and fastlane, used for linting the project.

### Install JS Dependencies

```console
$ yarn install
```

This will install husky pre-commit, pre-push and msg-commit hooks.

### Install Ktlint to IDE
Make sure to open this project with Android Studio at least once before proceeding with ktlint instalation. That guarantees some hidden files/folders (like `.idea`) exist and can be configured by the following command. Navigate to the project’s directory in the terminal and execute:
```console
ktlint applyToIDEAProject
```

This will change Android Studio's code format configurations. The next time you apply an automatic code formatting on a kotlin file, it will use our new set of rules defined by ktlint.

**Pro Tip:** use a hotkey to make Android Studio format your file for you. You can choose your hotkey on `File > Settings > Keymap > Main Menu > Code > Reformat Code & Reformat File`

## Running the project

To run the project, open it in the IDE, Android Studio, and execute it.

## Media Types

Exoplayer supports the three main adaptive streaming algorithms: HLS, DASH and MSS.
However, this project does not add the modules responsible for that in the library
module so we can lower the size of the distributed package.

Any apps that import `EnhancedVideoPlayer` can still play URIs from adaptive
streaming algorithms. To do that, one can import the necessary module's on the
app's `build.gradle` as described below.

### HLS

Add the following module to the app's `build.gradle`

```groovy
implementation "androidx.media3:media3-exoplayer-hls:$mediaVersion"
```

When creating the `MediaItem`, simply pass the HLS URI

```kotlin
EnhancedVideoPlayer(
  mediaItem = MediaItem.fromUri(
    "https://demo-streaming.gcdn.co/videos/676_YJHUNNocCsrjiCDx/master.m3u8"
  )
)
```

## Documentation

 - [Releasing](docs/RELEASING.md)
