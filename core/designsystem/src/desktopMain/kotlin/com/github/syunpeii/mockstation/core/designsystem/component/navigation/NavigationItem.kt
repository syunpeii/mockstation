package com.github.syunpeii.mockstation.core.designsystem.component.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector?,
)
