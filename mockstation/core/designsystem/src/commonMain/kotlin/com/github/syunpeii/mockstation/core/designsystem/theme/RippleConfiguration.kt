package com.github.syunpeii.mockstation.core.designsystem.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object MockStationRipple {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun forPrimaryBackground(
        colors: MockStationColors = MockStationTheme.colors,
    ): RippleConfiguration = RippleConfiguration(
        color = colors.onPrimary,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun forSurfaceBackground(
        colors: MockStationColors = MockStationTheme.colors,
    ): RippleConfiguration = RippleConfiguration(
        color = colors.primary,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun forSecondaryBackground(
        colors: MockStationColors = MockStationTheme.colors,
    ): RippleConfiguration = RippleConfiguration(
        color = colors.onSecondary,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    fun custom(
        color: Color,
    ): RippleConfiguration = RippleConfiguration(
        color = color,
    )

    fun disabled(): RippleConfiguration? = null
}
