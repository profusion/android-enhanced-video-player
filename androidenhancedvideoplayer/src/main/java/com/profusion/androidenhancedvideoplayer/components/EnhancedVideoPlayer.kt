package com.profusion.androidenhancedvideoplayer.components

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.PlayerControls
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization
import com.profusion.androidenhancedvideoplayer.utils.setLandscape
import com.profusion.androidenhancedvideoplayer.utils.setPortrait

private const val MAIN_PACKAGE_PATH_PREFIX = "android.resource://"

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun EnhancedVideoPlayer(
    resourceId: Int,
    alwaysRepeat: Boolean = true,
    expandContent: Boolean = true,
    playImmediately: Boolean = true,
    soundOff: Boolean = true,
    controlsCustomization: ControlsCustomization = ControlsCustomization(),
    settingsControlsCustomization: SettingsControlsCustomization = SettingsControlsCustomization()
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val mainPackagePath = "$MAIN_PACKAGE_PATH_PREFIX${context.packageName}/"
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build().apply {
                setMediaItem(
                    MediaItem.fromUri(
                        Uri.parse(mainPackagePath + resourceId.toString())
                    )
                )
                volume = if (soundOff) 0f else 1f
                repeatMode = if (alwaysRepeat) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
                playWhenReady = playImmediately
                prepare()
            }
    }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var hasEnded by remember { mutableStateOf(exoPlayer.playbackState == ExoPlayer.STATE_ENDED) }
    var isControlsVisible by remember { mutableStateOf(false) }
    var speed by remember { mutableStateOf(exoPlayer.playbackParameters.speed) }

    val isFullScreen = configuration.orientation == ORIENTATION_LANDSCAPE

    DisposableEffect(context) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(value: Boolean) {
                isPlaying = value
                super.onIsPlayingChanged(value)
            }
            override fun onPlaybackStateChanged(state: Int) {
                hasEnded = state == ExoPlayer.STATE_ENDED
                super.onPlaybackStateChanged(state)
            }
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
                speed = playbackParameters.speed
                super.onPlaybackParametersChanged(playbackParameters)
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { isControlsVisible = !isControlsVisible }
            )
            .testTag("VideoPlayerParent")
    ) {
        AndroidView(
            factory = { factoryContext ->
                PlayerView(factoryContext).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = if (expandContent) {
                        AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    } else {
                        AspectRatioFrameLayout.RESIZE_MODE_FIT
                    }
                }
            }
        )
        PlayerControls(
            isVisible = isControlsVisible,
            isPlaying = isPlaying,
            isFullScreen = isFullScreen,
            hasEnded = hasEnded,
            speed = speed,
            onPreviousClick = exoPlayer::seekToPrevious,
            onNextClick = exoPlayer::seekToNext,
            onPauseToggle = when {
                hasEnded -> exoPlayer::seekToDefaultPosition
                isPlaying -> exoPlayer::pause
                else -> exoPlayer::play
            },
            onFullScreenToggle = {
                when (isFullScreen) {
                    true -> context.setPortrait()
                    false -> context.setLandscape()
                }
            },
            onSpeedSelected = exoPlayer::setPlaybackSpeed,
            customization = controlsCustomization,
            settingsControlsCustomization = settingsControlsCustomization,
            modifier = Modifier
                .matchParentSize()
                .testTag("PlayerControlsParent")
        )
    }
}
