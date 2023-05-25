package com.profusion.androidenhancedvideoplayer.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

private const val MAIN_PACKAGE_PATH_PREFIX = "android.resource://"

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun EnhancedVideoPlayer(
    resourceId: Int,
    useControls: Boolean = false,
    alwaysRepeat: Boolean = true,
    fullScreen: Boolean = true,
    playImmediately: Boolean = true,
    soundOff: Boolean = true
) {
    val context = LocalContext.current
    val mainPackagePath = "$MAIN_PACKAGE_PATH_PREFIX${context.packageName}/"
    val exoPlayer = ExoPlayer.Builder(context).build()
    LaunchedEffect(Unit) {
        exoPlayer.apply {
            setMediaItem(
                MediaItem.fromUri(
                    Uri.parse(mainPackagePath + resourceId.toString())
                )
            )
            volume = if (soundOff) 0f else 1f
            repeatMode = if (alwaysRepeat) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
            playWhenReady = playImmediately
            prepare()
        }
    }

    AndroidView(
        factory = { factoryContext ->
            PlayerView(factoryContext).apply {
                player = exoPlayer
                useController = useControls
                resizeMode = if (fullScreen) {
                    AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                } else {
                    AspectRatioFrameLayout.RESIZE_MODE_FIT
                }
            }
        }
    )
}
