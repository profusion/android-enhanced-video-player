package com.profusion.androidenhancedvideoplayer.components.playerOverlay.sideControls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization

private const val MIN_VOLUME_VALUE = 0

fun mapVolumeIconStateToIcon(
    state: IconState,
    customization: ControlsCustomization
): @Composable () -> Unit {
    return when (state) {
        IconState.MED -> customization.volumeMedIconContent
        IconState.OFF -> customization.volumeOffIconContent
        IconState.HIGH -> customization.volumeHighIconContent
    }
}

@Composable
fun VolumeControl(
    modifier: Modifier = Modifier,
    volume: () -> Int,
    maxVolumeValue: () -> Int,
    setDeviceVolume: (Int) -> Unit,
    customization: ControlsCustomization,
    volumeMutableInteractionSource: MutableInteractionSource
) {
    val maxVolumeLocal = maxVolumeValue()
    val halfVolume = maxVolumeLocal / 2
    val lowRange = MIN_VOLUME_VALUE + 1..halfVolume
    val highRange = halfVolume + 1..maxVolumeLocal
    val volumeIconState = remember(volume()) {
        mapRangeToIconState(value = volume(), lowRange = lowRange, highRange = highRange)
    }
    val volumeIcon = mapVolumeIconStateToIcon(volumeIconState, customization)

    SliderControl(
        modifier = modifier,
        topIcon = volumeIcon,
        sliderValue = volume().toFloat(),
        sliderValueRange = MIN_VOLUME_VALUE.toFloat()..maxVolumeValue().toFloat(),
        onSliderValueChange = { setDeviceVolume(it.toInt()) },
        sliderInteractionSource = volumeMutableInteractionSource
    )
}
