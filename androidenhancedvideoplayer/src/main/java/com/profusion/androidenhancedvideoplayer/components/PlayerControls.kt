package com.profusion.androidenhancedvideoplayer.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.profusion.androidenhancedvideoplayer.R

class ControlsCustomization(
    val previousIconContent: @Composable () -> Unit = { DefaultPreviousIcon() },
    val playIconContent: @Composable () -> Unit = { DefaultPlayIcon() },
    val pauseIconContent: @Composable () -> Unit = { DefaultPauseIcon() },
    val replayIconContent: @Composable () -> Unit = { DefaultReplayIcon() },
    val nextIconContent: @Composable () -> Unit = { DefaultNextIcon() },
    val modifier: Modifier = Modifier
)

@Composable
fun PlayerControls(
    isVisible: Boolean,
    isPlaying: Boolean,
    hasEnded: Boolean,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit,
    customization: ControlsCustomization
) {
    SeekForwardRewindLayout {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.testTag("PlayerControlsParent")
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = customization.modifier
                    .background(Color.Black.copy(alpha = 0.6f))
                    .fillMaxSize()
            ) {
                IconButton(onClick = onPreviousClick) {
                    customization.previousIconContent()
                }
                IconButton(
                    onClick = onPauseToggle,
                    modifier = Modifier.testTag("PauseToggleButton")
                ) {
                    when {
                        hasEnded -> customization.replayIconContent()
                        isPlaying -> customization.pauseIconContent()
                        else -> customization.playIconContent()
                    }
                }
                IconButton(onClick = onNextClick) {
                    customization.nextIconContent()
                }
            }
        }
    }
}

@Composable
fun SeekForwardRewindLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
        content()
    }
}

@Composable
fun DefaultSeekFowardIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_fast_forward),
        contentDescription = stringResource(id = R.string.controls_fast_foward_description)
    )
}

@Composable
fun DefaultSeekBackwardIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_fast_backward),
        contentDescription = stringResource(id = R.string.controls_fast_backward_description)
    )
}

@Composable
fun DefaultPreviousIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_skip_previous),
        contentDescription = stringResource(id = R.string.controls_previous_description)
    )
}

@Composable
private fun DefaultPlayIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_play),
        contentDescription = stringResource(id = R.string.controls_play_description),
        modifier = Modifier.testTag("PlayIcon")
    )
}

@Composable
private fun DefaultPauseIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_pause),
        contentDescription = stringResource(id = R.string.controls_pause_description),
        modifier = Modifier.testTag("PauseIcon")
    )
}

@Composable
private fun DefaultReplayIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_replay),
        contentDescription = stringResource(id = R.string.controls_replay_description),
        modifier = Modifier.testTag("ReplayIcon")
    )
}

@Composable
private fun DefaultNextIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_skip_next),
        contentDescription = stringResource(R.string.controls_next_description)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerControls() {
    PlayerControls(
        isVisible = true,
        isPlaying = true,
        hasEnded = false,
        onPreviousClick = {},
        onPauseToggle = {},
        onNextClick = {},
        customization = ControlsCustomization()
    )
}
