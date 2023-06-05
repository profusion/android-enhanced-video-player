package com.example.androidenhancedvideoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidenhancedvideoplayer.ui.theme.AndroidEnhancedVideoPlayerTheme
import com.profusion.androidenhancedvideoplayer.components.EnhancedVideoPlayer
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidEnhancedVideoPlayerTheme {
                EnhancedVideoPlayer(
                    resourceId = R.raw.login_screen_background,
                    alwaysRepeat = false,
                    settingsControlsCustomization = SettingsControlsCustomization(
                        speeds = listOf(0.5f, 1f, 2f, 4f)
                    )
                )
            }
        }
    }
}
