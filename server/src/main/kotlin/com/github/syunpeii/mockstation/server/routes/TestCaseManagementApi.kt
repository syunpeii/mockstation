package com.github.syunpeii.mockstation.server.routes

import com.github.syunpeii.mockstation.core.model.api.ActivateTestCaseRequest
import com.github.syunpeii.mockstation.core.model.api.ErrorResponse
import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.TestCaseFileService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

private const val DEFAULT_TEST_CASE_ID = "default"

fun Route.configureTestCaseManagementApi(
    testCaseFileService: TestCaseFileService,
    deviceService: DeviceService,
) {
    route("/testcases") {
        get("") {
            val testCases = testCaseFileService.findAllTestCases()
            call.respond(testCases)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(ErrorType.MISSING_ID.message),
            )
            val testCase = testCaseFileService.findTestCaseById(id)
            if (testCase != null) {
                call.respond(testCase)
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse(ErrorType.TEST_CASE_NOT_FOUND.message))
            }
        }

        post("/activate") {
            val request = call.receive<ActivateTestCaseRequest>()
            val deviceId = request.deviceId ?: DEFAULT_TEST_CASE_ID
            deviceService.updateDeviceTestCase(deviceId, request.testCaseId)
            call.respond(HttpStatusCode.OK)
        }
    }
}
