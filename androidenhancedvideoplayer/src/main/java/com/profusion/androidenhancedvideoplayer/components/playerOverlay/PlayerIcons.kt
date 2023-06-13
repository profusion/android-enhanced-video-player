package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.profusion.androidenhancedvideoplayer.R

@Composable
fun PreviousIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_skip_previous),
        contentDescription = stringResource(id = R.string.controls_previous_description),
        tint = Color.White,
        modifier = Modifier.testTag("PreviousIcon")
    )
}

@Composable
fun PlayIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_play),
        contentDescription = stringResource(id = R.string.controls_play_description),
        tint = Color.White,
        modifier = Modifier.testTag("PlayIcon")
    )
}

@Composable
fun PauseIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_pause),
        contentDescription = stringResource(id = R.string.controls_pause_description),
        tint = Color.White,
        modifier = Modifier.testTag("PauseIcon")
    )
}

@Composable
fun NextIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_skip_next),
        contentDescription = stringResource(R.string.controls_next_description),
        tint = Color.White,
        modifier = Modifier.testTag("NextIcon")
    )
}

@Composable
fun ReplayIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_replay),
        contentDescription = stringResource(id = R.string.controls_replay_description),
        tint = Color.White,
        modifier = Modifier.testTag("ReplayIcon")
    )
}

@Composable
fun FullScreenIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_fullscreen),
        contentDescription = stringResource(R.string.controls_fullscreen_description),
        tint = Color.White,
        modifier = Modifier.testTag("FullScreenIcon")
    )
}

@Composable
fun ExitFullScreenIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_exit_fullscreen),
        contentDescription = stringResource(R.string.controls_exit_fullscreen_description),
        tint = Color.White,
        modifier = Modifier.testTag("ExitFullScreenIcon")
    )
}

@Composable
fun SettingsIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_settings),
        tint = Color.White,
        contentDescription = stringResource(R.string.controls_settings_description)
    )
}

@Composable
fun SpeedIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_speed),
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
        contentDescription = stringResource(R.string.settings_speed_description)
    )
}

@Composable
fun CheckIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_check),
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
        contentDescription = stringResource(R.string.settings_check_description),
        modifier = modifier
    )
}

@Composable
fun ForwardIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_forward),
        tint = Color.White,
        contentDescription = stringResource(R.string.controls_forward_description),
        modifier = modifier.testTag("ForwardIcon")
    )
}

@Composable
fun RewindIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_rewind),
        tint = Color.White,
        contentDescription = stringResource(R.string.controls_rewind_description),
        modifier = modifier.testTag("RewindIcon")
    )
}

@Composable
fun RepeatIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_repeat),
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
        contentDescription = stringResource(R.string.settings_repeat_description),
        modifier = modifier.testTag("RepeatIcon")
    )
}
