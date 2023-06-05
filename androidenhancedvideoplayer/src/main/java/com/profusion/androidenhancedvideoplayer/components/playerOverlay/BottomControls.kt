package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.profusion.androidenhancedvideoplayer.styling.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomControls(
    speed: Float,
    onSpeedSelected: (Float) -> Unit,
    isFullScreen: Boolean,
    onFullScreenToggle: () -> Unit,
    customization: ControlsCustomization,
    settingsControlsCustomization: SettingsControlsCustomization,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(Dimensions.small)
    ) {
        var isSettingsOpen by rememberSaveable { mutableStateOf(false) }

        IconButton(
            onClick = { isSettingsOpen = !isSettingsOpen },
            modifier = Modifier.testTag("SettingsButton")
        ) {
            customization.settingsIconContent()
        }
        IconButton(
            onClick = onFullScreenToggle,
            modifier = Modifier.testTag("FullScreenToggleButton")
        ) {
            when (isFullScreen) {
                true -> customization.exitFullScreenIconContent()
                false -> customization.fullScreenIconContent()
            }
        }

        if (isSettingsOpen) {
            Settings(
                onDismissRequest = { isSettingsOpen = false },
                speed = speed,
                onSpeedSelected = onSpeedSelected,
                customization = settingsControlsCustomization
            )
        }
    }
}
