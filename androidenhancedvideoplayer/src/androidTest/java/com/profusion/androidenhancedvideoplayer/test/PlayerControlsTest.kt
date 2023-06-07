package com.profusion.androidenhancedvideoplayer.test

import DefaultPlayerControls
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class PlayerControlsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun playerControls_WhenIsVisibleIsFalseShouldNotShowControls() {
        composeTestRule.setContent {
            DefaultPlayerControls(
                isVisible = false
            )
        }

        composeTestRule.onNodeWithTag("PlayerControlsParent").assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenIsVisibleIsTrueShouldShowControls() {
        composeTestRule.setContent {
            DefaultPlayerControls(
                isVisible = true
            )
        }

        composeTestRule.onNodeWithTag("PlayerControlsParent").assertIsDisplayed()
    }

    @Test
    fun playerControls_WhenIsPlayingIsFalseShouldShowPlayIcon() {
        composeTestRule.setContent {
            DefaultPlayerControls(
                isPlaying = false
            )
        }

        composeTestRule.onNodeWithTag("PlayIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("PauseIcon", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ReplayIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenIsPlayingIsTrueShouldShowPauseIcon() {
        composeTestRule.setContent {
            DefaultPlayerControls(
                isPlaying = true
            )
        }

        composeTestRule.onNodeWithTag("PauseIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("PlayIcon", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ReplayIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenPauseToggleIsCalledShouldChangeTheIcon() {
        var isPlaying = true
        composeTestRule.setContent {
            DefaultPlayerControls(
                isPlaying = isPlaying,
                onPauseToggle = { isPlaying = !isPlaying }
            )
        }

        composeTestRule.onNodeWithTag("PauseIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("PlayIcon", useUnmergedTree = true).assertDoesNotExist()

        composeTestRule.onNodeWithTag("PauseToggleButton").performClick()

        composeTestRule.onNodeWithTag("PlayIcon", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("PauseIcon", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun playerControls_WhenHasEndedIsFalseShouldNotShowReplayIcon() {
        composeTestRule.setContent {
            DefaultPlayerControls(
                hasEnded = false
            )
        }

        composeTestRule.onNodeWithTag("ReplayIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenHasEndedIsTrueShouldShowReplayIcon() {
        composeTestRule.setContent {
            DefaultPlayerControls(
                hasEnded = true
            )
        }

        composeTestRule.onNodeWithTag("ReplayIcon", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun playerControls_WhenIsFullScreenIsFalseShouldShowFullScreenIcon() {
        composeTestRule.setContent {
            DefaultPlayerControls(
                isFullScreen = false
            )
        }

        composeTestRule.onNodeWithTag("FullScreenIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("ExitFullScreenIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenIsFullScreenIsTrueShouldShowExitFullScreenIcon() {
        composeTestRule.setContent {
            DefaultPlayerControls(
                isFullScreen = true
            )
        }

        composeTestRule.onNodeWithTag("ExitFullScreenIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("FullScreenIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenFullScreenToggleIsCalledShouldChangeTheIcon() {
        var isFullScreen = true
        composeTestRule.setContent {
            DefaultPlayerControls(
                isFullScreen = isFullScreen,
                onFullScreenToggle = { isFullScreen = !isFullScreen }
            )
        }

        composeTestRule.onNodeWithTag("ExitFullScreenIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("FullScreenIcon", useUnmergedTree = true).assertDoesNotExist()

        composeTestRule.onNodeWithTag("FullScreenToggleButton").performClick()

        composeTestRule.onNodeWithTag("FullScreenIcon", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ExitFullScreenIcon", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun playerControls_WhenPressedSettingsButtonShouldShowSettings() {
        composeTestRule.setContent {
            DefaultPlayerControls()
        }

        composeTestRule.onNodeWithTag("SettingsControlsParent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("SettingsButton").performClick()
        composeTestRule.onNodeWithTag("SettingsControlsParent").assertIsDisplayed()
    }

    @Test
    fun playerControls_WhenHasTitleShouldDisplayTitle() {
        val title = "My Video Title"

        composeTestRule.setContent {
            DefaultPlayerControls(
                title = title
            )
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }
}
