package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun MiddleControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    hasEnded: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPauseToggle: () -> Unit
) {
    var onPreviousClick = remember { mutableStateOf(onPreviousClick) }
    var onNextClick = remember { mutableStateOf(onNextClick) }
    var onPauseToggle = remember { mutableStateOf(onPauseToggle) }
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
            IconButton(onClick = onPreviousClick.value) {
                PreviousIcon()
            }
            IconButton(
                onClick = onPauseToggle.value,
                modifier = Modifier.testTag("PauseToggleButton")
            ) {
                when {
                    hasEnded -> ReplayIcon()
                    isPlaying -> PauseIcon()
                    else -> PlayIcon()
                }
            }
            IconButton(onClick = onNextClick.value) {
                NextIcon()
            }
        }
    }
}
