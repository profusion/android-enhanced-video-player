package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.profusion.androidenhancedvideoplayer.R

@Composable
fun PreviousIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_skip_previous),
        contentDescription = stringResource(id = R.string.controls_previous_description),
        modifier = Modifier.testTag("PreviousIcon")
    )
}

@Composable
fun PlayIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_play),
        contentDescription = stringResource(id = R.string.controls_play_description),
        modifier = Modifier.testTag("PlayIcon")
    )
}

@Composable
fun PauseIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_pause),
        contentDescription = stringResource(id = R.string.controls_pause_description),
        modifier = Modifier.testTag("PauseIcon")
    )
}

@Composable
fun NextIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_skip_next),
        contentDescription = stringResource(R.string.controls_next_description),
        modifier = Modifier.testTag("NextIcon")
    )
}

@Composable
fun ReplayIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_replay),
        contentDescription = stringResource(id = R.string.controls_replay_description),
        modifier = Modifier.testTag("ReplayIcon")
    )
}

@Composable
fun FullScreenIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_fullscreen),
        contentDescription = stringResource(R.string.controls_fullscreen_description),
        modifier = Modifier.testTag("FullScreenIcon")
    )
}

@Composable
fun ExitFullScreenIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_exit_fullscreen),
        contentDescription = stringResource(R.string.controls_exit_fullscreen_description),
        modifier = Modifier.testTag("ExitFullScreenIcon")
    )
}
