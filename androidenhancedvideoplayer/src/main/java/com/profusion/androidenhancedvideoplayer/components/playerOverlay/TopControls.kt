package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.profusion.androidenhancedvideoplayer.components.mediaRouter.MediaRouter
import com.profusion.androidenhancedvideoplayer.styling.Dimensions

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun TopControls(
    modifier: Modifier = Modifier,
    title: String? = null,
    disableCast: Boolean = false,
    shouldShowContent: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.large)
    ) {
        if (shouldShowContent) {
            if (title != null) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Spacer(modifier = Modifier.fillMaxWidth())
            }

            if (!disableCast) {
                LocalContext.current.setTheme(
                    androidx.appcompat.R.style.Theme_AppCompat_NoActionBar
                )
                MediaRouter(
                    context = LocalContext.current,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
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
