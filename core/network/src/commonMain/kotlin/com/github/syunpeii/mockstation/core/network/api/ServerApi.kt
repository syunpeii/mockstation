package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.api.ServerSettingsResponse
import com.github.syunpeii.mockstation.core.model.api.ServerStatusResponse
import com.github.syunpeii.mockstation.core.model.api.ServerSummaryResponse
import com.github.syunpeii.mockstation.core.model.api.UpdateServerSettingsRequest

interface ServerApi {
    suspend fun getServerStatus(): ServerStatusResponse
    suspend fun getServerSettings(): ServerSettingsResponse
    suspend fun updateServerSettings(
        request: UpdateServerSettingsRequest
    ): ServerSettingsResponse

    suspend fun getServerSummary(): ServerSummaryResponse
}
