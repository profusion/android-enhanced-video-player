package com.profusion.androidenhancedvideoplayer.test

import com.profusion.androidenhancedvideoplayer.utils.formatElapsedTime
import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsTest {
    @Test
    fun convertMillisecondsToTimeString_GivenLessThanOneMinuteShouldReturnZeroMinutesAndTheSeconds() {
        val milliseconds = 10000L
        val expected = "00:10"
        val result = formatElapsedTime(milliseconds)
        assertEquals(expected, result)
    }

    @Test
    fun convertMillisecondsToTimeString_GivenMoreThanOneMinuteShouldReturnMinutesAndSeconds() {
        val milliseconds = 70000L
        val expected = "01:10"
        val result = formatElapsedTime(milliseconds)
        assertEquals(expected, result)
    }

    @Test
    fun convertMillisecondsToTimeString_GivenMoreThanOneHourShouldReturnHoursMinutesAndSeconds() {
        val milliseconds = 3661000L
        val expected = "01:01:01"
        val result = formatElapsedTime(milliseconds)
        assertEquals(expected, result)
    }

    @Test
    fun testGetVideoTimeString() {
        val videoTimer = 65432L
        val totalDuration = 3600000L
        val expected = "01:05 / 01:00:00"
        val result = formatElapsedTime(videoTimer, totalDuration)
        assertEquals(expected, result)
    }
}
