@file:OptIn(kotlin.time.ExperimentalTime::class)

package com.github.syunpeii.mockstation.server.routes

import com.github.syunpeii.mockstation.core.model.Device
import com.github.syunpeii.mockstation.core.model.api.DeviceResponse
import com.github.syunpeii.mockstation.core.model.api.ErrorResponse
import com.github.syunpeii.mockstation.core.model.api.RegisterDeviceRequest
import com.github.syunpeii.mockstation.core.model.api.UpdateDeviceRequest
import com.github.syunpeii.mockstation.server.service.DeviceService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.configureDeviceManagementApi(deviceService: DeviceService) {
    route("/devices") {
        get("") {
            val devices = deviceService.getAllDevices()
            val responses = devices.map { it.toResponse() }
            call.respond(responses)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(ErrorType.MISSING_ID.message),
            )
            val device = deviceService.getAllDevices().find { it.id == id }
            if (device != null) {
                call.respond(device.toResponse())
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse(ErrorType.DEVICE_NOT_FOUND.message))
            }
        }

        post("/{id}/register") {
            val id = call.parameters["id"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(ErrorType.MISSING_ID.message),
            )
            val request = call.receive<RegisterDeviceRequest>()
            val device = deviceService.registerDevice(id, request.name)
            call.respond(HttpStatusCode.Created, device.toResponse())
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(ErrorType.MISSING_ID.message),
            )
            val deleted = deviceService.deleteDevice(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse(ErrorType.DEVICE_NOT_FOUND.message))
            }
        }

        patch("/{id}") {
            val id = call.parameters["id"] ?: return@patch call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(ErrorType.MISSING_ID.message),
            )
            val request = call.receive<UpdateDeviceRequest>()
            try {
                val updatedDevice = deviceService.updateDevice(
                    deviceId = id,
                    name = request.name,
                    testCaseId = request.testCaseId,
                    isEnabled = request.isEnabled,
                )
                call.respond(HttpStatusCode.OK, updatedDevice.toResponse())
            } catch (_: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, ErrorResponse(ErrorType.DEVICE_NOT_FOUND.message))
            }
        }
    }
}

internal fun Device.toResponse() = DeviceResponse(
    id = id,
    name = name,
    testCaseId = testCaseId,
    isEnabled = isEnabled,
    lastAccessedAt = updatedAt.toString(),
    createdAt = createdAt.toString(),
)
