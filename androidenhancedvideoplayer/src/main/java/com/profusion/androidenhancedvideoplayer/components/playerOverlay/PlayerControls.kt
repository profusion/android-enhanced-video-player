package com.profusion.androidenhancedvideoplayer.components.playerOverlay

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
    val replayIconContent: @Composable () -> Unit = { ReplayIcon() },
    val nextIconContent: @Composable () -> Unit = { NextIcon() },
    val fullScreenIconContent: @Composable () -> Unit = { FullScreenIcon() },
    val exitFullScreenIconContent: @Composable () -> Unit = { ExitFullScreenIcon() },
    val settingsIconContent: @Composable () -> Unit = { SettingsIcon() },
    val forwardIconContent: @Composable (modifier: Modifier) -> Unit = { ForwardIcon(it) },
    val rewindIconContent: @Composable (modifier: Modifier) -> Unit = { RewindIcon(it) }
)

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    title: String? = null,
    isVisible: Boolean,
    isPlaying: Boolean,
    isFullScreen: Boolean,
    hasEnded: Boolean,
    currentTime: () -> Long,
    totalDuration: Long,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit,
    onFullScreenToggle: () -> Unit,
    onSettingsToggle: () -> Unit,
    onSeekBarValueChange: (Long) -> Unit,
    customization: ControlsCustomization
) {
    PlayerControlsScaffold(
        modifier = modifier.testTag("PlayerControlsParent"),
        isVisible = isVisible,
        topContent = {
            TopControls(
                title = title
            )
        },
        bottomContent = {
            BottomControls(
                isFullScreen = isFullScreen,
                currentTime = currentTime,
                totalDuration = totalDuration,
                onFullScreenToggle = onFullScreenToggle,
                onSettingsToggle = onSettingsToggle,
                onSeekBarValueChange = onSeekBarValueChange,
                customization = customization
            )
        }
    ) {
        MiddleControls(
            modifier = Modifier.weight(1f),
            isPlaying = isPlaying,
            hasEnded = hasEnded,
            customization = customization,
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
        isFullScreen = false,
        currentTime = { 0L },
        totalDuration = 0L,
        onPreviousClick = {},
        onPauseToggle = {},
        onNextClick = {},
        onFullScreenToggle = {},
        onSettingsToggle = {},
        onSeekBarValueChange = {},
        customization = ControlsCustomization()
    )
}
