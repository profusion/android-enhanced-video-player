package com.profusion.androidenhancedvideoplayer.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

class ControlsCustomization(
    val previousIconContent: @Composable () -> Unit = { PreviousIcon() },
    val playIconContent: @Composable () -> Unit = { PlayIcon() },
    val pauseIconContent: @Composable () -> Unit = { PauseIcon() },
    val replayIconContent: @Composable () -> Unit = { ReplayIcon() },
    val nextIconContent: @Composable () -> Unit = { NextIcon() },
    val modifier: Modifier = Modifier
)

@Composable
fun PlayerControls(
    isVisible: Boolean,
    isPlaying: Boolean,
    hasEnded: Boolean,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit,
    customization: ControlsCustomization
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.testTag("PlayerControlsParent")
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = customization.modifier
                .background(Color.Black.copy(alpha = 0.6f))
                .fillMaxSize()
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
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerControls() {
    PlayerControls(
        isVisible = true,
        isPlaying = true,
        hasEnded = false,
        onPreviousClick = {},
        onPauseToggle = {},
        onNextClick = {},
        customization = ControlsCustomization()
    )
}
