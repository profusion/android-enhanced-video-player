package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    isSeeking: Boolean,
    disableSeekClick: Boolean = false,
    onSeekSingleTap: () -> Unit,
    onSeekDoubleTap: () -> Unit,
    checkIfCanToggleIsControlsVisible: () -> Unit,
    getSeekTime: () -> Int,
    seekIcon: @Composable (modifier: Modifier) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .pointerInput(isSeeking, disableSeekClick) {
                detectTapGestures(
                    onTap = when {
                        !isSeeking -> { { checkIfCanToggleIsControlsVisible() } }
                        !disableSeekClick -> { { onSeekSingleTap() } }
                        else -> null
                    },
                    onDoubleTap = if (!isSeeking && !disableSeekClick) {
                        { onSeekDoubleTap() }
                    } else { null }
                )
            }
            .testTag("SeekClickableArea")
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if (isSeeking) {
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
