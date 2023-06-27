package com.profusion.androidenhancedvideoplayer.utils

import android.os.Bundle
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.media3.common.TrackGroup
import androidx.media3.common.util.UnstableApi
import com.google.common.collect.ImmutableList

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

object TrackQualityItemKey {
    const val LABEL = "label"
    const val GROUP = "group"
    const val TRACK_INDEX = "track"
    const val TYPE = "type"
    const val TYPE_AUTO = "auto"
    const val TYPE_ITEM_VALUE = "value"
}

@UnstableApi
fun saveTrackQualityItem(item: TrackQualityItem): Map<String, Any> {
    return when (item) {
        is TrackQualityItemValue -> {
            mapOf(
                TrackQualityItemKey.TYPE to TrackQualityItemKey.TYPE_ITEM_VALUE,
                TrackQualityItemKey.GROUP to item.group.toBundle(),
                TrackQualityItemKey.TRACK_INDEX to item.trackIndex
            )
        }
        is TrackQualityAuto -> {
            mapOf(
                TrackQualityItemKey.TYPE to TrackQualityItemKey.TYPE_AUTO,
                TrackQualityItemKey.LABEL to item.toString()
            )
        }
    }
}

@UnstableApi
fun restoreTrackQualityItem(map: Map<String, Any?>): TrackQualityItem {
    return when (map[TrackQualityItemKey.TYPE] as String) {
        TrackQualityItemKey.TYPE_ITEM_VALUE -> {
            TrackQualityItemValue(
                trackIndex = map[TrackQualityItemKey.TRACK_INDEX] as Int,
                group = TrackGroup.CREATOR.fromBundle(map[TrackQualityItemKey.GROUP] as Bundle)
            )
        }
        else -> {
            TrackQualityAuto(map[TrackQualityItemKey.LABEL] as String)
        }
    }
}

@UnstableApi
val TrackQualityItemSaver = run {
    mapSaver(
        save = {
            saveTrackQualityItem(it)
        },
        restore = {
            restoreTrackQualityItem(it)
        }
    )
}

@UnstableApi
fun saveTrackQualityItemList(trackQualityItemList: List<TrackQualityItem>): List<Any> =
    trackQualityItemList.map { saveTrackQualityItem(it) }

@UnstableApi
fun restoreTrackQualityItemList(list: List<Any>): List<TrackQualityItem> =
    ImmutableList.copyOf(list.map { restoreTrackQualityItem(it as Map<String, Any>) })

@UnstableApi
val TrackQualityItemListSaver = run {
    listSaver(
        save = { list -> saveTrackQualityItemList(list) },
        restore = { list -> restoreTrackQualityItemList(list) }
    )
}
