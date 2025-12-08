package com.github.syunpeii.mockstation.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.syunpeii.mockstation.app.ui.screen.main.MainScreen
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
