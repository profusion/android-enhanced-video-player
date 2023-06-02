package com.profusion.androidenhancedvideoplayer.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEFAULT_TIME_TO_SEEK_MS = 10 * 1000L // 10 seconds
private const val MAIN_PACKAGE_PATH_PREFIX = "android.resource://"

@Composable
fun EnhancedVideoPlayer(
    resourceId: Int,
    alwaysRepeat: Boolean = true,
    fullScreen: Boolean = true,
    playImmediately: Boolean = true,
    soundOff: Boolean = true,
    controlsCustomization: ControlsCustomization = ControlsCustomization(),
) {
    val context = LocalContext.current
    val mainPackagePath = "$MAIN_PACKAGE_PATH_PREFIX${context.packageName}/"
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build().apply {
                setMediaItem(
                    MediaItem.fromUri(
                        Uri.parse(mainPackagePath + resourceId.toString()),
                    ),
                )
                volume = if (soundOff) 0f else 1f
                repeatMode = if (alwaysRepeat) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
                playWhenReady = playImmediately
                prepare()
            }
    }

    val scope = rememberCoroutineScope()

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var hasEnded by remember { mutableStateOf(exoPlayer.playbackState == ExoPlayer.STATE_ENDED) }
    var isControlsVisible by remember { mutableStateOf(false) }
    var shouldShowForwardIcon by remember { mutableStateOf(false) }

    fun onSeekFoward(tapCount: Int) {
        if (!exoPlayer.currentTimeline.isEmpty) {
            shouldShowForwardIcon = true
            scope.launch {
                delay(50)
                shouldShowForwardIcon = false
            }
            exoPlayer.seekTo(exoPlayer.currentPosition + (DEFAULT_TIME_TO_SEEK_MS * tapCount))
        }
    }

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
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    VideoPlayer(
        exoPlayer = exoPlayer,
        fullScreen = fullScreen,
        onPlayerClick = { isControlsVisible = !isControlsVisible },
    )

    PlayerControls(
        isVisible = isControlsVisible,
        isPlaying = isPlaying,
        hasEnded = hasEnded,
        onPlayerClick = { isControlsVisible = !isControlsVisible },
        onPreviousClick = exoPlayer::seekToPrevious,
        onNextClick = exoPlayer::seekToNext,
        onPauseToggle = when {
            hasEnded -> exoPlayer::seekToDefaultPosition
            isPlaying -> exoPlayer::pause
            else -> exoPlayer::play
        },
        customization = controlsCustomization,
        onSeekForward = { tapCount -> onSeekFoward(tapCount) },
        onSeekBackward = { tapCount -> Log.d("SEEK", "onSeekBackward Called") },
        shouldShowBackwardIcon = false,
        shouldShowForwardIcon = shouldShowForwardIcon
    )
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
private fun VideoPlayer(
    exoPlayer: ExoPlayer,
    fullScreen: Boolean,
    onPlayerClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onPlayerClick
            )
            .testTag("VideoPlayerParent")
    ) {
        AndroidView(
            factory = { factoryContext ->
                PlayerView(factoryContext).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = if (fullScreen) {
                        AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    } else {
                        AspectRatioFrameLayout.RESIZE_MODE_FIT
                    }
                }
            }
        )
    }
}
