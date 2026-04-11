@file:OptIn(kotlin.time.ExperimentalTime::class)

package com.github.syunpeii.mockstation.server.routes

import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.model.Device
import com.github.syunpeii.mockstation.core.model.ResFileFormat
import com.github.syunpeii.mockstation.core.model.api.ActivateTestCaseRequest
import com.github.syunpeii.mockstation.core.model.api.DeviceResponse
import com.github.syunpeii.mockstation.core.model.api.ServerSettingsResponse
import com.github.syunpeii.mockstation.core.model.api.ServerStatusResponse
import com.github.syunpeii.mockstation.core.model.api.UpdateDeviceRequest
import com.github.syunpeii.mockstation.core.model.api.UpdateServerSettingsRequest
import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.TestCaseFileService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route

private const val DEFAULT_TEST_CASE_ID = "default"
private const val ERROR_MISSING_ID = "Missing id"
private const val SERVER_VERSION = "0.1.0"
private const val STATUS_RUNNING = "running"

fun Route.configureManagementApi(
    testCaseFileService: TestCaseFileService,
    deviceService: DeviceService,
    settingsRepository: ServerSettingsRepository,
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

            settingsRepository.updateSettings(updatedSettings)
            call.respond(HttpStatusCode.OK)
        }
    }

    route("/testcases") {
        get("") {
            val testCases = testCaseFileService.findAllTestCases()
            call.respond(testCases)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                ERROR_MISSING_ID,
                status = HttpStatusCode.BadRequest,
            )
            val testCase = testCaseFileService.findTestCaseById(id)
            if (testCase != null) {
                call.respond(testCase)
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Test case not found"))
            }
        }

        post("/activate") {
            val request = call.receive<ActivateTestCaseRequest>()
            val deviceId = request.deviceId ?: DEFAULT_TEST_CASE_ID
            deviceService.updateDeviceTestCase(deviceId, request.testCaseId)
            call.respond(HttpStatusCode.OK)
        }
    }

    route("/devices") {
        get("") {
            val devices = deviceService.getAllDevices()
            val responses = devices.map { it.toResponse() }
            call.respond(responses)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                ERROR_MISSING_ID,
                status = HttpStatusCode.BadRequest,
            )
            val device = deviceService.getAllDevices().find { it.id == id }
            if (device != null) {
                call.respond(device.toResponse())
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Device not found"))
            }
        }

        patch("/{id}") {
            val id = call.parameters["id"] ?: return@patch call.respondText(
                ERROR_MISSING_ID,
                status = HttpStatusCode.BadRequest,
            )
            val request = call.receive<UpdateDeviceRequest>()

            call.respond(HttpStatusCode.OK)
        }
    }
}

private fun Device.toResponse() = DeviceResponse(
    id = id,
    name = name,
    testCaseId = testCaseId,
    isEnabled = isEnabled,
    lastAccessedAt = updatedAt.toString(),
    createdAt = createdAt.toString(),
)
