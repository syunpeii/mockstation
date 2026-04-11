package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.model.DelaySettings
import com.github.syunpeii.mockstation.core.model.Device
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class DeviceRepositoryImpl : DeviceRepository {

    private val devices = mutableMapOf<String, Device>()
    private val delayRules = mutableMapOf<String, DelaySettings>()

    override suspend fun getAllDevices(): Result<List<Device>> = runCatching {
        devices.values.toList()
    }

    override suspend fun getDeviceById(id: String): Result<Device?> = runCatching {
        devices[id]
    }

    override suspend fun saveDevice(device: Device): Result<Unit> = runCatching {
        devices[device.id] = device
    }

    override suspend fun updateDevice(device: Device): Result<Unit> = runCatching {
        devices[device.id] = device
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun updateLastAccessed(deviceId: String): Result<Unit> = runCatching {
        devices[deviceId]?.let { device ->
            devices[deviceId] = device.copy(
                updatedAt = Instant.fromEpochMilliseconds(System.currentTimeMillis()),
            )
        }
    }

    override suspend fun deleteDevice(id: String): Result<Unit> = runCatching {
        devices.remove(id)
        delayRules.remove(id)
    }

    override suspend fun getDelayRule(deviceId: String): Result<DelaySettings?> = runCatching {
        delayRules[deviceId]
    }

    override suspend fun saveDelayRule(deviceId: String, delaySettings: DelaySettings): Result<Unit> = runCatching {
        delayRules[deviceId] = delaySettings
    }
}
