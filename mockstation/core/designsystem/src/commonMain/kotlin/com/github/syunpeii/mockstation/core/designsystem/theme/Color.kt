package com.github.syunpeii.mockstation.core.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val primaryLight = Color(0xFF6200EE)
private val primaryDark = Color(0xFFBB86FC)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val onPrimaryDark = Color(0xFF3700B3)
private val primaryContainerLight = Color(0xFFBB86FC)
private val primaryContainerDark = Color(0xFF6200EE)
private val onPrimaryContainerLight = Color(0xFF3700B3)
private val onPrimaryContainerDark = Color(0xFFEADDFF)

private val secondaryLight = Color(0xFF03DAC6)
private val secondaryDark = Color(0xFF03DAC6)
private val onSecondaryLight = Color(0xFF000000)
private val onSecondaryDark = Color(0xFF003735)
private val secondaryContainerLight = Color(0xFFB2F2E8)
private val secondaryContainerDark = Color(0xFF005050)
private val onSecondaryContainerLight = Color(0xFF018786)
private val onSecondaryContainerDark = Color(0xFF70F4E8)

private val tertiaryLight = Color(0xFF018786)
private val tertiaryDark = Color(0xFF4DB6AC)
private val onTertiaryLight = Color(0xFFFFFFFF)
private val onTertiaryDark = Color(0xFF003735)
private val tertiaryContainerLight = Color(0xFFA7F3D0)
private val tertiaryContainerDark = Color(0xFF005D57)
private val onTertiaryContainerLight = Color(0xFF014D40)
private val onTertiaryContainerDark = Color(0xFF8CEBE3)

private val errorLight = Color(0xFFB00020)
private val errorDark = Color(0xFFCF6679)
private val onErrorLight = Color(0xFFFFFFFF)
private val onErrorDark = Color(0xFF690005)
private val errorContainerLight = Color(0xFFFDE7E9)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerLight = Color(0xFF8C001A)
private val onErrorContainerDark = Color(0xFFFFDAD6)

private val backgroundLight = Color(0xFFFFFBFF)
private val backgroundDark = Color(0xFF1C1B1E)
private val onBackgroundLight = Color(0xFF1C1B1E)
private val onBackgroundDark = Color(0xFFE6E1E6)

private val surfaceLight = Color(0xFFFFFBFF)
private val surfaceDark = Color(0xFF1C1B1E)
private val onSurfaceLight = Color(0xFF1C1B1E)
private val onSurfaceDark = Color(0xFFE6E1E6)
private val surfaceVariantLight = Color(0xFFE7E0EB)
private val surfaceVariantDark = Color(0xFF49454E)
private val onSurfaceVariantLight = Color(0xFF49454E)
private val onSurfaceVariantDark = Color(0xFFCAC4CF)

private val outlineLight = Color(0xFF7A757F)
private val outlineDark = Color(0xFF948F99)
private val outlineVariantLight = Color(0xFFCAC4CF)
private val outlineVariantDark = Color(0xFF49454E)

private val scrimLight = Color(0xFF000000)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF313033)
private val inverseSurfaceDark = Color(0xFFE6E1E6)
private val inverseOnSurfaceLight = Color(0xFFF4EFF4)
private val inverseOnSurfaceDark = Color(0xFF1C1B1E)
private val inversePrimaryLight = Color(0xFFCFBCFF)
private val inversePrimaryDark = Color(0xFF6750A4)

data class MockStationColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val outlineVariant: Color,
    val scrim: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val inversePrimary: Color,
    val isDark: Boolean,
)

val LightColorScheme = MockStationColors(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    isDark = false,
)

val DarkColorScheme = MockStationColors(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    isDark = true,
)

val LocalMockStationColors = staticCompositionLocalOf {
    LightColorScheme
}
