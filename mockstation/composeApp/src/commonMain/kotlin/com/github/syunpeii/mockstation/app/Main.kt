package com.github.syunpeii.mockstation.app

import androidx.compose.runtime.Composable
import com.github.syunpeii.mockstation.app.ui.screen.HomeScreen
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun App() {
    MockStationTheme {
        HomeScreen()
    }
}
