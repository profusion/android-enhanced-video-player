package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.profusion.androidenhancedvideoplayer.styling.Dimensions

@Composable
fun TopControls(
    modifier: Modifier = Modifier,
    title: String? = null,
    shouldShowContent: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.large)
    ) {
        if (title != null && shouldShowContent) {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}

@Preview
@Composable
fun TopControlsPreview() {
    TopControls(
        title = "Title"
    )
}
