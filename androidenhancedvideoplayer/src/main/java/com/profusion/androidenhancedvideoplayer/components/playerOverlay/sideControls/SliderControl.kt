package com.profusion.androidenhancedvideoplayer.components.playerOverlay.sideControls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.profusion.androidenhancedvideoplayer.styling.Dimensions

@Composable
fun SliderControl(
    modifier: Modifier = Modifier,
    topIcon: @Composable () -> Unit,
    sliderValue: Float = 0f,
    sliderValueRange: ClosedFloatingPointRange<Float> = DEFAULT_SLIDER_RANGE,
    onSliderValueChange: (Float) -> Unit,
    sliderInteractionSource: MutableInteractionSource
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topIcon()
        Spacer(modifier = Modifier.height(Dimensions.xxlarge))
        VerticalSlider(
            value = sliderValue,
            valueRange = sliderValueRange,
            interactionSource = sliderInteractionSource,
            onValueChange = onSliderValueChange
        )
    }
}
