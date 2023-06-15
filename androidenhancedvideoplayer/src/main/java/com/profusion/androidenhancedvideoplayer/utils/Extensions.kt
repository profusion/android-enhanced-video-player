package com.profusion.androidenhancedvideoplayer.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

private const val MED_BRIGHTNESS = 0.5f

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

fun Context.setNavigationBarVisibility(showNavigationBar: Boolean) {
    val window = this.findActivity()?.window
    window?.let {
        val windowInsetsController = WindowCompat.getInsetsController(it, it.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT

        if (showNavigationBar) {
            windowInsetsController.show(WindowInsetsCompat.Type.navigationBars())
        } else {
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        }
    }
}
fun Context.setActivityBrightness(brightness: Float) {
    val window = this.findActivity()?.window

    window?.let {
        val layout: WindowManager.LayoutParams? = it.attributes
        layout?.screenBrightness = brightness.coerceIn(0f, 1f)
        it.attributes = layout
    }
}

fun Context.resetActivityBrightnessToDefault() {
    val window = this.findActivity()?.window

    window?.let {
        val layout: WindowManager.LayoutParams? = it.attributes
        layout?.screenBrightness = BRIGHTNESS_OVERRIDE_NONE
        layout?.buttonBrightness = BRIGHTNESS_OVERRIDE_NONE
        it.attributes = layout
    }
}

fun Context.getActivityBrightnessOrDefault(default: Float = MED_BRIGHTNESS): Float {
    val window = this.findActivity()?.window
    val brightness = window?.attributes?.screenBrightness ?: default
    return if (brightness < 0) default else brightness
}
