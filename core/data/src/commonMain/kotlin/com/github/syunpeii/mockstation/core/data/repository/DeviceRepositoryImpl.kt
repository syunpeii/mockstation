package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.data.mapper.toDomainModel
import com.github.syunpeii.mockstation.core.data.mapper.toUpdateRequest
import com.github.syunpeii.mockstation.core.model.DelaySettings
import com.github.syunpeii.mockstation.core.model.Device
import com.github.syunpeii.mockstation.core.network.api.DeviceApi
import kotlin.time.ExperimentalTime

class DeviceRepositoryImpl(
    private val deviceApi: DeviceApi,
) : DeviceRepository {

    override suspend fun getAllDevices(): Result<List<Device>> = runCatching {
        deviceApi.getDevices().map { it.toDomainModel() }
    }

    override suspend fun getDeviceById(id: String): Result<Device?> = runCatching {
        deviceApi.getDevice(id).toDomainModel()
    }

    override suspend fun saveDevice(device: Device): Result<Unit> = runCatching {
        // No API endpoint for create, skip
    }

    override suspend fun updateDevice(device: Device): Result<Unit> = runCatching {
        deviceApi.updateDevice(device.id, device.toUpdateRequest())
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun updateLastAccessed(deviceId: String): Result<Unit> = runCatching {
        // API call to update device will handle timestamp
    }

    override suspend fun deleteDevice(id: String): Result<Unit> = runCatching {
        deviceApi.deleteDevice(id)
    }

    override suspend fun getDelayRule(deviceId: String): Result<DelaySettings?> = runCatching {
        null
    }

    override suspend fun saveDelayRule(deviceId: String, delaySettings: DelaySettings): Result<Unit> = runCatching {
        // No API endpoint for delay rule, skip
    }
}
