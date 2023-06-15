package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

private val DEFAULT_HEIGHT = 100.dp
private const val ROTATION_DEGREES = 270f

@Composable
fun VerticalSlider(
    modifier: Modifier = Modifier,
    initialValue: Float,
    interactionSource: MutableInteractionSource,
    onValueChange: (value: Float) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null
) {
    var value by remember { mutableStateOf(initialValue) }

    Slider(
        modifier = modifier
            .rotate(ROTATION_DEGREES).width(DEFAULT_HEIGHT),
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        onValueChangeFinished = onValueChangeFinished,
        interactionSource = interactionSource
    )
}
