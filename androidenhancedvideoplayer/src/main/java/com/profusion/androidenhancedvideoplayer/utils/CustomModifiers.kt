package com.profusion.androidenhancedvideoplayer.utils

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.fillMaxSizeOnLandscape(orientation: Int) =
    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> this.then(Modifier.fillMaxSize())
        else -> this
    }

fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    clickable(
        onClick = onClick,
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }
    )
}
