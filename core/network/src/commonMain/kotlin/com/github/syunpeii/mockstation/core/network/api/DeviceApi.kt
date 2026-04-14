package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.api.DeviceResponse
import com.github.syunpeii.mockstation.core.model.api.RegisterDeviceRequest
import com.github.syunpeii.mockstation.core.model.api.UpdateDeviceRequest

interface DeviceApi {
    suspend fun getDevices(): List<DeviceResponse>
    suspend fun getDevice(id: String): DeviceResponse
    suspend fun registerDevice(
        id: String,
        request: RegisterDeviceRequest,
    ): DeviceResponse

    suspend fun updateDevice(id: String, request: UpdateDeviceRequest): DeviceResponse
    suspend fun deleteDevice(id: String)
}
