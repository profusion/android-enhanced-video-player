package com.profusion.androidenhancedvideoplayer.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.profusion.androidenhancedvideoplayer.R
import com.profusion.androidenhancedvideoplayer.components.customModifiers.doubleTapToSeek

class ControlsCustomization(
    val previousIconContent: @Composable () -> Unit = { DefaultPreviousIcon() },
    val playIconContent: @Composable () -> Unit = { DefaultPlayIcon() },
    val pauseIconContent: @Composable () -> Unit = { DefaultPauseIcon() },
    val replayIconContent: @Composable () -> Unit = { DefaultReplayIcon() },
    val nextIconContent: @Composable () -> Unit = { DefaultNextIcon() },
    val seekForwardContent: @Composable () -> Unit = { DefaultSeekFowardIcon() },
    val seekBackwardContent: @Composable () -> Unit = { DefaultSeekBackwardIcon() },
    val modifier: Modifier = Modifier,
)

@Composable
fun PlayerControls(
    isVisible: Boolean,
    isPlaying: Boolean,
    hasEnded: Boolean,
    shouldShowForwardIcon: Boolean,
    shouldShowBackwardIcon: Boolean,
    onSeekForward: (tapCount: Int) -> Unit,
    onSeekBackward: (tapCount: Int) -> Unit,
    onPlayerClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onNextClick: () -> Unit,
    customization: ControlsCustomization,
) {
    SeekForwardRewindLayout(
        shouldShowShadow = isVisible,
        shouldShowForwardIcon = shouldShowForwardIcon,
        shouldShowBackwardIcon = shouldShowBackwardIcon,
        onSeekForward = onSeekForward,
        onSeekBackward = onSeekBackward,
        onPlayerClick = onPlayerClick,
        forwardIcon = customization.seekForwardContent,
        backwardIcon = customization.seekBackwardContent,
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.testTag("PlayerControlsParent"),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = customization.modifier
                    .width(300.dp),
            ) {
                IconButton(onClick = onPreviousClick) {
                    customization.previousIconContent()
                }
                IconButton(
                    onClick = onPauseToggle,
                    modifier = Modifier.testTag("PauseToggleButton"),
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
    shouldShowShadow: Boolean,
    shouldShowForwardIcon: Boolean,
    shouldShowBackwardIcon: Boolean,
    onSeekForward: (tapCount: Int) -> Unit,
    onSeekBackward: (tapCount: Int) -> Unit,
    onPlayerClick: () -> Unit,
    forwardIcon: @Composable () -> Unit,
    backwardIcon: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val backgroundColor = when (shouldShowShadow) {
        true -> Color.Black.copy(alpha = 0.6f)
        false -> Color.Transparent
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { /* used to prevent trigger show/hide controls on doubleClick */ },
                    onTap = { onPlayerClick() },
                )
            }
            .background(backgroundColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .doubleTapToSeek(
                    onSeekForward = onSeekForward,
                    onSeekBackward = onSeekBackward,
                ),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                if (shouldShowBackwardIcon) {
                    backwardIcon()
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                if (shouldShowForwardIcon) {
                    forwardIcon()
                }
            }
        }
        content()
    }
}

@Composable
fun DefaultSeekFowardIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_fast_forward),
        contentDescription = stringResource(id = R.string.controls_fast_foward_description),
    )
}

@Composable
fun DefaultSeekBackwardIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_fast_backward),
        contentDescription = stringResource(id = R.string.controls_fast_backward_description),
    )
}

@Composable
fun DefaultPreviousIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_skip_previous),
        contentDescription = stringResource(id = R.string.controls_previous_description),
    )
}

@Composable
private fun DefaultPlayIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_play),
        contentDescription = stringResource(id = R.string.controls_play_description),
        modifier = Modifier.testTag("PlayIcon"),
    )
}

@Composable
private fun DefaultPauseIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_pause),
        contentDescription = stringResource(id = R.string.controls_pause_description),
        modifier = Modifier.testTag("PauseIcon"),
    )
}

@Composable
private fun DefaultReplayIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_replay),
        contentDescription = stringResource(id = R.string.controls_replay_description),
        modifier = Modifier.testTag("ReplayIcon"),
    )
}

@Composable
private fun DefaultNextIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_skip_next),
        contentDescription = stringResource(R.string.controls_next_description),
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerControls() {
    PlayerControls(
        isVisible = true,
        isPlaying = true,
        hasEnded = false,
        shouldShowForwardIcon = false,
        shouldShowBackwardIcon = false,
        onPlayerClick = {},
        onSeekForward = {},
        onSeekBackward = {},
        onPreviousClick = {},
        onPauseToggle = {},
        onNextClick = {},
        customization = ControlsCustomization(),
    )
}
