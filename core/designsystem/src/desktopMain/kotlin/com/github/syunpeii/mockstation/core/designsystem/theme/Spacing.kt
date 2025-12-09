package com.github.syunpeii.mockstation.core.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class MockStationSpacing(
    val none: Dp,
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val extraExtraLarge: Dp,
)

val DefaultSpacing = MockStationSpacing(
    none = 0.dp,
    extraSmall = 4.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
    extraLarge = 32.dp,
    extraExtraLarge = 48.dp,
)

val LocalMockStationSpacing = staticCompositionLocalOf {
    DefaultSpacing
}
