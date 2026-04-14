package com.github.syunpeii.mockstation.core.data.mapper

import com.github.syunpeii.mockstation.core.model.DelaySettings
import com.github.syunpeii.mockstation.core.model.Device
import com.github.syunpeii.mockstation.core.model.api.DeviceResponse
import com.github.syunpeii.mockstation.core.model.api.UpdateDeviceRequest
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun DeviceResponse.toDomainModel(): Device {
    val instant = Instant.parse(createdAt)
    val lastAccessedInstant = Instant.parse(lastAccessedAt)

    return Device(
        id = id,
        name = name,
        testCaseId = testCaseId ?: "default",
        isEnabled = isEnabled,
        delaySettings = DelaySettings.NONE,
        createdAt = instant,
        updatedAt = lastAccessedInstant,
    )
}

fun Device.toUpdateRequest(): UpdateDeviceRequest = UpdateDeviceRequest(
    name = name,
    testCaseId = testCaseId,
    isEnabled = isEnabled,
)
