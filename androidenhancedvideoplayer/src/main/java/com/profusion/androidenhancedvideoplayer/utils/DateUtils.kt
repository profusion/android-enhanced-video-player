package com.profusion.androidenhancedvideoplayer.utils

const val MILLISECONDS_PER_SECOND = 1000
const val SECONDS_PER_MINUTE = 60

fun formatElapsedTime(value: Long): String {
    val seconds = value.millisecondsToSeconds % SECONDS_PER_MINUTE
    val minutes = value.millisecondsToMinutes % SECONDS_PER_MINUTE
    val hours = value.millisecondsToHours

    return when {
        value < 0 -> "--:--"
        hours > 0 -> "%02d:%02d:%02d".format(hours, minutes, seconds)
        else -> "%02d:%02d".format(minutes, seconds)
    }
}

fun formatElapsedTime(current: Long, total: Long): String {
    return "${formatElapsedTime(current)} / ${formatElapsedTime(total)}"
}

val Long.millisecondsToSeconds: Long
    get() = this / MILLISECONDS_PER_SECOND
val Long.millisecondsToMinutes: Long
    get() = this / (MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE)
val Long.millisecondsToHours: Long
    get() = this / (MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE * SECONDS_PER_MINUTE)
