@file:OptIn(kotlin.time.ExperimentalTime::class)

package com.github.syunpeii.mockstation.server.routes

import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.RequestHistoryService
import com.github.syunpeii.mockstation.server.service.TestCaseFileService
import io.ktor.server.routing.Route

fun Route.configureManagementApi(
    testCaseFileService: TestCaseFileService,
    deviceService: DeviceService,
    settingsRepository: ServerSettingsRepository,
    requestHistoryService: RequestHistoryService,
) {
    configureServerManagementApi(
        testCaseFileService = testCaseFileService,
        deviceService = deviceService,
        settingsRepository = settingsRepository,
        requestHistoryService = requestHistoryService,
    )
    configureTestCaseManagementApi(
        testCaseFileService = testCaseFileService,
        deviceService = deviceService,
    )
    configureDeviceManagementApi(
        deviceService = deviceService,
    )
    configureRequestHistoryApi(
        requestHistoryService = requestHistoryService,
    )
}
