package com.github.syunpeii.mockstation.server.plugins

import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.server.routes.configureManagementApi
import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.MockResponseResolver
import com.github.syunpeii.mockstation.server.service.RequestHistoryService
import com.github.syunpeii.mockstation.server.service.TestCaseFileService
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val mockResponseResolver: MockResponseResolver by inject()
    val deviceService: DeviceService by inject()
    val testCaseFileService: TestCaseFileService by inject()
    val settingsRepository: ServerSettingsRepository by inject()
    val requestHistoryService: RequestHistoryService by inject()

    routing {
        get("/") {
            call.respondText("Mockstation Server is running!", ContentType.Text.Plain)
        }

        route("/api") {
            configureManagementApi(
                testCaseFileService = testCaseFileService,
                deviceService = deviceService,
                settingsRepository = settingsRepository,
                requestHistoryService = requestHistoryService,
            )
        }

        route("{path...}") {
            configureMockRouting(
                mockResponseResolver = mockResponseResolver,
                deviceService = deviceService,
                requestHistoryService = requestHistoryService,
            )
        }
    }
}
