package com.chacha.darajacmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "daraja-compose-app",
    ) {
        App()
    }
}