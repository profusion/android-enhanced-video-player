package com.profusion.androidenhancedvideoplayer.utils

import androidx.compose.runtime.Stable
import androidx.media3.common.TrackGroup
import androidx.media3.common.util.UnstableApi

sealed interface TrackQualityItem

@UnstableApi
@Stable
data class TrackQualityAuto(
    private val label: String
) : TrackQualityItem {
    override fun toString(): String {
        return label
    }
}

@UnstableApi
@Stable
data class TrackQualityItemValue(
    val trackIndex: Int,
    val group: TrackGroup
) : Comparable<TrackQualityItemValue>, TrackQualityItem {

    private val format get() = group.getFormat(trackIndex)

    override fun compareTo(other: TrackQualityItemValue) = compareValuesBy(
        this,
        other,
        { it.format.height },
        { it.format.bitrate }
    )

    override fun toString(): String {
        return format.buildQualityLabel()
    }
}
