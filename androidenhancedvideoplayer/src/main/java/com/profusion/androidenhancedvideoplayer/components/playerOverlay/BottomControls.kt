package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp


@Composable
fun BottomControls(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean,
    onFullScreenToggle: () -> Unit,
    customization: ControlsCustomization
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .absolutePadding(4.dp)
    ) {
        IconButton(
            onClick = onFullScreenToggle,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .testTag("FullScreenToggleButton")
        ) {
            when (isFullScreen) {
                true -> customization.exitFullScreenIconContent()
                false -> customization.fullScreenIconContent()
            }
        }
    }
}
