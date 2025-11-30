package com.github.syunpeii.mockstation.server

import com.github.syunpeii.mockstation.server.plugins.configureRouting
import com.github.syunpeii.mockstation.server.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    install(CallLogging)
    configureSerialization()
    configureRouting()
}
