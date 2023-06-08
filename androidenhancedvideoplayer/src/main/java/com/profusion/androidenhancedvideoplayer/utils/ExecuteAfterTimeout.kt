package com.profusion.androidenhancedvideoplayer.utils

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "EXECUTE_AFTER_TIMEOUT"

fun executeAfterTimeout(
    scope: CoroutineScope,
    job: Job?,
    timeInMillis: Long,
    onJobComplete: () -> Unit
): Job {
    job?.cancel()
    return scope.launch() {
        try {
            delay(timeInMillis)
            onJobComplete()
        } catch (e: CancellationException) {
            Log.i(TAG, e.stackTraceToString())
        }
    }
}
