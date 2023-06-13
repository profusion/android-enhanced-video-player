package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable

@Composable
fun TimeBarComponent(
    currentTime: Long,
    duration: Long,
    onTimeChange: (Long) -> Unit
) {
    Slider(
        value = currentTime.toFloat().coerceIn(0f, Float.MAX_VALUE),
        onValueChange = {
            onTimeChange(it.toLong().coerceIn(0, Long.MAX_VALUE))
        },
        valueRange = 0f..duration.toFloat().coerceIn(1f, Float.MAX_VALUE)
    )
}
