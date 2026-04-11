package com.github.syunpeii.mockstation.server.plugins

import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.MockResponseResolver
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.header
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.head
import io.ktor.server.routing.options
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import kotlinx.coroutines.delay

private const val DEFAULT_TEST_CASE_ID = "default"
private const val ERROR_NO_MATCHING_RESPONSE = "No matching response file found"

fun Route.configureMockRouting(
    mockResponseResolver: MockResponseResolver,
    deviceService: DeviceService,
) {
    get {
        call.handleMockRequest(mockResponseResolver, deviceService)
    }
    post {
        call.handleMockRequest(mockResponseResolver, deviceService)
    }
    put {
        call.handleMockRequest(mockResponseResolver, deviceService)
    }
    delete {
        call.handleMockRequest(mockResponseResolver, deviceService)
    }
    patch {
        call.handleMockRequest(mockResponseResolver, deviceService)
    }
    head {
        call.handleMockRequest(mockResponseResolver, deviceService)
    }
    options {
        call.handleMockRequest(mockResponseResolver, deviceService)
    }
}

private suspend fun ApplicationCall.handleMockRequest(
    mockResponseResolver: MockResponseResolver,
    deviceService: DeviceService,
) {
    val path = request.path()
    val method = request.httpMethod
    val queryParameters = request.queryParameters

    val deviceId = deviceService.identifyDevice(this)
    val testCaseId = deviceService.getCurrentTestCaseId(deviceId) ?: DEFAULT_TEST_CASE_ID

    val delayMs = deviceService.getDelay(deviceId, path)
    if (delayMs > 0) {
        delay(delayMs)
    }

    val response = mockResponseResolver.resolve(testCaseId, path, method, queryParameters)

    if (response != null) {
        response.headers.forEach { (key, value) ->
            this.response.header(key, value)
        }

        respondText(response.body, status = response.status)
    } else {
        respondText(
            ERROR_NO_MATCHING_RESPONSE,
            status = HttpStatusCode.NotFound,
        )
    }
}
