package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.profusion.androidenhancedvideoplayer.R
import com.profusion.androidenhancedvideoplayer.styling.Dimensions
import com.profusion.androidenhancedvideoplayer.utils.TrackQualityItem
import kotlinx.coroutines.launch

// At least we should have two tracks, one for AUTO and another for the
// actual bitrate quality to be played
private const val MIN_TRACKS_SIZE = 2

@Stable
data class SettingsControlsCustomization(
    val speeds: List<Float> = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f),
    val speedIconContent: @Composable () -> Unit = { SpeedIcon() },
    val repeatIconContent: @Composable () -> Unit = { RepeatIcon() },
    val qualityIconContent: @Composable () -> Unit = { QualityIcon() },
    val modifier: Modifier = Modifier
)

@ExperimentalMaterial3Api
@Composable
fun Settings(
    speed: Float,
    isLoopEnabled: Boolean,
    onDismissRequest: () -> Unit,
    onSpeedSelected: (Float) -> Unit,
    onIsLoopEnabledSelected: (Boolean) -> Unit,
    selectedQualityTrack: () -> TrackQualityItem,
    qualityTracks: () -> List<TrackQualityItem>,
    onQualityChanged: (TrackQualityItem) -> Unit,
    customization: SettingsControlsCustomization
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isQualitySelectorVisible = remember(qualityTracks().size) {
        qualityTracks().size > MIN_TRACKS_SIZE
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = customization.modifier.testTag("SettingsControlsParent")
    ) {
        SettingsSelector(
            label = stringResource(id = R.string.settings_speed),
            icon = customization.speedIconContent,
            value = speed,
            items = customization.speeds,
            onSelected = onSpeedSelected
        )
        if (isQualitySelectorVisible) {
            SettingsSelector(
                label = stringResource(id = R.string.settings_quality),
                icon = customization.qualityIconContent,
                value = selectedQualityTrack(),
                items = qualityTracks(),
                onSelected = onQualityChanged
            )
        }
        SettingsSelector(
            label = stringResource(id = R.string.settings_repeat),
            icon = customization.repeatIconContent,
            value = isLoopEnabled,
            onSelected = onIsLoopEnabledSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun <T>SettingsSelector(
    label: String,
    value: T,
    items: List<T>,
    onSelected: (T) -> Unit,
    icon: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isSelectorOpen by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ListItem(
        headlineContent = { Text(label) },
        leadingContent = icon,
        trailingContent = { Text(value.toString()) },
        modifier = modifier
            .clickable(onClick = { isSelectorOpen = true })
            .testTag("${label}SettingsButton")
    )

    if (isSelectorOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSelectorOpen = false },
            sheetState = sheetState,
            windowInsets = WindowInsets.systemBarsIgnoringVisibility,
            modifier = Modifier.testTag("${label}SettingsSelector")
        ) {
            LazyColumn {
                items(items) { item ->
                    ListItem(
                        headlineContent = { Text(text = item.toString()) },
                        leadingContent = {
                            val iconSize = Dimensions.xlarge
                            if (item == value) {
                                CheckIcon(
                                    modifier = Modifier.size(iconSize)
                                )
                            } else {
                                Box(Modifier.size(iconSize))
                            }
                        },
                        modifier = Modifier.clickable {
                            onSelected(item)
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                isSelectorOpen = false
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSelector(
    label: String,
    value: Boolean,
    onSelected: (Boolean) -> Unit,
    icon: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currentOnSelected by rememberUpdatedState(onSelected)

    ListItem(
        headlineContent = { Text(label) },
        leadingContent = icon,
        trailingContent = {
            Text(
                text = if (value) {
                    stringResource(id = R.string.settings_on)
                } else {
                    stringResource(id = R.string.settings_off)
                }
            )
        },
        modifier = modifier
            .clickable(onClick = { currentOnSelected(!value) })
            .testTag("${label}SettingsButton")
    )
}
