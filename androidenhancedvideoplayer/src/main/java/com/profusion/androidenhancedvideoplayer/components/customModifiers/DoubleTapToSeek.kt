package com.profusion.androidenhancedvideoplayer.components.customModifiers

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val NO_TOUCH_MIDDLE_AREA = 50

fun Modifier.doubleTapToSeek(
    onSeekForward: (tapCounter: Int) -> Unit,
    onSeekBackward: (tapCounter: Int) -> Unit
): Modifier = composed {
    var isSeekListenerEnabled by remember { mutableStateOf(false) }
    var lastTimeStampOfUniqueTap = System.currentTimeMillis()
    val scope = CoroutineScope(Dispatchers.Main)
    this.pointerInput(Unit) {
        var tapCount = 0
        val halfWidth = size.width
        // maybe we need to use rememberUpdatedState
        var job = createLazyJob(
            scope = scope,
            lastTimeStampOfUniqueTap = lastTimeStampOfUniqueTap,
            onJobComplete = { isSeekListenerEnabled = false }
        )

        detectTapGestures(
            onDoubleTap = { offset ->
                if (!isSeekListenerEnabled) {
                    isSeekListenerEnabled = true
                    tapCount++
                    job.start()
                    executeDependingOnClickSide(
                        offset = offset,
                        halfWidth = halfWidth,
                        tapCount = tapCount,
                        onSeekBackward = onSeekBackward,
                        onSeekForward = onSeekForward
                    )
                }
            },
            onTap = { offset ->
                if (isSeekListenerEnabled) {
                    job.cancel()
                    tapCount++
                    lastTimeStampOfUniqueTap = System.currentTimeMillis()
                    job = createLazyJob(
                        scope = scope,
                        lastTimeStampOfUniqueTap = lastTimeStampOfUniqueTap,
                        onJobComplete = { isSeekListenerEnabled = false }
                    )
                    job.start()
                    executeDependingOnClickSide(
                        offset = offset,
                        halfWidth = halfWidth,
                        tapCount = tapCount,
                        onSeekForward = onSeekForward,
                        onSeekBackward = onSeekBackward
                    )
                }
            }
        )
    }
}

fun createLazyJob(
    scope: CoroutineScope,
    lastTimeStampOfUniqueTap: Long,
    onJobComplete: () -> Unit
): Job {
    return scope.launch(start = CoroutineStart.LAZY) {
        delay(400)
        val currentTimeStamp = System.currentTimeMillis()
        if (currentTimeStamp - lastTimeStampOfUniqueTap > 200) {
            onJobComplete()
        }
    }
}

fun executeDependingOnClickSide(
    offset: Offset,
    halfWidth: Int,
    tapCount: Int,
    onSeekForward: (tapCount: Int) -> Unit,
    onSeekBackward: (tapCounter: Int) -> Unit
) {
    if (isTapRight(offset, halfWidth)) {
        onSeekForward(tapCount)
    } else if (isTapLeft(offset, halfWidth)) {
        onSeekBackward(tapCount)
    }
}

fun isTapRight(offset: Offset, halfWidth: Int): Boolean =
    offset.x > halfWidth + NO_TOUCH_MIDDLE_AREA / 2

fun isTapLeft(offset: Offset, halfWidth: Int): Boolean =
    offset.x < halfWidth + NO_TOUCH_MIDDLE_AREA / 2
