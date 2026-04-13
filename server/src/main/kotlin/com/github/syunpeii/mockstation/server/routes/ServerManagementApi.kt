package com.github.syunpeii.mockstation.server.routes

import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.model.ResFileFormat
import com.github.syunpeii.mockstation.core.model.api.ServerSettingsResponse
import com.github.syunpeii.mockstation.core.model.api.ServerStatusResponse
import com.github.syunpeii.mockstation.core.model.api.ServerSummaryResponse
import com.github.syunpeii.mockstation.core.model.api.UpdateServerSettingsRequest
import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.RequestHistoryService
import com.github.syunpeii.mockstation.server.service.TestCaseFileService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.route

private const val SERVER_VERSION = "0.1.0"
private const val STATUS_RUNNING = "running"

fun Route.configureServerManagementApi(
    testCaseFileService: TestCaseFileService,
    deviceService: DeviceService,
    settingsRepository: ServerSettingsRepository,
    requestHistoryService: RequestHistoryService,
) {
    route("/server") {
        get("/status") {
            call.respond(
                ServerStatusResponse(
                    status = STATUS_RUNNING,
                    version = SERVER_VERSION,
                    uptime = 0L,
                ),
            )
        }

        get("/settings") {
            val settings = settingsRepository.getSettings().getOrThrow()
            call.respond(
                ServerSettingsResponse(
                    resFileFormat = settings.resFileFormat.value,
                    testCaseDirectory = settings.testCaseDirectory,
                    defaultDelayMs = settings.defaultDelayMs,
                    port = settings.port,
                ),
            )
        }

        patch("/settings") {
            val request = call.receive<UpdateServerSettingsRequest>()
            val currentSettings = settingsRepository.getSettings().getOrThrow()

            val updatedSettings = currentSettings.copy(
                resFileFormat = request.resFileFormat?.let { value ->
                    ResFileFormat.entries.firstOrNull { it.value == value } ?: currentSettings.resFileFormat
                } ?: currentSettings.resFileFormat,
                testCaseDirectory = request.testCaseDirectory ?: currentSettings.testCaseDirectory,
                defaultDelayMs = request.defaultDelayMs ?: currentSettings.defaultDelayMs,
                port = request.port ?: currentSettings.port,
            )

            settingsRepository.updateSettings(updatedSettings).getOrThrow()
            call.respond(HttpStatusCode.OK)
        }

        get("/summary") {
            val devices = deviceService.getAllDevices()
            val testCases = testCaseFileService.findAllTestCases()
            call.respond(
                ServerSummaryResponse(
                    status = STATUS_RUNNING,
                    deviceCount = devices.size,
                    testCaseCount = testCases.size,
                    historyCount = requestHistoryService.getHistoryCount().getOrDefault(0),
                    uptime = 0L,
                ),
            )
        }
    }
}
