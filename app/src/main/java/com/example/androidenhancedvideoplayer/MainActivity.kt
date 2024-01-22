package com.example.androidenhancedvideoplayer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.util.EventLogger
import com.example.androidenhancedvideoplayer.components.RecommendedVideosComponent
import com.example.androidenhancedvideoplayer.ui.theme.AndroidEnhancedVideoPlayerTheme
import com.example.androidenhancedvideoplayer.utils.ExampleUrl
import com.example.androidenhancedvideoplayer.utils.fillMaxSizeInLandscape
import com.example.androidenhancedvideoplayer.utils.safePaddingInPortrait
import com.google.android.gms.cast.framework.CastContext
import com.profusion.androidenhancedvideoplayer.components.EnhancedVideoPlayer
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : FragmentActivity() {
    private lateinit var exoPlayer: ExoPlayer
    private val isInPictureInPictureMode: MutableStateFlow<Boolean> = MutableStateFlow(false)

    @OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initializeExoPlayer() {
        exoPlayer = ExoPlayer
            .Builder(applicationContext)
            .setTrackSelector(
                DefaultTrackSelector(applicationContext, AdaptiveTrackSelection.Factory())
            )
            .build()
            .apply {
                setMediaItem(MediaItem.Builder().setUri(ExampleUrl.DASH).setMimeType(MimeTypes.APPLICATION_MPD).build())
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
        CastContext.getSharedInstance(this)

        setContent {
            AndroidEnhancedVideoPlayerTheme {
                val orientation = LocalConfiguration.current.orientation
                val isInPictureInPictureModeState = isInPictureInPictureMode.collectAsState()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .safePaddingInPortrait(orientation)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSizeInLandscape(orientation = orientation)
                            .fillMaxWidth()
                    ) {
                        EnhancedVideoPlayer(
                            exoPlayer = exoPlayer,
                            zoomToFit = false,
                            disableControls = isInPictureInPictureModeState.value,
                            settingsControlsCustomization = SettingsControlsCustomization(
                                speeds = listOf(0.5f, 1f, 2f, 4f)
                            ),
                            previewThumbnailBuilder = { ImageBitmap(100, 70) }
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
