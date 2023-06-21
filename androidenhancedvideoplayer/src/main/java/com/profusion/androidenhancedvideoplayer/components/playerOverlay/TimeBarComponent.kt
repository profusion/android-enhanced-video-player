package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable

@Composable
fun TimeBarComponent(
    currentTime: () -> Long,
    duration: Long,
    onTimeChange: (Long) -> Unit,
    onTimeChangeFinished: () -> Unit,
    interactionSource: MutableInteractionSource
) {
    Slider(
        value = currentTime().toFloat().coerceIn(0f, Float.MAX_VALUE),
        onValueChange = {
            onTimeChange(it.toLong().coerceIn(0, Long.MAX_VALUE))
        },
        onValueChangeFinished = onTimeChangeFinished,
        valueRange = 0f..duration.toFloat().coerceIn(1f, Float.MAX_VALUE),
        interactionSource = interactionSource
    )
}
