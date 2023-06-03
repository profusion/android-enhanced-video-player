package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun MiddleControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    hasEnded: Boolean,
    customization: ControlsCustomization,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPauseToggle: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
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
