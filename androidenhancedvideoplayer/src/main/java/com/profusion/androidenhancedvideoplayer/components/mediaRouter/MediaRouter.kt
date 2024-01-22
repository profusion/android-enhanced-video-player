package com.profusion.androidenhancedvideoplayer.components.mediaRouter

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.cast.framework.CastContext
import com.profusion.androidenhancedvideoplayer.styling.Dimensions

@Composable
fun MediaRouter(context: Context, modifier: Modifier = Modifier) {
    val viewModel = MediaRouterViewModel(context)
    val castContext = CastContext.getSharedInstance()

    if (castContext != null) {
        Box(modifier = modifier) {
            AndroidView(
                factory = { viewModel.mediaRouteButton() },
                modifier = Modifier.size(Dimensions.xlarge)
            )
        }
    }
}
