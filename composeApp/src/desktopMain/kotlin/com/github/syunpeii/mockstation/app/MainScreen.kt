package com.github.syunpeii.mockstation.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.syunpeii.mockstation.app.navigation.HomeRoute
import com.github.syunpeii.mockstation.app.navigation.SettingsRoute
import com.github.syunpeii.mockstation.app.navigation.TopLevelDestination
import com.github.syunpeii.mockstation.app.navigation.WindowSizeClass
import com.github.syunpeii.mockstation.app.ui.home.HomeScreen
import com.github.syunpeii.mockstation.app.ui.settings.SettingsScreen
import com.github.syunpeii.mockstation.core.designsystem.component.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.component.navigation.BottomNavigationWrapper
import com.github.syunpeii.mockstation.core.designsystem.component.navigation.CustomTopBar
import com.github.syunpeii.mockstation.core.designsystem.component.navigation.NavigationItem
import com.github.syunpeii.mockstation.core.designsystem.component.navigation.NavigationRailWrapper
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

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
    val navigationItems = remember {
        TopLevelDestination.entries.map { destination ->
            NavigationItem(
                label = destination.label,
                icon = destination.icon,
                selectedIcon = destination.selectedIcon,
            )
        }
    }

    var selectedItemIndex by remember { mutableIntStateOf(0) }

    BoxWithConstraints {
        val windowSizeClass = remember(maxWidth) {
            WindowSizeClass.fromWidthByDp(maxWidth.value)
        }

        MainScreenContent(
            windowSizeClass = windowSizeClass,
            navigationItems = navigationItems,
            selectedItemIndex = selectedItemIndex,
            navHost = { MainNavHost(navController) },
            onItemClick = { index ->
                selectedItemIndex = index
                navController.navigate(TopLevelDestination.entries[index].route)
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
                    contentDescription = "Add",
                    onClick = { /* TODO: 実装 */ },
                )
                AppIconButton(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More",
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
                    modifier = Modifier.weight(1f),
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
private fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.HOME.route,
    ) {
        composable<HomeRoute> { HomeScreen() }
        composable<SettingsRoute> { SettingsScreen() }
    }
}

@Preview
@Composable
private fun PreviewMainScreenContentCompact() {
    MockStationTheme {
        val navController = rememberNavController()
        val navigationItems = TopLevelDestination.entries.map { destination ->
            NavigationItem(
                label = destination.label,
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
                label = destination.label,
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
