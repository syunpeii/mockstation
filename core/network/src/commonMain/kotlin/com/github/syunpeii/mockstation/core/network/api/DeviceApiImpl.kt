package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.api.DeviceResponse
import com.github.syunpeii.mockstation.core.model.api.RegisterDeviceRequest
import com.github.syunpeii.mockstation.core.model.api.UpdateDeviceRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class DeviceApiImpl(
    private val client: HttpClient,
    private val baseUrl: String,
) : DeviceApi {

    override suspend fun getDevices(): List<DeviceResponse> {
        return client.get("$baseUrl/api/devices").body()
    }

    override suspend fun getDevice(id: String): DeviceResponse {
        return client.get("$baseUrl/api/devices/$id").body()
    }

    override suspend fun registerDevice(
        id: String,
        request: RegisterDeviceRequest
    ): DeviceResponse {
        return client.post("$baseUrl/api/devices/$id/register") {
            setBody(request)
        }.body()
    }

    override suspend fun updateDevice(id: String, request: UpdateDeviceRequest): DeviceResponse {
        return client.patch("$baseUrl/api/devices/$id") {
            setBody(request)
        }.body()
    }

    override suspend fun deleteDevice(id: String) {
        client.delete("$baseUrl/api/devices/$id")
    }
}
