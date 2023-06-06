package com.profusion.androidenhancedvideoplayer.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun TimeoutEffect(
    timeoutInMs: Long,
    enabled: Boolean = true,
    onTimeout: () -> Unit
) {
    if (!enabled) return

    LaunchedEffect(Unit) {
        while (true) {
            onTimeout()
            delay(timeoutInMs)
        }
    }
}
