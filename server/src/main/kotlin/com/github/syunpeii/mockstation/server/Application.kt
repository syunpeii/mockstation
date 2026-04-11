package com.github.syunpeii.mockstation.server

import com.github.syunpeii.mockstation.server.di.allServerModules
import com.github.syunpeii.mockstation.server.plugins.configureRouting
import com.github.syunpeii.mockstation.server.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(
        Netty,
        host = "0.0.0.0",
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    val applicationConfig = environment.config
    val configModule = module {
        single<ApplicationConfig> { applicationConfig }
    }

    install(Koin) {
        slf4jLogger()
        modules(listOf(configModule) + allServerModules)
    }
    install(CallLogging)
    configureSerialization()
    configureRouting()
}
