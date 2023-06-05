package com.profusion.androidenhancedvideoplayer.styling

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Dimensions {
    /** The default unit of spacing in dp */
    private val spaceUnit: Dp = 16.dp

    /** none spacing value (0dp) */
    val none: Dp = 0.dp

    /** xxxs spacing value (1dp) */
    val xxxsmall: Dp = (0.0625 * spaceUnit.value).dp

    /** xxs spacing value (2dp) */
    val xxsmall: Dp = (0.125 * spaceUnit.value).dp

    /** xs spacing value (4dp) */
    val xsmall: Dp = (0.25 * spaceUnit.value).dp

    /** sm spacing value (8dp) */
    val small: Dp = (0.5 * spaceUnit.value).dp

    /** md spacing value (12dp) */
    val medium: Dp = (0.75 * spaceUnit.value).dp

    /** lg spacing value (16dp) */
    val large: Dp = spaceUnit

    /** xlg spacing value (24dp) */
    val xlarge: Dp = (1.5 * spaceUnit.value).dp

    /** xxlg spacing value (40dp) */
    val xxlarge: Dp = (2.5 * spaceUnit.value).dp

    /** xxxlg pacing value (64dp) */
    val xxxlarge: Dp = (4 * spaceUnit.value).dp
}
