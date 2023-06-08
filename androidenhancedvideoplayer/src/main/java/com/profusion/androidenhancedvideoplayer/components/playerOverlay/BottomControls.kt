package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.profusion.androidenhancedvideoplayer.styling.Dimensions
import com.profusion.androidenhancedvideoplayer.utils.formatElapsedTime

@Stable
data class Holder<T>(var value: T)

val FloatRangeSaver = Saver<Holder<ClosedFloatingPointRange<Float>>, List<Float>>(
    save = { listOf<Float>(it.value.start, it.value.endInclusive) },
    restore = { Holder(it[0]..it[1]) }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomControls(
    isFullScreen: Boolean,
    speed: Float,
    currentTime: Long,
    totalDuration: Long,
    onSpeedSelected: (Float) -> Unit,
    onFullScreenToggle: () -> Unit,
    onSeekBarValueChange: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTimeInFloat by rememberSaveable { mutableStateOf(currentTime.toFloat()) }
    val valueRange by rememberSaveable(stateSaver = FloatRangeSaver) {
        mutableStateOf(Holder(0f..totalDuration.toFloat()))
    }
    val onSeekBarValueChange = remember {
        mutableStateOf(onSeekBarValueChange)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(Dimensions.small)
    ) {
        Slider(
            value = currentTimeInFloat,
            onValueChange = { onSeekBarValueChange.value(it.toLong()) },
            valueRange = valueRange.value
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var isSettingsOpen by rememberSaveable { mutableStateOf(false) }

            Text(
                text = formatElapsedTime(currentTime, totalDuration),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White
                ),
                modifier = Modifier.absolutePadding(left = Dimensions.small)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { isSettingsOpen = !isSettingsOpen },
                modifier = Modifier.testTag("SettingsButton")
            ) {
                SettingsIcon()
            }
            IconButton(
                onClick = onFullScreenToggle,
                modifier = Modifier.testTag("FullScreenToggleButton")
            ) {
                when (isFullScreen) {
                    true -> ExitFullScreenIcon()
                    false -> FullScreenIcon()
                }
            }

            if (isSettingsOpen) {
                Settings(
                    onDismissRequest = { isSettingsOpen = false },
                    speed = speed,
                    onSpeedSelected = onSpeedSelected
                )
            }
        }
    }
}
