package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.api.ServerSettingsResponse
import com.github.syunpeii.mockstation.core.model.api.ServerStatusResponse
import com.github.syunpeii.mockstation.core.model.api.ServerSummaryResponse
import com.github.syunpeii.mockstation.core.model.api.UpdateServerSettingsRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody

class ServerApiImpl(
    private val client: HttpClient,
    private val baseUrl: String,
) : ServerApi {

    override suspend fun getServerStatus(): ServerStatusResponse {
        return client.get("$baseUrl/api/server/status").body()
    }

    override suspend fun getServerSettings(): ServerSettingsResponse {
        return client.get("$baseUrl/api/server/settings").body()
    }

    override suspend fun updateServerSettings(
        request: UpdateServerSettingsRequest
    ): ServerSettingsResponse {
        return client.patch("$baseUrl/api/server/settings") {
            setBody(request)
        }.body()
    }

    override suspend fun getServerSummary(): ServerSummaryResponse {
        return client.get("$baseUrl/api/server/summary").body()
    }
}
