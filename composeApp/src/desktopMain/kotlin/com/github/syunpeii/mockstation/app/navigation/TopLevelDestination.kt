package com.github.syunpeii.mockstation.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.nav_device_management
import mockstation.composeapp.generated.resources.nav_home
import mockstation.composeapp.generated.resources.nav_settings
import mockstation.composeapp.generated.resources.nav_testcase_search
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
    DEVICE_MANAGEMENT(
        route = DeviceManagementRoute,
        labelRes = Res.string.nav_device_management,
        icon = Icons.Filled.Devices,
        selectedIcon = Icons.Outlined.Devices,
    ),
    TEST_CASE_SEARCH(
        route = TestCaseSearchRoute,
        labelRes = Res.string.nav_testcase_search,
        icon = Icons.Filled.Search,
        selectedIcon = Icons.Outlined.Search,
    ),
}

sealed interface TopLevelRoute

fun NavHostController.navigateToTopLevelDestination(
    destination: TopLevelDestination,
) {
    navigate(destination.route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
    }
}
