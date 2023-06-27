package com.profusion.androidenhancedvideoplayer.utils

import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.google.common.collect.ImmutableList

private const val NO_ROLE_FLAGS = 0

@OptIn(UnstableApi::class)
fun generateTrackQualityOptions(
    tracks: Tracks,
    autoTrack: TrackQualityItem
): ImmutableList<TrackQualityItem> {
    val qualityOptions = ArrayList<TrackQualityItemValue>()

    for (group in tracks.groups) {
        for (trackIndex in 0 until group.length) {
            if (group.isTrackSupported(trackIndex) &&
                group.mediaTrackGroup.type == C.TRACK_TYPE_VIDEO &&
                group.getTrackFormat(trackIndex).isVideoTrackPlayable()
            ) {
                qualityOptions += TrackQualityItemValue(
                    group = group.mediaTrackGroup,
                    trackIndex = trackIndex
                )
            }
        }
    }

    return ImmutableList.copyOf(listOf(autoTrack) + qualityOptions.sortedDescending())
}

@UnstableApi
fun TrackSelectionParameters.getChangedTrackSelection(
    trackType: Int
): TrackSelectionOverride? = overrides.values.firstOrNull { it.type == trackType }

@UnstableApi
fun TrackSelectionParameters.getSelectedTrackQualityItem(
    autoTrack: TrackQualityItem
): TrackQualityItem {
    val videoTrackOverride = getChangedTrackSelection(C.TRACK_TYPE_VIDEO)
        ?: return autoTrack

    val index = videoTrackOverride.trackIndices.single()
        ?: return autoTrack

    val group = videoTrackOverride.mediaTrackGroup

    return TrackQualityItemValue(
        group = group,
        trackIndex = index
    )
}

fun ExoPlayer.setVideoQuality(track: TrackQualityItem) {
    trackSelectionParameters = when (track) {
        is TrackQualityAuto -> {
            trackSelectionParameters
                .buildUpon()
                .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
                .build()
        }
        is TrackQualityItemValue -> {
            trackSelectionParameters
                .buildUpon()
                .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
                .addOverride(
                    TrackSelectionOverride(
                        track.group,
                        track.trackIndex
                    )
                )
                .build()
        }
    }
}

@OptIn(UnstableApi::class)
fun Format.buildQualityLabel(): String {
    return "${height}p - ${bitrate / 1000}kbps"
}

@OptIn(UnstableApi::class)
private fun Format.isVideoTrackPlayable(): Boolean {
    return roleFlags == NO_ROLE_FLAGS
}
