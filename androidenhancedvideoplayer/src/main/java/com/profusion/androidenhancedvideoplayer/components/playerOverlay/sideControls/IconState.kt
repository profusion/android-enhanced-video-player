package com.profusion.androidenhancedvideoplayer.components.playerOverlay.sideControls

enum class IconState {
    HIGH,
    MED,
    OFF
}

fun <T> mapRangeToIconState(
    value: T,
    lowRange: ClosedRange<T>,
    highRange: ClosedRange<T>
): IconState
    where T : Comparable<T> {
    return when (value) {
        in lowRange -> IconState.MED
        in highRange -> IconState.HIGH
        else -> IconState.OFF
    }
}
