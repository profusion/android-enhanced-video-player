package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.profusion.androidenhancedvideoplayer.styling.Dimensions
import com.profusion.androidenhancedvideoplayer.utils.formatElapsedTime

@Composable
fun BottomControls(
    isFullScreen: Boolean,
    currentTime: () -> Long,
    totalDuration: Long,
    onSettingsToggle: () -> Unit,
    onFullScreenToggle: () -> Unit,
    onSeekBarValueChange: (Long) -> Unit,
    customization: ControlsCustomization,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(Dimensions.small)
    ) {
        TimeBarComponent(
            currentTime = currentTime,
            duration = totalDuration,
            onTimeChange = onSeekBarValueChange
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatElapsedTime(currentTime(), totalDuration),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White
                ),
                modifier = Modifier.absolutePadding(left = Dimensions.small)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onSettingsToggle,
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
        }
    }
}
