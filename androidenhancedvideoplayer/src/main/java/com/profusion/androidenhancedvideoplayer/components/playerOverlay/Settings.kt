package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.profusion.androidenhancedvideoplayer.R
import com.profusion.androidenhancedvideoplayer.styling.Dimensions
import kotlinx.coroutines.launch

data class SettingsControlsCustomization(
    val speeds: List<Float> = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f),
    val speedIconContent: @Composable () -> Unit = { SpeedIcon() },
    val modifier: Modifier = Modifier
)

@ExperimentalMaterial3Api
@Composable
fun Settings(
    speed: Float,
    onDismissRequest: () -> Unit,
    onSpeedSelected: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val speedList = rememberSaveable {
        mutableStateOf(listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f))
    }
    val onSpeedSelected = remember { mutableStateOf(onSpeedSelected) }
    val onDismissRequest = remember { mutableStateOf(onDismissRequest) }
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest.value,
        sheetState = sheetState,
        modifier = modifier.testTag("SettingsControlsParent")
    ) {
        SettingsSelector(
            label = stringResource(id = R.string.settings_speed),
            icon = { SpeedIcon() },
            value = speed,
            items = speedList.value,
            onSelected = onSpeedSelected.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
    val sheetState = rememberModalBottomSheetState()
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
            modifier = Modifier.testTag("${label}SettingsSelector")
        ) {
            items.forEach { item ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewSettings() {
    Settings(
        speed = 1.0f,
        onDismissRequest = { },
        onSpeedSelected = { },
    )
}
