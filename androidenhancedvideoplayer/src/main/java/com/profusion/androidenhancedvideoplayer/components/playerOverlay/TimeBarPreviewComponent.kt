package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

private val OFFSET_Y = (-100).dp
private val BORDER_WIDTH = 1.dp

@Composable
fun TimeBarPreviewComponent(
    modifier: Modifier = Modifier,
    shouldShowPreview: Boolean,
    bitmap: ImageBitmap,
    offsetX: Int
) {
    if (shouldShowPreview) {
        Box(
            modifier = modifier
                .offset(x = offsetX.dp, y = OFFSET_Y)
                .width(bitmap.width.dp)
                .height(bitmap.height.dp)
                .background(Color.Black)
                .border(width = BORDER_WIDTH, color = Color.White, shape = RectangleShape)
        ) {
            Image(bitmap = bitmap, contentDescription = null)
        }
    }
}
