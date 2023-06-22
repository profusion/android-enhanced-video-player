package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun TimeBarComponent(
    currentTime: () -> Long,
    bufferedPosition: () -> Long,
    duration: Long,
    onTimeChangeFinished: (Long) -> Unit,
    onTimeChange: (Long) -> Unit,
    interactionSource: MutableInteractionSource
) {
    val isTimeBarDragged by interactionSource.collectIsDraggedAsState()
    var sliderValue by remember { mutableStateOf(0f) }
    val valueRange = remember(duration) { 0f..duration.toFloat() }

    Box {
        Slider(
            value = bufferedPosition().toFloat(),
            enabled = false,
            onValueChange = { },
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                disabledThumbColor = Color.Transparent,
                disabledActiveTrackColor = TimeBarTokens.BufferedTrackColor,
                disabledInactiveTrackColor = TimeBarTokens.BackgroundTrackColor
            )
        )
        Slider(
            value = if (isTimeBarDragged) sliderValue else currentTime().toFloat(),
            onValueChange = {
                sliderValue = it
                onTimeChange(it.toLong())
            },
            onValueChangeFinished = {
                onTimeChangeFinished(sliderValue.toLong())
            },
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                inactiveTrackColor = Color.Transparent
            ),
            interactionSource = interactionSource
        )
    }
}

internal object TimeBarTokens {
    val BufferedTrackColor = Color.Gray
    val BackgroundTrackColor = Color.DarkGray
}
