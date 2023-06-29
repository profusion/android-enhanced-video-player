package com.profusion.androidenhancedvideoplayer.components.playerOverlay.sideControls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

val DEFAULT_SLIDER_RANGE = 0f..1f
private val DEFAULT_HEIGHT = 100.dp
private const val ROTATION_DEGREES = 270f

@Composable
fun VerticalSlider(
    modifier: Modifier = Modifier,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float> = DEFAULT_SLIDER_RANGE,
    interactionSource: MutableInteractionSource,
    onValueChange: (value: Float) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null
) {
    Slider(
        modifier = modifier
            .rotate(ROTATION_DEGREES).width(DEFAULT_HEIGHT),
        value = value,
        valueRange = valueRange,
        onValueChange = onValueChange,
        onValueChangeFinished = onValueChangeFinished,
        interactionSource = interactionSource
    )
}
