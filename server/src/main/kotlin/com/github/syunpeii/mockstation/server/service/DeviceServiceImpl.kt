package com.github.syunpeii.mockstation.server.service

import com.github.syunpeii.mockstation.core.data.repository.DeviceRepository
import com.github.syunpeii.mockstation.core.model.DelaySettings
import com.github.syunpeii.mockstation.core.model.DelayType
import com.github.syunpeii.mockstation.core.model.Device
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.header
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class DeviceServiceImpl(
    private val deviceRepository: DeviceRepository,
) : DeviceService {

    override suspend fun identifyDevice(call: ApplicationCall): String {
        val deviceId = call.request.headers[HEADER_DEVICE_ID]

        if (deviceId != null) {
            deviceRepository.updateLastAccessed(deviceId)
            return deviceId
        }

        val newDeviceId = generateDeviceId()
        val now = Instant.fromEpochMilliseconds(System.currentTimeMillis())
        val newDevice = Device(
            id = newDeviceId,
            name = "Device $newDeviceId",
            testCaseId = DEFAULT_TEST_CASE_ID,
            isEnabled = true,
            delaySettings = DelaySettings(
                type = DelayType.OFF,
                delayMs = null,
                isEnabled = false,
                targetFiles = emptyList(),
            ),
            createdAt = now,
            updatedAt = now,
        )
        deviceRepository.saveDevice(newDevice)

        call.response.header(HEADER_DEVICE_ID, newDeviceId)
        return newDeviceId
    }

    override suspend fun getCurrentTestCaseId(deviceId: String): String? {
        return deviceRepository.getDeviceById(deviceId)
            .getOrNull()
            ?.testCaseId
    }

    override suspend fun getDelay(deviceId: String, path: String): Long {
        val delayRule = deviceRepository.getDelayRule(deviceId).getOrNull() ?: return 0L

        if (!delayRule.isEnabled) return 0L

        if (delayRule.targetFiles.isNotEmpty()) {
            val matchesTarget = delayRule.targetFiles.any { pattern ->
                path.contains(pattern, ignoreCase = true)
            }
            if (!matchesTarget) return 0L
        }

        return when (delayRule.type) {
            DelayType.OFF -> 0L
            DelayType.PRESET -> PRESET_DELAY_MS
            DelayType.CUSTOM -> delayRule.delayMs?.toLong() ?: 0L
        }
    }

    override suspend fun getAllDevices(): List<Device> {
        return deviceRepository.getAllDevices().getOrDefault(emptyList())
    }

    override suspend fun updateDeviceTestCase(
        deviceId: String,
        testCaseId: String,
    ) {
        val device = deviceRepository.getDeviceById(deviceId).getOrNull() ?: return
        deviceRepository.updateDevice(device.copy(testCaseId = testCaseId))
    }

    private fun generateDeviceId(): String = UUID.randomUUID().toString()

    companion object {
        private const val PRESET_DELAY_MS = 5000L
        private const val HEADER_DEVICE_ID = "X-Device-Id"
        private const val DEFAULT_TEST_CASE_ID = "default"
    }
}
