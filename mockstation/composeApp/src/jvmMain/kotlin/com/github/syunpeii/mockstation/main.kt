package com.github.syunpeii.mockstation

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "mockstation",
    ) {
        App()
    }
}