package com.example.androidenhancedvideoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.WindowCompat
import androidx.media3.common.MediaItem
import com.example.androidenhancedvideoplayer.components.RecommendedVideosComponent
import com.example.androidenhancedvideoplayer.ui.theme.AndroidEnhancedVideoPlayerTheme
import com.example.androidenhancedvideoplayer.utils.fillMaxSizeOnLandscape
import com.profusion.androidenhancedvideoplayer.components.EnhancedVideoPlayer
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            AndroidEnhancedVideoPlayerTheme {
                val orientation = LocalConfiguration.current.orientation
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .safeDrawingPadding()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSizeOnLandscape(orientation = orientation)
                            .fillMaxWidth()
                    ) {
                        VideoFromResources()
                    }
                    RecommendedVideosComponent()
                }
            }
        }
    }
}

@Composable
fun VideoFromURL() {
    EnhancedVideoPlayer(
        mediaItem = MediaItem.fromUri(
            "https://commondatastorage.googleapis.com/" +
                "gtv-videos-bucket/sample/ElephantsDream.mp4"
        ),
        zoomToFit = false,
        enableImmersiveMode = true,
        alwaysRepeat = false,
        settingsControlsCustomization = SettingsControlsCustomization(
            speeds = listOf(0.5f, 1f, 2f, 4f)
        )
    )
}

@Composable
fun VideoFromResources() {
    EnhancedVideoPlayer(
        resourceId = R.raw.spinning_earth,
        zoomToFit = false,
        enableImmersiveMode = true,
        disableControls = false,
        alwaysRepeat = false,
        settingsControlsCustomization = SettingsControlsCustomization(
            speeds = listOf(0.5f, 1f, 2f, 4f)
        )
    )
}
