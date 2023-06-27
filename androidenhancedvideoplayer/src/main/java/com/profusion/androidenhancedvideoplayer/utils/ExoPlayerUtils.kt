package com.profusion.androidenhancedvideoplayer.utils

import androidx.media3.exoplayer.ExoPlayer

fun ExoPlayer.seekIncrement(incrementMs: Long) {
    seekTo(currentPosition + incrementMs)
}
