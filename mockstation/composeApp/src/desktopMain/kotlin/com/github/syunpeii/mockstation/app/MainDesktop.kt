package com.github.syunpeii.mockstation.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.syunpeii.mockstation.app.di.allModules
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(allModules)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Mock Station",
    ) {
        App()
    }
}
