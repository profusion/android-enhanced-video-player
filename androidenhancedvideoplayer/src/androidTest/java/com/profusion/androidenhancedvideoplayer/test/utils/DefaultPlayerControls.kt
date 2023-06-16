import androidx.compose.runtime.Composable
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.PlayerControls

@Composable
fun DefaultPlayerControls(
    title: String? = null,
    isVisible: Boolean = true,
    isPlaying: Boolean = false,
    isFullScreen: Boolean = false,
    hasEnded: Boolean = false,
    videoTimer: Long = 0,
    totalDuration: Long = 0,
    onPreviousClick: () -> Unit = {},
    onPauseToggle: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onFullScreenToggle: () -> Unit = {},
    onSettingsToggle: () -> Unit = {},
    onSeekBarValueChange: (Long) -> Unit = {},
    customization: ControlsCustomization = ControlsCustomization()
) {
    PlayerControls(
        title = title,
        isVisible = isVisible,
        isPlaying = isPlaying,
        isFullScreen = isFullScreen,
        hasEnded = hasEnded,
        currentTime = videoTimer,
        totalDuration = totalDuration,
        onPreviousClick = onPreviousClick,
        onPauseToggle = onPauseToggle,
        onNextClick = onNextClick,
        onFullScreenToggle = onFullScreenToggle,
        onSettingsToggle = onSettingsToggle,
        onSeekBarValueChange = onSeekBarValueChange,
        customization = customization
    )
}
