package com.profusion.androidenhancedvideoplayer.components.playerOverlay.sideControls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.utils.getActivityBrightnessOrDefault
import com.profusion.androidenhancedvideoplayer.utils.setActivityBrightness

private val MED_RANGE = 0.33f..0.65f
private val HIGH_RANGE = 0.66f..1.0f

fun mapBrightnessIconStateToIcon(
    state: IconState,
    customization: ControlsCustomization
): @Composable () -> Unit {
    return when (state) {
        IconState.OFF -> customization.brightnessLowIconContent
        IconState.MED -> customization.brightnessMedIconContent
        IconState.HIGH -> customization.brightnessHighIconContent
    }
}

@Composable
fun BrightnessControl(
    modifier: Modifier = Modifier,
    customization: ControlsCustomization,
    brightnessMutableInteractionSource: MutableInteractionSource
) {
    val context = LocalContext.current
    var sliderValue by remember { mutableStateOf(context.getActivityBrightnessOrDefault()) }
    var brightnessIconState = remember(sliderValue) {
        mapRangeToIconState(
            value = context.getActivityBrightnessOrDefault(),
            lowRange = MED_RANGE,
            highRange = HIGH_RANGE
        )
    }

    val brightnessIcon = mapBrightnessIconStateToIcon(brightnessIconState, customization)

    SliderControl(
        modifier = modifier,
        sliderValue = sliderValue,
        onSliderValueChange = {
            context.setActivityBrightness(it)
            sliderValue = it
        },
        sliderInteractionSource = brightnessMutableInteractionSource,
        topIcon = brightnessIcon
    )
}
