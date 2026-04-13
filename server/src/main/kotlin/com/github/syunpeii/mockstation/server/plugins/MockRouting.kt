package com.github.syunpeii.mockstation.server.plugins

import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.MockResponseResolver
import com.github.syunpeii.mockstation.server.service.RequestHistoryService
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
    requestHistoryService: RequestHistoryService,
) {
    get {
        call.handleMockRequest(
            mockResponseResolver = mockResponseResolver,
            deviceService = deviceService,
            requestHistoryService = requestHistoryService,
        )
    }
    post {
        call.handleMockRequest(
            mockResponseResolver = mockResponseResolver,
            deviceService = deviceService,
            requestHistoryService = requestHistoryService,
        )
    }
    put {
        call.handleMockRequest(
            mockResponseResolver = mockResponseResolver,
            deviceService = deviceService,
            requestHistoryService = requestHistoryService,
        )
    }
    delete {
        call.handleMockRequest(
            mockResponseResolver = mockResponseResolver,
            deviceService = deviceService,
            requestHistoryService = requestHistoryService,
        )
    }
    patch {
        call.handleMockRequest(
            mockResponseResolver = mockResponseResolver,
            deviceService = deviceService,
            requestHistoryService = requestHistoryService,
        )
    }
    head {
        call.handleMockRequest(
            mockResponseResolver = mockResponseResolver,
            deviceService = deviceService,
            requestHistoryService = requestHistoryService,
        )
    }
    options {
        call.handleMockRequest(
            mockResponseResolver = mockResponseResolver,
            deviceService = deviceService,
            requestHistoryService = requestHistoryService,
        )
    }
}

private suspend fun ApplicationCall.handleMockRequest(
    mockResponseResolver: MockResponseResolver,
    deviceService: DeviceService,
    requestHistoryService: RequestHistoryService,
) {
    val path = request.path()
    val method = request.httpMethod
    val queryParameters = request.queryParameters
    val startTimeMs = System.currentTimeMillis()

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

        val durationMs = System.currentTimeMillis() - startTimeMs
        requestHistoryService.recordRequest(
            method = method.toAppHttpMethod(),
            path = path,
            statusCode = response.status.value,
            durationMs = durationMs,
            deviceId = deviceId,
            requestHeaders = null,
            responseBody = response.body.take(256),
            requestBody = null,
        )
    } else {
        respondText(
            ERROR_NO_MATCHING_RESPONSE,
            status = HttpStatusCode.NotFound,
        )

        val durationMs = System.currentTimeMillis() - startTimeMs
        requestHistoryService.recordRequest(
            method = method.toAppHttpMethod(),
            path = path,
            statusCode = HttpStatusCode.NotFound.value,
            durationMs = durationMs,
            deviceId = deviceId,
            requestHeaders = null,
            responseBody = ERROR_NO_MATCHING_RESPONSE,
            requestBody = null,
        )
    }
}

private fun io.ktor.http.HttpMethod.toAppHttpMethod(): HttpMethod = when (this) {
    io.ktor.http.HttpMethod.Get -> HttpMethod.GET
    io.ktor.http.HttpMethod.Post -> HttpMethod.POST
    io.ktor.http.HttpMethod.Put -> HttpMethod.PUT
    io.ktor.http.HttpMethod.Delete -> HttpMethod.DELETE
    io.ktor.http.HttpMethod.Patch -> HttpMethod.PATCH
    io.ktor.http.HttpMethod.Head -> HttpMethod.HEAD
    io.ktor.http.HttpMethod.Options -> HttpMethod.OPTIONS
    else -> HttpMethod.GET
}
