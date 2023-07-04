package com.example.androidenhancedvideoplayer.utils

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier

fun Modifier.fillMaxSizeInLandscape(orientation: Int) =
    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> this.then(
            Modifier.fillMaxSize()
        )
        else -> this
    }

fun Modifier.safePaddingInPortrait(orientation: Int) =
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        this.then(Modifier.safeDrawingPadding())
    } else {
        this
    }
