package com.example.androidenhancedvideoplayer.components

import androidx.compose.runtime.Composable
import com.profusion.androidenhancedvideoplayer.components.EnhancedVideoPlayer

@Composable
fun VideoPlayerComponent(
    resourceId: Int,
    useControls: Boolean = false,
    alwaysRepeat: Boolean = true,
    fullScreen: Boolean = true,
    playImmediately: Boolean = true,
    soundOff: Boolean = true
) {
    EnhancedVideoPlayer(
        resourceId = resourceId,
        useControls = useControls,
        alwaysRepeat = alwaysRepeat,
        fullScreen = fullScreen,
        playImmediately = playImmediately,
        soundOff = soundOff
    )
}
