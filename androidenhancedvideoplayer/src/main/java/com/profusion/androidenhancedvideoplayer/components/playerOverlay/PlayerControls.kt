package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

class ControlsCustomization(
    val previousIconContent: @Composable () -> Unit = { PreviousIcon() },
    val playIconContent: @Composable () -> Unit = { PlayIcon() },
    val pauseIconContent: @Composable () -> Unit = { PauseIcon() },
    val replayIconContent: @Composable () -> Unit = { ReplayIcon() },
    val nextIconContent: @Composable () -> Unit = { NextIcon() },
    val fullScreenIconContent: @Composable () -> Unit = { FullScreenIcon() },
    val exitFullScreenIconContent: @Composable () -> Unit = { ExitFullScreenIcon() },
    val settingsIconContent: @Composable () -> Unit = { SettingsIcon() }
)

@Composable
fun PlayerControls(
    title: String? = null,
    isVisible: Boolean,
    isPlaying: Boolean,
    isFullScreen: Boolean,
    hasEnded: Boolean,
    speed: Float,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit,
    onFullScreenToggle: () -> Unit,
    onSpeedSelected: (Float) -> Unit,
    customization: ControlsCustomization,
    settingsControlsCustomization: SettingsControlsCustomization,
    modifier: Modifier = Modifier
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
                speed = speed,
                onFullScreenToggle = onFullScreenToggle,
                onSpeedSelected = onSpeedSelected,
                customization = customization,
                settingsControlsCustomization = settingsControlsCustomization
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
        speed = 1f,
        onPreviousClick = {},
        onPauseToggle = {},
        onNextClick = {},
        onFullScreenToggle = {},
        onSpeedSelected = {},
        customization = ControlsCustomization(),
        settingsControlsCustomization = SettingsControlsCustomization()
    )
}
