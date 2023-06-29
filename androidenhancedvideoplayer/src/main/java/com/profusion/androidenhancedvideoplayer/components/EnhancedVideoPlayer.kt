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
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.profusion.androidenhancedvideoplayer.R
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.PlayerControls
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SeekHandler
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.Settings
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.TimeBarPreviewComponent
import com.profusion.androidenhancedvideoplayer.utils.TimeoutEffect
import com.profusion.androidenhancedvideoplayer.utils.TrackQualityAuto
import com.profusion.androidenhancedvideoplayer.utils.TrackQualityItemListSaver
import com.profusion.androidenhancedvideoplayer.utils.TrackQualityItemSaver
import com.profusion.androidenhancedvideoplayer.utils.VolumeController
import com.profusion.androidenhancedvideoplayer.utils.fillMaxSizeOnLandscape
import com.profusion.androidenhancedvideoplayer.utils.generateTrackQualityOptions
import com.profusion.androidenhancedvideoplayer.utils.getDeviceRealSizeDp
import com.profusion.androidenhancedvideoplayer.utils.getSelectedTrackQualityItem
import com.profusion.androidenhancedvideoplayer.utils.getTopDisplayCutoutDp
import com.profusion.androidenhancedvideoplayer.utils.mapLongToIntRange
import com.profusion.androidenhancedvideoplayer.utils.resetActivityBrightnessToDefault
import com.profusion.androidenhancedvideoplayer.utils.seekIncrement
import com.profusion.androidenhancedvideoplayer.utils.setLandscape
import com.profusion.androidenhancedvideoplayer.utils.setPortrait
import com.profusion.androidenhancedvideoplayer.utils.setSystemBarVisibility
import com.profusion.androidenhancedvideoplayer.utils.setVideoQuality
import kotlinx.coroutines.delay

private const val CURRENT_TIME_TICK_IN_MS = 50L
private const val PLAYER_CONTROLS_VISIBILITY_DURATION_IN_MS = 3000L // 3 seconds
private const val DEFAULT_SEEK_TIME_MS = 10 * 1000L // 10 seconds
private const val DEFAULT_THUMBNAIL_WIDTH = 100
private const val DEFAULT_THUMBNAIL_HEIGHT = 70
private const val PREVIEW_HORIZONTAL_PADDING = 10
private const val INITIAL_PREVIEW_OFFSET = 0

// references half the pointer width of the Slider composable
private const val SLIDER_POINTER_HALF_WIDTH = 10

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
    previewThumbnailBuilder: ((timeInMillis: Long) -> ImageBitmap)? = null,
    settingsControlsCustomization: SettingsControlsCustomization = SettingsControlsCustomization()
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val volumeController = remember { VolumeController(context) }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var isBuffering by remember {
        mutableStateOf(exoPlayer.playbackState == ExoPlayer.STATE_BUFFERING)
    }
    var hasEnded by remember { mutableStateOf(exoPlayer.playbackState == ExoPlayer.STATE_ENDED) }
    var isControlsVisible by remember { mutableStateOf(false) }
    var speed by remember { mutableStateOf(exoPlayer.playbackParameters.speed) }
    var loop by remember { mutableStateOf(exoPlayer.repeatMode == ExoPlayer.REPEAT_MODE_ALL) }
    var currentTime by remember { mutableStateOf(exoPlayer.contentPosition) }
    var bufferedPosition by remember { mutableStateOf(exoPlayer.bufferedPosition) }
    var totalDuration by remember { mutableStateOf(exoPlayer.duration) }
    var title by remember {
        mutableStateOf(exoPlayer.currentMediaItem?.mediaMetadata?.title?.toString())
    }
    var currentImagePreview by remember {
        mutableStateOf(ImageBitmap(DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT))
    }
    var currentOffsetXPreview by remember { mutableStateOf(INITIAL_PREVIEW_OFFSET) }

    var deviceVolume by remember { mutableStateOf(volumeController.getDeviceVolume()) }
    val brightnessMutableInteractionSource = remember { MutableInteractionSource() }
    val isBrightnessSliderDragged by brightnessMutableInteractionSource.collectIsDraggedAsState()
    val volumeMutableInteractionSource = remember { MutableInteractionSource() }
    val isVolumeSliderDragged by volumeMutableInteractionSource.collectIsDraggedAsState()
    val timeBarMutableInteractionSource = remember { MutableInteractionSource() }
    val isTimeBarDragged by timeBarMutableInteractionSource.collectIsDraggedAsState()

    val isFullScreen = orientation == Configuration.ORIENTATION_LANDSCAPE
    var isSettingsOpen by rememberSaveable { mutableStateOf(false) }

    val autoQualityTrack by rememberSaveable(
        stateSaver = TrackQualityItemSaver
    ) {
        mutableStateOf(
            TrackQualityAuto(context.getString(R.string.settings_quality_auto))
        )
    }
    var selectedTrack by remember { mutableStateOf(autoQualityTrack) }
    var trackQualityOptions by rememberSaveable(
        stateSaver = TrackQualityItemListSaver
    ) {
        mutableStateOf(
            generateTrackQualityOptions(
                exoPlayer.currentTracks,
                autoQualityTrack
            )
        )
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
                deviceVolume = player.deviceVolume
                super.onEvents(player, events)
            }

            override fun onTracksChanged(tracks: Tracks) {
                trackQualityOptions = generateTrackQualityOptions(
                    tracks = tracks,
                    autoTrack = autoQualityTrack
                )
                super.onTracksChanged(tracks)
            }

            override fun onTrackSelectionParametersChanged(parameters: TrackSelectionParameters) {
                selectedTrack = parameters.getSelectedTrackQualityItem(
                    autoTrack = autoQualityTrack
                )
                super.onTrackSelectionParametersChanged(parameters)
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    TimeoutEffect(
        timeoutInMs = currentTimeTickInMs,
        enabled = isControlsVisible
    ) {
        currentTime = exoPlayer.currentPosition
        bufferedPosition = exoPlayer.bufferedPosition
    }

    LaunchedEffect(isFullScreen) {
        if (!isFullScreen) {
            context.resetActivityBrightnessToDefault()
        }
    }

    LaunchedEffect(enableImmersiveMode, isFullScreen) {
        if (enableImmersiveMode) {
            val shouldShowSystemUi = !isFullScreen
            context.setSystemBarVisibility(shouldShowSystemUi)
        }
    }

    LaunchedEffect(
        isControlsVisible,
        isPlaying,
        isBrightnessSliderDragged,
        isVolumeSliderDragged,
        isTimeBarDragged
    ) {
        if (
            isControlsVisible &&
            isPlaying &&
            controlsVisibilityDurationInMs > 0 &&
            !isBrightnessSliderDragged &&
            !isTimeBarDragged &&
            !isVolumeSliderDragged
        ) {
            delay(controlsVisibilityDurationInMs)
            isControlsVisible = false
        }
    }

    fun getPreviewOffsetX(draggedTime: Long): Int {
        val halfImage = currentImagePreview.width / 2
        val topDisplayCutout = context.getTopDisplayCutoutDp()
        val cutout = if (isFullScreen) topDisplayCutout else 0
        val screenWidth = context.getDeviceRealSizeDp().first
        val maxOffsetX = screenWidth - currentImagePreview.width - cutout
        val screenWidthRange =
            SLIDER_POINTER_HALF_WIDTH..(screenWidth - SLIDER_POINTER_HALF_WIDTH) - cutout
        val videoDurationRange = 0L..totalDuration

        val mappedValue = mapLongToIntRange(
            draggedTime,
            videoDurationRange,
            screenWidthRange
        )
        return (mappedValue - halfImage).coerceIn(
            PREVIEW_HORIZONTAL_PADDING,
            maxOffsetX - PREVIEW_HORIZONTAL_PADDING
        )
    }

    fun setControlsVisibility(visible: Boolean) {
        isControlsVisible = visible
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
                    seekIncrement = exoPlayer::seekIncrement,
                    disableSeekForward = hasEnded,
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
                    isTimeBarDragged = isTimeBarDragged,
                    isVolumeSliderDragged = isVolumeSliderDragged,
                    hasEnded = hasEnded,
                    brightnessMutableInteractionSource = brightnessMutableInteractionSource,
                    volumeMutableInteractionSource = volumeMutableInteractionSource,
                    timeBarMutableInteractionSource = timeBarMutableInteractionSource,
                    totalDuration = totalDuration,
                    currentTime = { currentTime },
                    bufferedPosition = { bufferedPosition },
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
                    onSeekBarValueFinished = { value ->
                        currentTime = value
                        exoPlayer.seekTo(value)
                    },
                    onSeekBarValueChange = {
                        if (previewThumbnailBuilder != null) {
                            currentOffsetXPreview = getPreviewOffsetX(it)
                            currentImagePreview = previewThumbnailBuilder(it)
                        }
                    },
                    deviceVolume = { deviceVolume },
                    maxVolumeValue = { volumeController.getMaxVolumeValue() },
                    setDeviceVolume = { volumeController.setDeviceVolume(it) },
                    shouldShowVolumeControl = !volumeController.deviceHasVolumeFixedPolicy,
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
                    selectedQualityTrack = {
                        selectedTrack
                    },
                    qualityTracks = {
                        trackQualityOptions
                    },
                    onQualityChanged = { selectedQualityTrack ->
                        exoPlayer.setVideoQuality(selectedQualityTrack)
                    },
                    customization = settingsControlsCustomization
                )
            }
            TimeBarPreviewComponent(
                modifier = Modifier.align(Alignment.BottomStart),
                shouldShowPreview = isTimeBarDragged && previewThumbnailBuilder != null,
                bitmap = currentImagePreview,
                offsetX = currentOffsetXPreview
            )
        }
    }
}
