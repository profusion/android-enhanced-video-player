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
    onPreviousClick: () -> Unit = {},
    onPauseToggle: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onFullScreenToggle: () -> Unit = {},
    onSpeedSelected: (Float) -> Unit = {},
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
        onPreviousClick = onPreviousClick,
        onPauseToggle = onPauseToggle,
        onNextClick = onNextClick,
        onFullScreenToggle = onFullScreenToggle,
        onSpeedSelected = onSpeedSelected,
        customization = customization,
        settingsControlsCustomization = settingsControlsCustomization
    )
}
