import androidx.compose.runtime.Composable
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.ControlsCustomization
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.PlayerControls
import com.profusion.androidenhancedvideoplayer.components.playerOverlay.SettingsControlsCustomization

@Composable
fun DefaultPlayerControls(
    title: String? = null,
    isVisible: Boolean = true,
    isPlaying: Boolean = false,
    isFullScreen: Boolean = false,
    hasEnded: Boolean = false,
    speed: Float = 1f,
    videoTimer: Long = 0,
    totalDuration: Long = 0,
    onPreviousClick: () -> Unit = {},
    onPauseToggle: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onFullScreenToggle: () -> Unit = {},
    onSpeedSelected: (Float) -> Unit = {},
    onSeekBarValueChange: (Long) -> Unit = {},
    customization: ControlsCustomization = ControlsCustomization(),
    settingsControlsCustomization: SettingsControlsCustomization = SettingsControlsCustomization()
) {
    PlayerControls(
        title = title,
        isVisible = isVisible,
        isPlaying = isPlaying,
        isFullScreen = isFullScreen,
        hasEnded = hasEnded,
        speed = speed,
        currentTime = videoTimer,
        totalDuration = totalDuration,
        onPreviousClick = onPreviousClick,
        onPauseToggle = onPauseToggle,
        onNextClick = onNextClick,
        onFullScreenToggle = onFullScreenToggle,
        onSpeedSelected = onSpeedSelected,
        onSeekBarValueChange = onSeekBarValueChange,
        customization = customization,
        settingsControlsCustomization = settingsControlsCustomization
    )
}
