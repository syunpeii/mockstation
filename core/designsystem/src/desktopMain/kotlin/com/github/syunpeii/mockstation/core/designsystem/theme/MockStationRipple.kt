package com.github.syunpeii.mockstation.core.designsystem.theme

import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object MockStationRipple {
    @Composable
    fun forPrimaryBackground(
        colors: MockStationColors = MockStationTheme.colors,
    ): RippleConfiguration = RippleConfiguration(
        color = colors.onPrimary,
    )

    @Composable
    fun forSurfaceBackground(
        colors: MockStationColors = MockStationTheme.colors,
    ): RippleConfiguration = RippleConfiguration(
        color = colors.primary,
    )

    @Composable
    fun forSecondaryBackground(
        colors: MockStationColors = MockStationTheme.colors,
    ): RippleConfiguration = RippleConfiguration(
        color = colors.onSecondary,
    )

    fun custom(
        color: Color,
    ): RippleConfiguration = RippleConfiguration(
        color = color,
    )

    fun disabled(): RippleConfiguration? = null
}
