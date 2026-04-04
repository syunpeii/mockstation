package com.github.syunpeii.mockstation.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.syunpeii.mockstation.app.navigation.DeviceManagementRoute
import com.github.syunpeii.mockstation.app.navigation.HomeRoute
import com.github.syunpeii.mockstation.app.navigation.SettingsRoute
import com.github.syunpeii.mockstation.app.navigation.TestCaseSearchRoute
import com.github.syunpeii.mockstation.app.navigation.TopLevelDestination
import com.github.syunpeii.mockstation.app.navigation.WindowSizeClass
import com.github.syunpeii.mockstation.app.navigation.navigateToTopLevelDestination
import com.github.syunpeii.mockstation.app.ui.devicemanagement.DeviceManagementScreen
import com.github.syunpeii.mockstation.app.ui.home.HomeScreen
import com.github.syunpeii.mockstation.app.ui.settings.SettingsScreen
import com.github.syunpeii.mockstation.app.ui.testcasesearch.TestCaseSearchScreen
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.util.BoxContainer
import com.github.syunpeii.mockstation.core.designsystem.component.navigation.BottomNavigationWrapper
import com.github.syunpeii.mockstation.core.designsystem.component.navigation.CustomTopBar
import com.github.syunpeii.mockstation.core.designsystem.component.navigation.NavigationItem
import com.github.syunpeii.mockstation.core.designsystem.component.navigation.NavigationRailWrapper
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import kotlinx.serialization.Serializable
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.accessibility_add
import mockstation.composeapp.generated.resources.accessibility_more
import org.jetbrains.compose.resources.stringResource

@Serializable
data object MainRoute

private val BottomNavigationHeight = 80.dp

@Composable
fun MockStationApp() {
    MockStationTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = MainRoute,
        ) {
            composable<MainRoute> {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    val navigationItems = TopLevelDestination.entries.map { destination ->
        NavigationItem(
            label = stringResource(destination.labelRes),
            icon = destination.icon,
            selectedIcon = destination.selectedIcon,
        )
    }

    var selectedItemIndex by remember { mutableIntStateOf(0) }

    BoxContainer(
        modifier = Modifier
            .background(MockStationTheme.colors.surfaceVariant)
            .fillMaxSize(),
    ) {
        val windowSizeClass = remember(maxWidth) {
            WindowSizeClass.fromWidthByDp(maxWidth.value)
        }

        MainScreenContent(
            windowSizeClass = windowSizeClass,
            navigationItems = navigationItems,
            selectedItemIndex = selectedItemIndex,
            navHost = { MainNavHost(navController, windowSizeClass) },
            onItemClick = { index ->
                selectedItemIndex = index
                val destination = TopLevelDestination.entries[index]
                navController.navigateToTopLevelDestination(destination)
            },
        )
    }
}

@Composable
private fun MainScreenContent(
    windowSizeClass: WindowSizeClass,
    navigationItems: List<NavigationItem>,
    selectedItemIndex: Int,
    navHost: @Composable () -> Unit,
    onItemClick: (Int) -> Unit,
) {
    val isCompact = windowSizeClass == WindowSizeClass.Compact

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CustomTopBar(
            actions = {
                AppIconButton(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.accessibility_add),
                    onClick = { /* TODO: 実装 */ },
                )
                AppIconButton(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(Res.string.accessibility_more),
                    onClick = { /* TODO: 実装 */ },
                )
            },
        )

        Box(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                AnimatedVisibility(
                    visible = !isCompact,
                    enter = slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = 300,
                            delayMillis = 300,
                        ),
                        initialOffsetX = { -it },
                    ),
                    exit = slideOutHorizontally(
                        animationSpec = tween(
                            durationMillis = 300,
                        ),
                        targetOffsetX = { -it },
                    ),
                ) {
                    NavigationRailWrapper(
                        items = navigationItems,
                        selectedIndex = selectedItemIndex,
                        onItemClick = onItemClick,
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = if (isCompact) BottomNavigationHeight else 0.dp),
                ) {
                    navHost.invoke()
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                AnimatedVisibility(
                    visible = isCompact,
                    enter = slideInVertically(
                        animationSpec = tween(
                            durationMillis = 300,
                            delayMillis = 300,
                        ),
                        initialOffsetY = { it },
                    ),
                    exit = slideOutVertically(
                        animationSpec = tween(
                            durationMillis = 300,
                        ),
                        targetOffsetY = { it },
                    ),
                ) {
                    BottomNavigationWrapper(
                        items = navigationItems,
                        selectedIndex = selectedItemIndex,
                        onItemClick = onItemClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.HOME.route,
        modifier = Modifier
            .background(MockStationTheme.colors.surfaceVariant)
            .padding(MockStationTheme.spacing.extraSmall)
            .clip(MockStationTheme.shapes.small),
    ) {
        composable<HomeRoute> { _ ->
            HomeScreen()
        }
        composable<SettingsRoute> { _ ->
            SettingsScreen()
        }
        composable<DeviceManagementRoute> { _ ->
            DeviceManagementScreen()
        }
        composable<TestCaseSearchRoute> { _ ->
            TestCaseSearchScreen(windowSizeClass = windowSizeClass)
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreenContentCompact() {
    MockStationTheme {
        val navController = rememberNavController()
        val navigationItems = TopLevelDestination.entries.map { destination ->
            NavigationItem(
                label = stringResource(destination.labelRes),
                icon = destination.icon,
                selectedIcon = destination.selectedIcon,
            )
        }

        MainScreenContent(
            windowSizeClass = WindowSizeClass.Compact,
            navigationItems = navigationItems,
            selectedItemIndex = 0,
            navHost = {},
            onItemClick = {},
        )
    }
}

@Preview
@Composable
private fun PreviewMainScreenContentExpanded() {
    MockStationTheme {
        val navController = rememberNavController()
        val navigationItems = TopLevelDestination.entries.map { destination ->
            NavigationItem(
                label = stringResource(destination.labelRes),
                icon = destination.icon,
                selectedIcon = destination.selectedIcon,
            )
        }

        MainScreenContent(
            windowSizeClass = WindowSizeClass.Expanded,
            navigationItems = navigationItems,
            selectedItemIndex = 0,
            navHost = {},
            onItemClick = {},
        )
    }
}
