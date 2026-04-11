package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.model.DelaySettings
import com.github.syunpeii.mockstation.core.model.Device

interface DeviceRepository {
    suspend fun getAllDevices(): Result<List<Device>>
    suspend fun getDeviceById(id: String): Result<Device?>
    suspend fun saveDevice(device: Device): Result<Unit>
    suspend fun updateDevice(device: Device): Result<Unit>
    suspend fun updateLastAccessed(deviceId: String): Result<Unit>
    suspend fun deleteDevice(id: String): Result<Unit>
    suspend fun getDelayRule(deviceId: String): Result<DelaySettings?>
    suspend fun saveDelayRule(deviceId: String, delaySettings: DelaySettings): Result<Unit>
}
