package com.example.androidenhancedvideoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.WindowCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import com.example.androidenhancedvideoplayer.components.RecommendedVideosComponent
import com.example.androidenhancedvideoplayer.ui.theme.AndroidEnhancedVideoPlayerTheme
import com.example.androidenhancedvideoplayer.utils.ExampleUrl
import com.example.androidenhancedvideoplayer.utils.fillMaxSizeOnLandscape
import com.profusion.androidenhancedvideoplayer.components.EnhancedVideoPlayer
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    private lateinit var exoPlayer: ExoPlayer
    private val isInPictureInPictureMode: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private fun initializeExoPlayer() {
        exoPlayer = ExoPlayer
            .Builder(applicationContext)
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(ExampleUrl.HLS))
                playWhenReady = true
                addAnalyticsListener(EventLogger())
                prepare()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        initializeExoPlayer()

        setContent {
            AndroidEnhancedVideoPlayerTheme {
                val orientation = LocalConfiguration.current.orientation

                val isInPictureInPictureModeState = isInPictureInPictureMode.collectAsState()

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
                        EnhancedVideoPlayer(
                            exoPlayer = exoPlayer,
                            zoomToFit = false,
                            enableImmersiveMode = true,
                            disableControls = isInPictureInPictureModeState.value,
                            settingsControlsCustomization = SettingsControlsCustomization(
                                speeds = listOf(0.5f, 1f, 2f, 4f)
                            )
                        )
                    }
                    RecommendedVideosComponent()
                }
            }
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()

        if (exoPlayer.isPlaying) {
            enterPictureInPictureMode(
                android.app.PictureInPictureParams.Builder()
                    .setAspectRatio(android.util.Rational(16, 9))
                    .build()
            )
        }
    }

    override fun onPictureInPictureModeChanged(
        value: Boolean,
        newConfig: android.content.res.Configuration
    ) {
        super.onPictureInPictureModeChanged(value, newConfig)
        isInPictureInPictureMode.value = value
    }
}
