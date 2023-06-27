package com.profusion.androidenhancedvideoplayer.components.playerOverlay

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.profusion.androidenhancedvideoplayer.styling.Colors
import com.profusion.androidenhancedvideoplayer.utils.JobsHolder
import com.profusion.androidenhancedvideoplayer.utils.executeAfterTimeout
import com.profusion.androidenhancedvideoplayer.utils.noRippleClickable

private const val ICON_ANIMATION_DURATION_MS = 650
private const val ICON_INITIAL_SCALE = 0.8f
private const val ICON_TARGET_SCALE = 1.1f
private const val JOB_TIMEOUT = 650L
private const val TRANSITION_LABEL = "scaleSeekIcon"

@Composable
fun SeekHandler(
    seekIncrement: (Long) -> Unit,
    disableSeekForward: Boolean,
    toggleControlsVisibility: () -> Unit,
    setControlsVisibility: (value: Boolean) -> Unit,
    transformSeekIncrementRatio: (tapCount: Int) -> Long,
    controlsCustomization: ControlsCustomization
) {
    val jobs = JobsHolder
    val scope = rememberCoroutineScope()
    var forwardTapCount by remember { mutableStateOf(0) }
    var rewindTapCount by remember { mutableStateOf(0) }
    val isRewinding by remember { derivedStateOf { rewindTapCount > 0 } }
    val isForwarding by remember { derivedStateOf { forwardTapCount > 0 } }

    val transition = rememberInfiniteTransition(TRANSITION_LABEL)

    val scale by transition.animateFloat(
        label = TRANSITION_LABEL,
        initialValue = ICON_INITIAL_SCALE,
        targetValue = ICON_TARGET_SCALE,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ICON_ANIMATION_DURATION_MS,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(forwardTapCount) {
        if (forwardTapCount > 0) {
            val incrementTime = transformSeekIncrementRatio(forwardTapCount) -
                transformSeekIncrementRatio(
                    forwardTapCount - 1
                )
            seekIncrement(incrementTime)
        }
    }

    LaunchedEffect(rewindTapCount) {
        if (rewindTapCount > 0) {
            val incrementTime = transformSeekIncrementRatio(rewindTapCount) -
                transformSeekIncrementRatio(
                    rewindTapCount - 1
                )
            seekIncrement(-incrementTime)
        }
    }

    fun checkIfCanToggleIsControlsVisible() {
        if (!isRewinding && !isForwarding) {
            toggleControlsVisibility()
        }
    }

    fun onForwardSingleTap() {
        jobs.seekJob = executeAfterTimeout(scope, jobs.seekJob, JOB_TIMEOUT) {
            forwardTapCount = 0
        }
        forwardTapCount++
    }

    fun onForwardDoubleTap() {
        if (isRewinding) return
        setControlsVisibility(false)
        onForwardSingleTap()
    }

    fun onRewindSingleTap() {
        jobs.seekJob = executeAfterTimeout(scope, jobs.seekJob, JOB_TIMEOUT) {
            rewindTapCount = 0
        }
        rewindTapCount++
    }

    fun onRewindDoubleTap() {
        if (isForwarding) return
        setControlsVisibility(false)
        onRewindSingleTap()
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isRewinding || isForwarding) {
                    Colors.controlsShadow
                } else {
                    Color.Transparent
                }
            )
            .noRippleClickable(
                onClick = toggleControlsVisibility
            )
    ) {
        SeekClickableArea(
            modifier = Modifier.weight(1f),
            isSeeking = isRewinding,
            scaleAnimation = { scale },
            onSeekDoubleTap = ::onRewindDoubleTap,
            onSeekSingleTap = ::onRewindSingleTap,
            checkIfCanToggleIsControlsVisible = ::checkIfCanToggleIsControlsVisible,
            getSeekTime = { (transformSeekIncrementRatio(rewindTapCount) / 1000).toInt() },
            seekIcon = { controlsCustomization.rewindIconContent(it) }
        )
        Spacer(modifier = Modifier.weight(0.2f))
        SeekClickableArea(
            modifier = Modifier.weight(1f),
            isSeeking = isForwarding,
            scaleAnimation = { scale },
            disableSeekClick = disableSeekForward,
            onSeekDoubleTap = ::onForwardDoubleTap,
            onSeekSingleTap = ::onForwardSingleTap,
            checkIfCanToggleIsControlsVisible = ::checkIfCanToggleIsControlsVisible,
            getSeekTime = { (transformSeekIncrementRatio(forwardTapCount) / 1000).toInt() },
            seekIcon = { controlsCustomization.forwardIconContent(it) }
        )
    }
}
