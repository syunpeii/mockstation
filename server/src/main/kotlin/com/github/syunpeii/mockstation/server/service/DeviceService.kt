package com.github.syunpeii.mockstation.server.service

import com.github.syunpeii.mockstation.core.model.Device
import io.ktor.server.application.ApplicationCall

interface DeviceService {
    suspend fun identifyDevice(call: ApplicationCall): String
    suspend fun getCurrentTestCaseId(deviceId: String): String?
    suspend fun getDelay(deviceId: String, path: String): Long
    suspend fun getAllDevices(): List<Device>
    suspend fun updateDeviceTestCase(
        deviceId: String,
        testCaseId: String,
    )
}
