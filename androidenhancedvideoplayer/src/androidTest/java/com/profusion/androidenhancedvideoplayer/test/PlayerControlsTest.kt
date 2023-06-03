package com.profusion.androidenhancedvideoplayer.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.PlayerControls
import org.junit.Rule
import org.junit.Test

class PlayerControlsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Composable
    fun defaultPlayerControls(
        isVisible: Boolean = true,
        isPlaying: Boolean = false,
        isFullScreen: Boolean = false,
        hasEnded: Boolean = false,
        onPreviousClick: () -> Unit = {},
        onPauseToggle: () -> Unit = {},
        onNextClick: () -> Unit = {},
        onFullScreenToggle: () -> Unit = {},
        customization: ControlsCustomization = ControlsCustomization()
    ) {
        PlayerControls(
            isVisible = isVisible,
            isPlaying = isPlaying,
            isFullScreen = isFullScreen,
            hasEnded = hasEnded,
            onPreviousClick = onPreviousClick,
            onPauseToggle = onPauseToggle,
            onNextClick = onNextClick,
            onFullScreenToggle = onFullScreenToggle,
            customization = customization
        )
    }

    @Test
    fun playerControls_WhenIsVisibleIsFalseShouldNotShowControls() {
        composeTestRule.setContent {
            defaultPlayerControls(
                isVisible = false
            )
        }

        composeTestRule.onNodeWithTag("PlayerControlsParent").assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenIsVisibleIsTrueShouldShowControls() {
        composeTestRule.setContent {
            defaultPlayerControls(
                isVisible = true
            )
        }

        composeTestRule.onNodeWithTag("PlayerControlsParent").assertIsDisplayed()
    }

    @Test
    fun playerControls_WhenIsPlayingIsFalseShouldShowPlayIcon() {
        composeTestRule.setContent {
            defaultPlayerControls(
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
            defaultPlayerControls(
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
            defaultPlayerControls(
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
            defaultPlayerControls(
                hasEnded = false
            )
        }

        composeTestRule.onNodeWithTag("ReplayIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenHasEndedIsTrueShouldShowReplayIcon() {
        composeTestRule.setContent {
            defaultPlayerControls(
                hasEnded = true
            )
        }

        composeTestRule.onNodeWithTag("ReplayIcon", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun playerControls_WhenIsFullScreenIsFalseShouldShowFullScreenIcon() {
        composeTestRule.setContent {
            defaultPlayerControls(
                isFullScreen = false
            )
        }

        composeTestRule.onNodeWithTag("FullScreenIcon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("ExitFullScreenIcon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun playerControls_WhenIsFullScreenIsTrueShouldShowExitFullScreenIcon() {
        composeTestRule.setContent {
            defaultPlayerControls(
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
            defaultPlayerControls(
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
}
