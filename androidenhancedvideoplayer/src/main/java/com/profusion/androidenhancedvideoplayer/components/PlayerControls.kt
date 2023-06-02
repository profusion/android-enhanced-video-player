package com.profusion.androidenhancedvideoplayer.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ControlsCustomization(
    val previousIconContent: @Composable () -> Unit = { PreviousIcon() },
    val playIconContent: @Composable () -> Unit = { PlayIcon() },
    val pauseIconContent: @Composable () -> Unit = { PauseIcon() },
    val replayIconContent: @Composable () -> Unit = { ReplayIcon() },
    val nextIconContent: @Composable () -> Unit = { NextIcon() },
    val fullScreenIconContent: @Composable () -> Unit = { FullScreenIcon() },
    val exitFullScreenIconContent: @Composable () -> Unit = { ExitFullScreenIcon() },
    val modifier: Modifier = Modifier
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
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.testTag("PlayerControlsParent")
    ) {
        Column(
            modifier = customization.modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                IconButton(onClick = onPreviousClick) {
                    customization.previousIconContent()
                }
                IconButton(
                    onClick = onPauseToggle,
                    modifier = Modifier.testTag("PauseToggleButton")
                ) {
                    when {
                        hasEnded -> customization.replayIconContent()
                        isPlaying -> customization.pauseIconContent()
                        else -> customization.playIconContent()
                    }
                }
                IconButton(onClick = onNextClick) {
                    customization.nextIconContent()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .absolutePadding(bottom = 16.dp, right = 16.dp)
            ) {
                IconButton(
                    onClick = onFullScreenToggle,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .testTag("FullScreenToggleButton")
                ) {
                    when (isFullScreen) {
                        true -> customization.exitFullScreenIconContent()
                        false -> customization.fullScreenIconContent()
                    }
                }
            }
        }
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
