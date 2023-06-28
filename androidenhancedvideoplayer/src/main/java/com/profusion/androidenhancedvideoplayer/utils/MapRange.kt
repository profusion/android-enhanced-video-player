package com.profusion.androidenhancedvideoplayer.utils

fun mapLongToIntRange(value: Long, inputRange: LongRange, outputRange: IntRange): Int {
    val inputStart = inputRange.first
    val inputEnd = inputRange.last
    val outputStart = outputRange.first
    val outputEnd = outputRange.last

    val inputValue = value.coerceIn(inputStart, inputEnd)
    val inputRangeSize = inputEnd - inputStart
    val outputRangeSize = outputEnd - outputStart

    val scaledValue = (inputValue - inputStart) * outputRangeSize / inputRangeSize
    return outputStart + scaledValue.toInt()
}
