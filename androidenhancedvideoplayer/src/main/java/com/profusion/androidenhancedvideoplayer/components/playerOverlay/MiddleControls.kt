package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.sideControls.BrightnessControl
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.sideControls.VolumeControl

@Composable
fun MiddleControls(
    modifier: Modifier = Modifier,
    shouldShowContent: Boolean = true,
    shouldShowVolumeControl: Boolean,
    isPlaying: Boolean,
    isBuffering: Boolean,
    isFullScreen: Boolean,
    isTimeBarDragged: Boolean,
    hasEnded: Boolean,
    customization: ControlsCustomization,
    brightnessMutableInteractionSource: MutableInteractionSource,
    volumeMutableInteractionSource: MutableInteractionSource,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPauseToggle: () -> Unit,
    deviceVolume: () -> Int,
    maxVolumeValue: () -> Int,
    setDeviceVolume: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val shouldShowSideControls = !isTimeBarDragged && isFullScreen
        if (shouldShowSideControls) {
            BrightnessControl(
                modifier = Modifier.align(Alignment.CenterStart),
                customization = customization,
                brightnessMutableInteractionSource = brightnessMutableInteractionSource
            )
        }

        if (shouldShowContent) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(onClick = onPreviousClick) {
                    customization.previousIconContent()
                }
                IconButton(
                    onClick = onPauseToggle,
                    modifier = Modifier.testTag("PauseToggleButton")
                ) {
                    when {
                        hasEnded -> customization.replayIconContent()
                        isPlaying -> customization.pauseIconContent()
                        isBuffering -> customization.bufferingContent()
                        else -> customization.playIconContent()
                    }
                }
                IconButton(onClick = onNextClick) {
                    customization.nextIconContent()
                }
            }
        }
        if (shouldShowVolumeControl && shouldShowSideControls) {
            VolumeControl(
                modifier = Modifier.align(Alignment.CenterEnd),
                volume = deviceVolume,
                maxVolumeValue = maxVolumeValue,
                setDeviceVolume = setDeviceVolume,
                customization = customization,
                volumeMutableInteractionSource = volumeMutableInteractionSource
            )
        }
    }
}
