package com.profusion.androidenhancedvideoplayer.utils

import android.content.Context
import android.media.AudioManager

private const val HIDE_VISUAL_FEEDBACK = 0

class VolumeController(context: Context) {
    private val audioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val deviceHasVolumeFixedPolicy = audioManager.isVolumeFixed

    fun setDeviceVolume(volume: Int) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, HIDE_VISUAL_FEEDBACK)
    }
    fun getDeviceVolume(): Int {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    fun getMaxVolumeValue(): Int {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }
}
