package com.profusion.androidenhancedvideoplayer.utils

import androidx.media3.common.Player

fun Player.seekIncrement(incrementMs: Long) {
    seekTo(currentPosition + incrementMs)
}
