package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.profusion.androidenhancedvideoplayer.R
import com.profusion.androidenhancedvideoplayer.styling.Dimensions

private const val TEXT_MAX_LINES = 2

@Composable
fun SeekClickableArea(
    modifier: Modifier = Modifier,
    scaleAnimation: Float,
    tapCount: Int,
    disableSeekClick: Boolean = false,
    onSeekSingleTap: () -> Unit,
    onSeekDoubleTap: () -> Unit,
    checkIfCanToggleIsControlsVisible: () -> Unit,
    getSeekTime: () -> Int,
    seekIcon: @Composable (modifier: Modifier) -> Unit
) {
    val isTapCountGreaterThanZero = tapCount > 0
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .indication(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .pointerInput(tapCount, disableSeekClick) {
                detectTapGestures(
                    onTap = if (isTapCountGreaterThanZero) {
                        if (disableSeekClick) {
                            null
                        } else { { onSeekSingleTap() } }
                    } else {
                        { checkIfCanToggleIsControlsVisible() }
                    },
                    onDoubleTap = if (isTapCountGreaterThanZero) { null } else {
                        if (disableSeekClick) {
                            null
                        } else {
                            { onSeekDoubleTap() }
                        }
                    }
                )
            }
            .testTag("SeekClickableArea")
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if (isTapCountGreaterThanZero) {
            val timeLabel = getSeekTime()
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                seekIcon(modifier = Modifier.scale(scaleAnimation))
                Spacer(modifier = Modifier.height(Dimensions.large))
                Text(
                    text = "$timeLabel  ${stringResource(id = R.string.controls_time_unit)}",
                    maxLines = TEXT_MAX_LINES,
                    color = Color.White
                )
            }
        }
    }
}
