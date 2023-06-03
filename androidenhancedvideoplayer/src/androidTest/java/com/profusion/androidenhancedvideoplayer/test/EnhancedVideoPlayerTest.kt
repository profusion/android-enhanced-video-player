package com.profusion.androidenhancedvideoplayer.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.profusion.androidenhancedvideoplayer.components.EnhancedVideoPlayer
import org.junit.Rule
import org.junit.Test

class EnhancedVideoPlayerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun enhancedVideoPlayer_WhenClickingOnVideoShouldShowControls() {
        composeTestRule.setContent {
            EnhancedVideoPlayer(
                resourceId = R.raw.login_screen_background
            )
        }

        composeTestRule.onNodeWithTag("PlayerControlsParent").assertDoesNotExist()

        composeTestRule.onNodeWithTag("VideoPlayerParent")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("PlayerControlsParent", useUnmergedTree = true)
            .assertIsDisplayed()
    }
}
