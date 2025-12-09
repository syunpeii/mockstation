package com.github.syunpeii.mockstation.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val route: TopLevelRoute,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector?,
) {
    HOME(
        route = HomeRoute,
        label = "Home",
        icon = Icons.Filled.Home,
        selectedIcon = Icons.Outlined.Home,
    ),
    SETTINGS(
        route = SettingsRoute,
        label = "Settings",
        icon = Icons.Filled.Settings,
        selectedIcon = Icons.Outlined.Settings,
    ),
}

sealed interface TopLevelRoute
