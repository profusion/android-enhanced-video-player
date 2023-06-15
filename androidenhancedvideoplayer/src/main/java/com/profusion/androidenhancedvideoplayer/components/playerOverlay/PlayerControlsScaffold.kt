package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.profusion.androidenhancedvideoplayer.styling.Colors

private const val TOP_CONTENT_WEIGHT = 0.2f
private const val MIDDLE_CONTENT_WEIGHT_FULLSCREEN = 0.7f
private const val MIDDLE_CONTENT_WEIGHT_PORTRAIT = 0.35f
private const val BOTTOM_CONTENT_WEIGHT_FULLSCREEN = 0.3F
private const val BOTTOM_CONTENT_WEIGHT_PORTRAIT = 0.45F

@Composable
fun PlayerControlsScaffold(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isFullScreen: Boolean,
    isBrightnessSliderDragged: Boolean,
    topContent: @Composable ColumnScope.(modifier: Modifier) -> Unit,
    bottomContent: @Composable ColumnScope.(modifier: Modifier) -> Unit,
    content: @Composable ColumnScope.(modifier: Modifier) -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
            .background(if (isBrightnessSliderDragged) Color.Transparent else Colors.controlsShadow)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            topContent(Modifier.weight(TOP_CONTENT_WEIGHT))
            content(
                Modifier.weight(
                    if (isFullScreen) {
                        MIDDLE_CONTENT_WEIGHT_FULLSCREEN
                    } else { MIDDLE_CONTENT_WEIGHT_PORTRAIT }
                )
            )
            bottomContent(
                Modifier.weight(
                    if (isFullScreen) {
                        BOTTOM_CONTENT_WEIGHT_FULLSCREEN
                    } else { BOTTOM_CONTENT_WEIGHT_PORTRAIT }
                )
            )
        }
    }
}
