# android-enhanced-video-player

[![](https://jitpack.io/v/profusion/android-enhanced-video-player.svg)](https://jitpack.io/#profusion/android-enhanced-video-player)
[![CI Android Enhanced Video Player](https://github.com/profusion/android-enhanced-video-player/actions/workflows/android-jetpack.yml/badge.svg)](https://github.com/profusion/android-enhanced-video-player/actions/workflows/android-jetpack.yml)

Enhanced Video Player for Android built on top of Exoplayer compliant with Android Jetpack Compose

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Installation](#installation)
- [Media Types](#media-types)
  - [HLS](#hls)
  - [DASH](#dash)
  - [MSS/SmoothStreaming](#msssmoothstreaming)
- [Documentation](#documentation)

## Installation

1. Add it in your root build.gradle at the end of repositories:
  
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the dependency

```groovy
dependencies {
	implementation 'com.github.profusion:android-enhanced-video-player:Tag'
}
```

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

### DASH

Add the following module to the app's `build.gradle`

```groovy
implementation "androidx.media3:media3-exoplayer-dash:$mediaVersion"
```

When creating the `MediaItem` you can simply pass the URI if it ends with `.mpd` or you can
pass `MimeTypes.APPLICATION_MPD` to `setMimeType` of `MediaItem.Builder`

```kotlin
val mediaItem = MediaItem.Builder()
        .setMimeType(MimeTypes.APPLICATION_MPD)
        .setUri(SOME_URI)
        .build()

EnhancedVideoPlayer(mediaItem = mediaItem)
```

### MSS/SmoothStreaming

Add the following module to the app's `build.gradle`

```groovy
implementation "androidx.media3:media3-exoplayer-smoothstreaming:$mediaVersion"
```

When creating the `MediaItem` you can simply pass the URI if it ends with `.ism/Manifest` or
you can pass `MimeTypes.APPLICATION_SS` to `setMimeType` of `MediaItem.Builder`

```kotlin
val mediaItem = MediaItem.Builder()
        .setMimeType(MimeTypes.APPLICATION_SS)
        .setUri(SOME_URI)
        .build()

EnhancedVideoPlayer(mediaItem = mediaItem)
```

## Documentation

- [Contributing](docs/CONTRIBUTING.md)
- [Integrating with React Native](docs/react-native.md)
- [Releasing](docs/RELEASING.md)
