package com.github.syunpeii.mockstation.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.nav_home
import mockstation.composeapp.generated.resources.nav_settings
import org.jetbrains.compose.resources.StringResource

enum class TopLevelDestination(
    val route: TopLevelRoute,
    val labelRes: StringResource,
    val icon: ImageVector,
    val selectedIcon: ImageVector?,
) {
    HOME(
        route = HomeRoute,
        labelRes = Res.string.nav_home,
        icon = Icons.Filled.Home,
        selectedIcon = Icons.Outlined.Home,
    ),
    SETTINGS(
        route = SettingsRoute,
        labelRes = Res.string.nav_settings,
        icon = Icons.Filled.Settings,
        selectedIcon = Icons.Outlined.Settings,
    ),
}

sealed interface TopLevelRoute
