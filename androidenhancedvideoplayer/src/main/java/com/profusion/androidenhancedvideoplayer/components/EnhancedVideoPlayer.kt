package com.profusion.androidenhancedvideoplayer.components

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.PlayerControls
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SeekHandler
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.Settings
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization
import com.profusion.androidenhancedvideoplayer.utils.TimeoutEffect
import com.profusion.androidenhancedvideoplayer.utils.fillMaxSizeOnLandscape
import com.profusion.androidenhancedvideoplayer.utils.resetActivityBrightnessToDefault
import com.profusion.androidenhancedvideoplayer.utils.setLandscape
import com.profusion.androidenhancedvideoplayer.utils.setNavigationBarVisibility
import com.profusion.androidenhancedvideoplayer.utils.setPortrait
import com.profusion.androidenhancedvideoplayer.utils.setStatusBarVisibility
import kotlinx.coroutines.delay

private const val CURRENT_TIME_TICK_IN_MS = 50L
private const val PLAYER_CONTROLS_VISIBILITY_DURATION_IN_MS = 3000L // 3 seconds
private const val DEFAULT_SEEK_TIME_MS = 10 * 1000L // 10 seconds

@OptIn(ExperimentalMaterial3Api::class)
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun EnhancedVideoPlayer(
    exoPlayer: ExoPlayer,
    zoomToFit: Boolean = true,
    enableImmersiveMode: Boolean = true,
    disableControls: Boolean = false,
    currentTimeTickInMs: Long = CURRENT_TIME_TICK_IN_MS,
    controlsVisibilityDurationInMs: Long = PLAYER_CONTROLS_VISIBILITY_DURATION_IN_MS,
    controlsCustomization: ControlsCustomization = ControlsCustomization(),
    transformSeekIncrementRatio: (tapCount: Int) -> Long = { it -> it * DEFAULT_SEEK_TIME_MS },
    settingsControlsCustomization: SettingsControlsCustomization = SettingsControlsCustomization()
) {
    val context = LocalContext.current
    val orientation = LocalConfiguration.current.orientation

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var isBuffering by remember {
        mutableStateOf(exoPlayer.playbackState == ExoPlayer.STATE_BUFFERING)
    }
    var hasEnded by remember { mutableStateOf(exoPlayer.playbackState == ExoPlayer.STATE_ENDED) }
    var isControlsVisible by remember { mutableStateOf(false) }
    var speed by remember { mutableStateOf(exoPlayer.playbackParameters.speed) }
    var loop by remember { mutableStateOf(exoPlayer.repeatMode == ExoPlayer.REPEAT_MODE_ALL) }
    var currentTime by remember { mutableStateOf(exoPlayer.contentPosition) }
    var totalDuration by remember { mutableStateOf(exoPlayer.duration) }
    var title by remember {
        mutableStateOf(exoPlayer.currentMediaItem?.mediaMetadata?.title?.toString())
    }

    val brightnessMutableInteractionSource = remember { MutableInteractionSource() }
    val isBrightnessSliderDragged by brightnessMutableInteractionSource.collectIsDraggedAsState()
    val timeBarMutableInteractionSource = remember { MutableInteractionSource() }
    val isTimeBarDragged by timeBarMutableInteractionSource.collectIsDraggedAsState()

    val isFullScreen = orientation == Configuration.ORIENTATION_LANDSCAPE
    var isSettingsOpen by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isFullScreen) {
        if (!isFullScreen) {
            context.resetActivityBrightnessToDefault()
        }
    }

    LaunchedEffect(enableImmersiveMode, isFullScreen) {
        if (enableImmersiveMode) {
            val shouldShowSystemUi = !isFullScreen
            context.setStatusBarVisibility(shouldShowSystemUi)
            context.setNavigationBarVisibility(shouldShowSystemUi)
        }
    }

    fun setControlsVisibility(visible: Boolean) {
        isControlsVisible = visible
    }

    DisposableEffect(context) {
        val listener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                isPlaying = player.isPlaying
                isBuffering = player.playbackState == ExoPlayer.STATE_BUFFERING
                hasEnded = player.playbackState == ExoPlayer.STATE_ENDED
                speed = player.playbackParameters.speed
                title = player.mediaMetadata.title?.toString()
                currentTime = player.contentPosition
                totalDuration = player.duration
                loop = player.repeatMode == ExoPlayer.REPEAT_MODE_ALL
                super.onEvents(player, events)
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    LaunchedEffect(isTimeBarDragged) {
        if (isTimeBarDragged) {
            exoPlayer.pause()
        }
    }

    TimeoutEffect(
        timeoutInMs = currentTimeTickInMs,
        enabled = isPlaying && isControlsVisible
    ) {
        currentTime = exoPlayer.currentPosition
    }

    LaunchedEffect(isControlsVisible, isPlaying, isBrightnessSliderDragged) {
        if (
            isControlsVisible &&
            isPlaying &&
            controlsVisibilityDurationInMs > 0 &&
            !isBrightnessSliderDragged
        ) {
            delay(controlsVisibilityDurationInMs)
            isControlsVisible = false
        }
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSizeOnLandscape(orientation)
            .testTag("VideoPlayerParent"),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { factoryContext ->
                PlayerView(factoryContext).apply {
                    player = exoPlayer
                    useController = false
                }
            },
            update = { playerView ->
                playerView.resizeMode = if (zoomToFit) {
                    AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                } else {
                    AspectRatioFrameLayout.RESIZE_MODE_FIT
                }
            }
        )
        if (!disableControls) {
            Box(modifier = Modifier.matchParentSize()) {
                SeekHandler(
                    disableSeekForward = hasEnded,
                    isControlsVisible = isControlsVisible,
                    exoPlayer = exoPlayer,
                    controlsCustomization = controlsCustomization,
                    toggleControlsVisibility = {
                        setControlsVisibility(!isControlsVisible)
                    },
                    setControlsVisibility = ::setControlsVisibility,
                    transformSeekIncrementRatio = transformSeekIncrementRatio
                )
                PlayerControls(
                    title = title,
                    isVisible = isControlsVisible,
                    isPlaying = isPlaying,
                    isBuffering = isBuffering,
                    isFullScreen = isFullScreen,
                    isBrightnessSliderDragged = isBrightnessSliderDragged,
                    hasEnded = hasEnded,
                    brightnessMutableInteractionSource = brightnessMutableInteractionSource,
                    timeBarMutableInteractionSource = timeBarMutableInteractionSource,
                    totalDuration = totalDuration,
                    currentTime = { currentTime },
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
                    onSettingsToggle = { isSettingsOpen = !isSettingsOpen },
                    onSeekBarValueChange = { currentTime = it },
                    onSeekBarValueFinished = {
                        exoPlayer.seekTo(currentTime)
                        exoPlayer.play()
                    },
                    customization = controlsCustomization
                )
            }
            if (isSettingsOpen) {
                Settings(
                    onDismissRequest = { isSettingsOpen = false },
                    speed = speed,
                    isLoopEnabled = loop,
                    onSpeedSelected = exoPlayer::setPlaybackSpeed,
                    onIsLoopEnabledSelected = { value ->
                        exoPlayer.repeatMode = if (value) {
                            ExoPlayer.REPEAT_MODE_ALL
                        } else {
                            ExoPlayer.REPEAT_MODE_OFF
                        }
                    },
                    customization = settingsControlsCustomization
                )
            }
        }
    }
}
