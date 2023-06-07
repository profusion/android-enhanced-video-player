package com.profusion.androidenhancedvideoplayer.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Context.setLandscape() {
    val activity = this.findActivity()
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

fun Context.setPortrait() {
    val activity = this.findActivity()
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun Modifier.fillMaxSizeOnLandscape(orientation: Int) =
    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> this.then(Modifier.fillMaxSize())
        else -> this
    }

fun Context.setStatusBarVisibility(showStatusBar: Boolean) {
    val window = this.findActivity()?.window
    window?.let {
        val windowInsetsController = WindowCompat.getInsetsController(it, it.decorView)
        if (showStatusBar) {
            windowInsetsController.show(WindowInsetsCompat.Type.statusBars())
        } else {
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        }
    }
}
