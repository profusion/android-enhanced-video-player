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
    val exitFullScreenIconContent: @Composable () -> Unit = { ExitFullScreenIcon() }
)

@Composable
fun PlayerControls(
    isVisible: Boolean,
    isPlaying: Boolean,
    isFullScreen: Boolean,
    hasEnded: Boolean,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit,
    onFullScreenToggle: () -> Unit,
    customization: ControlsCustomization,
    modifier: Modifier = Modifier
) {
    PlayerControlsScaffold(
        modifier = modifier.testTag("PlayerControlsParent"),
        isVisible = isVisible,
        topContent = { /* TODO */ },
        bottomContent = {
            BottomControls(
                isFullScreen = isFullScreen,
                onFullScreenToggle = onFullScreenToggle,
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
        isVisible = true,
        isPlaying = true,
        hasEnded = false,
        isFullScreen = false,
        onPreviousClick = {},
        onPauseToggle = {},
        onNextClick = {},
        onFullScreenToggle = {},
        customization = ControlsCustomization()
    )
}
