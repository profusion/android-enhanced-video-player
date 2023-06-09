package com.profusion.androidenhancedvideoplayer.test

import DefaultPlayerControls
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class SettingsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun settings_WhenClickingOnSpeedButtonShouldShowSpeedSelector() {
        composeTestRule.setContent {
            DefaultPlayerControls()
        }

        composeTestRule.onNodeWithTag("Playback SpeedSettingsSelector").assertDoesNotExist()

        composeTestRule.onNodeWithTag("SettingsButton").performClick()
        composeTestRule.onNodeWithTag("Playback SpeedSettingsButton").performClick()

        composeTestRule.onNodeWithTag("Playback SpeedSettingsSelector").assertIsDisplayed()
    }
}
