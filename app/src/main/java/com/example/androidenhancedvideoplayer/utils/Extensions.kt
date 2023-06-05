package com.example.androidenhancedvideoplayer.utils

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

fun Modifier.fillMaxSizeOnLandscape(orientation: Int) =
    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> this.then(Modifier.fillMaxSize())
        else -> this
    }
