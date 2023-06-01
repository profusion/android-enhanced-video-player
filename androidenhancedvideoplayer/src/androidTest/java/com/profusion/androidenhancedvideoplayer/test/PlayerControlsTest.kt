package com.profusion.androidenhancedvideoplayer.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.profusion.androidenhancedvideoplayer.components.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.PlayerControls
import org.junit.Rule
import org.junit.Test

class PlayerControlsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun playerControls_WhenIsVisibleIsFalseShouldNotShowControls() {
        composeTestRule.setContent {
            PlayerControls(
                isVisible = false,
                isPlaying = false,
                onPreviousClick = {},
                onPauseToggle = {},
                onNextClick = {},
                customization = ControlsCustomization()
            )
        }

        composeTestRule.onNodeWithTag("PlayerControlsParent").assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenIsVisibleIsTrueShouldShowControls() {
        composeTestRule.setContent {
            PlayerControls(
                isVisible = true,
                isPlaying = false,
                onPreviousClick = {},
                onPauseToggle = {},
                onNextClick = {},
                customization = ControlsCustomization()
            )
        }

        composeTestRule.onNodeWithTag("PlayerControlsParent").assertIsDisplayed()
    }

    @Test
    fun playerControls_WhenIsPlayingIsFalseShouldShowPlayIcon() {
        composeTestRule.setContent {
            PlayerControls(
                isVisible = true,
                isPlaying = false,
                onPreviousClick = {},
                onPauseToggle = {},
                onNextClick = {},
                customization = ControlsCustomization()
            )
        }

        composeTestRule.onNodeWithTag("PlayIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("PauseIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenIsPlayingIsTrueShouldShowPauseIcon() {
        composeTestRule.setContent {
            PlayerControls(
                isVisible = true,
                isPlaying = true,
                onPreviousClick = {},
                onPauseToggle = {},
                onNextClick = {},
                customization = ControlsCustomization()
            )
        }

        composeTestRule.onNodeWithTag("PauseIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("PlayIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenPauseToggleIsCalledShouldChangeTheIcon() {
        var isPlaying = true
        composeTestRule.setContent {
            PlayerControls(
                isVisible = true,
                isPlaying = isPlaying,
                onPreviousClick = {},
                onPauseToggle = { isPlaying = !isPlaying },
                onNextClick = {},
                customization = ControlsCustomization()
            )
        }

        composeTestRule.onNodeWithTag("PauseIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("PlayIcon", useUnmergedTree = true).assertDoesNotExist()

        composeTestRule.onNodeWithTag("PauseToggleButton").performClick()

        composeTestRule.onNodeWithTag("PlayIcon", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("PauseIcon", useUnmergedTree = true).assertIsDisplayed()
    }
}
