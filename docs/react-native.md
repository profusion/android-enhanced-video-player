# React Native Guide Use

## Minimal Requirements

- Kotlin: 1.8.22
- Gradle: 7.6.1

**Warning: This guide uses the older architecture of React Native, Android UI Components.**

## Setup

### Setting `android/build.gradle`

```groovy
buildscript {
    ext {
        buildToolsVersion = "33.0.0"
        minSdkVersion = 26
        compileSdkVersion = 33
        targetSdkVersion = 33
        kotlinVersion = "1.8.22"
        mediaVersion = "1.0.2"
    }
    repositories {
        google()
        mavenCentral()
        maven {
            url 'https://maven.google.com'
            name 'Google'
        }
        maven { url 'https://plugins.gradle.org/m2/' }
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath('com.android.tools.build:gradle:7.4.2')
    }
}
```

### Setting `android/app/build.gradle`

In `android/app/build.gradle`, add the following lines in `android` and `dependencies`:

```groovy
android {
    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }

    buildFeatures {
        compose true
    }
}

dependencies {
    implementation "com.github.profusion:android-enhanced-video-player:0.2.0"
    implementation "androidx.media3:media3-exoplayer:$mediaVersion"
    implementation "androidx.media3:media3-exoplayer-dash:$mediaVersion"
    implementation "androidx.media3:media3-ui:$mediaVersion"

    def composeBom = platform('androidx.compose:compose-bom:2023.05.01')
    implementation composeBom
    androidTestImplementation composeBom
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    debugImplementation 'androidx.compose.ui:ui-tooling'
}
```

# How to setup the component in Android React Native

1. Add the ComposeView in the xml file in `android/app/src/main/res/layout`
2. Create your component
3. Create the component's manager
4. Create the component's package
5. Import the component's package in `getPackages()` in `android/app/src/main/java/com/rngql/MainApplication.java`

### 1. Add ComposeView in xml file at `android/app/src/main/res/layout`

Consider the file is called `my-xml-file.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<merge xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <FrameLayout
        android:id="@+id/player_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <androidx.compose.ui.platform.ComposeView
            android:id="@+id/compose_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </FrameLayout>
</merge>
```

### 2. Create component's view

```kotlin
class NativeVideoView(context: ThemedReactContext) : RelativeLayout(context) {
    private val composeView: ComposeView
    private val player: ExoPlayer
    var uri: String? = null
        set(value) {
            field = value
            updateUri()
        }

    // Connects the view with the xml file
    init {
        inflate(context, R.layout.my-xml-file, this)
        player = ExoPlayer.Builder(context).build()
        composeView = findViewById(R.id.compose_view)
        initContent()
    }

    // Set the Exoplayer to play a certain uri with a mp4 file
    private fun updateUri() {
        player.setMediaItem(MediaItem.fromUri(Uri.parse(uri)))
        player.prepare()
    }

    // Responsible to stop media functions if the player is not visible
    fun onDropViewInstance() {
        player.release()
    }

    // Create an instance of EnhancedVideoPlayer which is linked with Exoplayer
    private fun initContent() {
        composeView.setContent {
            EnhancedVideoPlayer(
                exoPlayer = player,
            )
        }
    }
}

```

### 3. Create component's manager

```kotlin
class NativeVideoViewManager : SimpleViewManager<NativeVideoView>() {
    // Defines the name of component used in React Native
    override fun getName() = "VideoView"

    override fun createViewInstance(reactContext: ThemedReactContext): NativeVideoView {
        return NativeVideoView(reactContext)
    }

    // Defines the prop to be used in React Native
    @ReactProp(name = "uri")
    fun setUri(view: NativeVideoView, uri: String?) {
        view.uri = uri
    }

    // If the view is not available, it'll release the player
    override fun onDropViewInstance(view: NativeVideoView) {
        view.onDropViewInstance()
    }
}

```

### 4. Create component's package

```kotlin
class NativeVideoViewPackage : ReactPackage {
    override fun createViewManagers(reactContext: ReactApplicationContext): List<NativeVideoViewManager> {
        return listOf(NativeVideoViewManager())
    }

    override fun createNativeModules(p0: ReactApplicationContext): MutableList<NativeModule> {
        return mutableListOf()
    }
}
```

### 5. Import package in MainApplication

```kotlin
public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost =
    new DefaultReactNativeHost(this) {
      @Override
          protected List<ReactPackage> getPackages() {
            List<ReactPackage> packages = new PackageList(this).getPackages();
            packages.add(new NativeVideoViewPackage());
            return packages;
          }
```

## Usage in React Native

First of all, make sure to clean the cache and build the Android application.

In order to import the components we need to use `requireNativeComponent`.

```typescript
import { requireNativeComponent } from 'react-native';

interface NativeMediaPlayerProps {
  style?: StyleProp<ViewStyle>;
  uri?: string | null;
}

const NativeVideoView =
  requireNativeComponent<NativeMediaPlayerProps>('VideoView');

const NativeMediaPlayer = ({
  style,
  uri,
}: NativeMediaPlayerProps): JSX.Element => {
  return (
    <NativeVideoView style={style} uri={uri} />
  );
};

export default NativeMediaPlayer;
```

## References

- [Compose in Views](https://developer.android.com/jetpack/compose/migrate/interoperability-apis/compose-in-views)
- [Android Native UI Components](https://reactnative.dev/docs/native-components-android)
- [Stackoverflow: Android: Jetpack Compose and XML in Activity](https://stackoverflow.com/questions/65648904/android-jetpack-compose-and-xml-in-activity)
