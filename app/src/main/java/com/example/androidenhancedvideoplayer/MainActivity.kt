package com.example.androidenhancedvideoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.WindowCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.androidenhancedvideoplayer.components.RecommendedVideosComponent
import com.example.androidenhancedvideoplayer.ui.theme.AndroidEnhancedVideoPlayerTheme
import com.example.androidenhancedvideoplayer.utils.ExampleUrl
import com.example.androidenhancedvideoplayer.utils.fillMaxSizeOnLandscape
import com.profusion.androidenhancedvideoplayer.components.EnhancedVideoPlayer
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization

class MainActivity : ComponentActivity() {
    private lateinit var exoPlayer: ExoPlayer

    private fun initializeExoPlayer() {
        exoPlayer = ExoPlayer
            .Builder(applicationContext)
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(ExampleUrl.RESOURCE))
                playWhenReady = true
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
}
