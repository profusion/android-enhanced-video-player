package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.profusion.androidenhancedvideoplayer.styling.Dimensions
import com.profusion.androidenhancedvideoplayer.utils.getActivityBrightnessOrDefault
import com.profusion.androidenhancedvideoplayer.utils.setActivityBrightness

private val LOW_RANGE = 0.0f..0.33f
private val HIGH_RANGE = 0.66f..1.0f

enum class BrightnessIconState {
    HIGH,
    MED,
    LOW
}

fun mapBrightnessRangeToState(brightness: Float): BrightnessIconState {
    return when (brightness) {
        in LOW_RANGE -> BrightnessIconState.LOW
        in HIGH_RANGE -> BrightnessIconState.HIGH
        else -> BrightnessIconState.MED
    }
}

fun mapBrightnessIconStateToIcon(
    state: BrightnessIconState,
    customization: ControlsCustomization
): @Composable () -> Unit {
    return when (state) {
        BrightnessIconState.LOW -> customization.brightnessLowIconContent
        BrightnessIconState.MED -> customization.brightnessMedIconContent
        BrightnessIconState.HIGH -> customization.brightnessHighIconContent
    }
}

@Composable
fun BrightnessControl(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean,
    customization: ControlsCustomization,
    brightnessMutableInteractionSource: MutableInteractionSource
) {
    if (isFullScreen) {
        val context = LocalContext.current
        var brightnessIconState by remember {
            mutableStateOf(
                mapBrightnessRangeToState(
                    context.getActivityBrightnessOrDefault()
                )
            )
        }

        val brightnessIcon = mapBrightnessIconStateToIcon(brightnessIconState, customization)

        fun onSliderChange(brightness: Float) {
            context.setActivityBrightness(brightness)
            brightnessIconState = mapBrightnessRangeToState(brightness)
        }

        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            brightnessIcon()
            Spacer(modifier = Modifier.height(Dimensions.xxlarge))
            VerticalSlider(
                initialValue = context.getActivityBrightnessOrDefault(),
                onValueChange = ::onSliderChange,
                interactionSource = brightnessMutableInteractionSource
            )
        }
    }
}
