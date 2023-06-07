package com.profusion.androidenhancedvideoplayer.components

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.PlayerControls
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization
import com.profusion.androidenhancedvideoplayer.utils.TimeoutEffect
import com.profusion.androidenhancedvideoplayer.utils.fillMaxSizeOnLandscape
import com.profusion.androidenhancedvideoplayer.utils.setLandscape
import com.profusion.androidenhancedvideoplayer.utils.setPortrait
import com.profusion.androidenhancedvideoplayer.utils.setStatusBarVisibility

private const val MAIN_PACKAGE_PATH_PREFIX = "android.resource://"
private const val CURRENT_TIME_TICK_IN_MS = 50L

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun EnhancedVideoPlayer(
    resourceId: Int,
    alwaysRepeat: Boolean = true,
    zoomToFit: Boolean = true,
    enableImmersiveMode: Boolean = true,
    playImmediately: Boolean = true,
    soundOff: Boolean = true,
    currentTimeTickInMs: Long = CURRENT_TIME_TICK_IN_MS,
    controlsCustomization: ControlsCustomization = ControlsCustomization(),
    settingsControlsCustomization: SettingsControlsCustomization = SettingsControlsCustomization()
) {
    val context = LocalContext.current
    val mainPackagePath = "$MAIN_PACKAGE_PATH_PREFIX${context.packageName}/"

    EnhancedVideoPlayer(
        mediaItem = MediaItem.fromUri(
            Uri.parse(mainPackagePath + resourceId.toString())
        ),
        alwaysRepeat = alwaysRepeat,
        zoomToFit = zoomToFit,
        enableImmersiveMode = enableImmersiveMode,
        playImmediately = playImmediately,
        soundOff = soundOff,
        currentTimeTickInMs = currentTimeTickInMs,
        controlsCustomization = controlsCustomization,
        settingsControlsCustomization = settingsControlsCustomization
    )
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun EnhancedVideoPlayer(
    mediaItem: MediaItem,
    alwaysRepeat: Boolean = true,
    zoomToFit: Boolean = true,
    enableImmersiveMode: Boolean = true,
    playImmediately: Boolean = true,
    soundOff: Boolean = true,
    currentTimeTickInMs: Long = CURRENT_TIME_TICK_IN_MS,
    controlsCustomization: ControlsCustomization = ControlsCustomization(),
    settingsControlsCustomization: SettingsControlsCustomization = SettingsControlsCustomization()
) {
    val context = LocalContext.current

    val orientation = LocalConfiguration.current.orientation

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build().apply {
                setMediaItem(mediaItem)
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
    var currentTime by remember { mutableStateOf(exoPlayer.contentPosition) }
    var totalDuration by remember { mutableStateOf(exoPlayer.duration) }
    var title by remember {
        mutableStateOf(exoPlayer.currentMediaItem?.mediaMetadata?.displayTitle?.toString())
    }
    val isFullScreen = orientation == Configuration.ORIENTATION_LANDSCAPE

    if (enableImmersiveMode) {
        context.setStatusBarVisibility(
            showStatusBar = isControlsVisible || !isFullScreen
        )
    }

    DisposableEffect(context) {
        val listener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                isPlaying = player.isPlaying
                hasEnded = player.playbackState == ExoPlayer.STATE_ENDED
                speed = player.playbackParameters.speed
                title = player.mediaMetadata.displayTitle?.toString()
                currentTime = player.contentPosition
                totalDuration = player.duration
                super.onEvents(player, events)
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    TimeoutEffect(
        timeoutInMs = currentTimeTickInMs,
        enabled = isPlaying && isControlsVisible
    ) {
        currentTime = exoPlayer.currentPosition
    }

    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { isControlsVisible = !isControlsVisible }
            )
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
        PlayerControls(
            title = title,
            isVisible = isControlsVisible,
            isPlaying = isPlaying,
            isFullScreen = isFullScreen,
            hasEnded = hasEnded,
            speed = speed,
            totalDuration = totalDuration,
            currentTime = currentTime,
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
            onSeekBarValueChange = exoPlayer::seekTo,
            customization = controlsCustomization,
            settingsControlsCustomization = settingsControlsCustomization,
            modifier = Modifier
                .matchParentSize()
                .testTag("PlayerControlsParent")
        )
    }
}
