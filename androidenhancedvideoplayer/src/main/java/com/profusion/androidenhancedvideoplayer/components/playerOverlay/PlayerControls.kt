package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

@Stable
data class ControlsCustomization(
    val previousIconContent: @Composable () -> Unit = { PreviousIcon() },
    val playIconContent: @Composable () -> Unit = { PlayIcon() },
    val pauseIconContent: @Composable () -> Unit = { PauseIcon() },
    val bufferingContent: @Composable () -> Unit = { CircularProgressIndicator() },
    val replayIconContent: @Composable () -> Unit = { ReplayIcon() },
    val nextIconContent: @Composable () -> Unit = { NextIcon() },
    val fullScreenIconContent: @Composable () -> Unit = { FullScreenIcon() },
    val exitFullScreenIconContent: @Composable () -> Unit = { ExitFullScreenIcon() },
    val settingsIconContent: @Composable () -> Unit = { SettingsIcon() },
    val brightnessLowIconContent: @Composable () -> Unit = { BrightnessLowIcon() },
    val brightnessHighIconContent: @Composable () -> Unit = { BrightnessHighIcon() },
    val brightnessMedIconContent: @Composable () -> Unit = { BrightnessMedIcon() },
    val forwardIconContent: @Composable (modifier: Modifier) -> Unit = { ForwardIcon(it) },
    val rewindIconContent: @Composable (modifier: Modifier) -> Unit = { RewindIcon(it) }
)

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    title: String? = null,
    isVisible: Boolean,
    isPlaying: Boolean,
    isBuffering: Boolean,
    isFullScreen: Boolean,
    isBrightnessSliderDragged: Boolean,
    isTimeBarDragged: Boolean,
    hasEnded: Boolean,
    brightnessMutableInteractionSource: MutableInteractionSource,
    timeBarMutableInteractionSource: MutableInteractionSource,
    currentTime: () -> Long,
    bufferedPosition: () -> Long,
    totalDuration: Long,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit,
    onFullScreenToggle: () -> Unit,
    onSettingsToggle: () -> Unit,
    onSeekBarValueFinished: (Long) -> Unit,
    onSeekBarValueChange: (Long) -> Unit,
    customization: ControlsCustomization
) {
    PlayerControlsScaffold(
        modifier = modifier.testTag("PlayerControlsParent"),
        isVisible = isVisible,
        isFullScreen = isFullScreen,
        isBrightnessSliderDragged = isBrightnessSliderDragged,
        topContent = {
            TopControls(
                modifier = it,
                title = title,
                shouldShowContent = !isBrightnessSliderDragged
            )
        },
        bottomContent = {
            BottomControls(
                modifier = it,
                shouldShowContent = !isBrightnessSliderDragged,
                isFullScreen = isFullScreen,
                currentTime = currentTime,
                bufferedPosition = bufferedPosition,
                totalDuration = totalDuration,
                onFullScreenToggle = onFullScreenToggle,
                onSettingsToggle = onSettingsToggle,
                timeBarMutableInteractionSource = timeBarMutableInteractionSource,
                onSeekBarValueFinished = onSeekBarValueFinished,
                onSeekBarValueChange = onSeekBarValueChange,
                customization = customization
            )
        }
    ) {
        MiddleControls(
            modifier = it,
            shouldShowContent = !isBrightnessSliderDragged && !isTimeBarDragged,
            isTimeBarDragged = isTimeBarDragged,
            isPlaying = isPlaying,
            isBuffering = isBuffering,
            isFullScreen = isFullScreen,
            hasEnded = hasEnded,
            customization = customization,
            brightnessMutableInteractionSource = brightnessMutableInteractionSource,
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
            onPauseToggle = onPauseToggle
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerControls() {
    PlayerControls(
        title = "Really long title that should be truncated",
        isVisible = true,
        isPlaying = true,
        hasEnded = false,
        isBrightnessSliderDragged = false,
        isTimeBarDragged = false,
        isFullScreen = false,
        currentTime = { 0L },
        bufferedPosition = { 50L },
        totalDuration = 100L,
        brightnessMutableInteractionSource = MutableInteractionSource(),
        onPreviousClick = {},
        onPauseToggle = {},
        onNextClick = {},
        onFullScreenToggle = {},
        onSettingsToggle = {},
        onSeekBarValueFinished = {},
        onSeekBarValueChange = {},
        customization = ControlsCustomization(),
        timeBarMutableInteractionSource = MutableInteractionSource(),
        isBuffering = false
    )
}
