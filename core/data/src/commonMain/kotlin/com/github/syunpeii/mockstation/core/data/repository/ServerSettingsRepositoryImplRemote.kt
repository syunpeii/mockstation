package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.data.mapper.toDomainModel
import com.github.syunpeii.mockstation.core.data.mapper.toUpdateRequest
import com.github.syunpeii.mockstation.core.model.ServerSettings
import com.github.syunpeii.mockstation.core.model.api.ServerStatusResponse
import com.github.syunpeii.mockstation.core.model.api.ServerSummaryResponse
import com.github.syunpeii.mockstation.core.network.api.ServerApi

class ServerSettingsRepositoryImplRemote(
    private val serverApi: ServerApi,
) : ServerSettingsRepository {

    override suspend fun getSettings(): Result<ServerSettings> = runCatching {
        serverApi.getServerSettings().toDomainModel()
    }

    override suspend fun updateSettings(settings: ServerSettings): Result<Unit> = runCatching {
        serverApi.updateServerSettings(settings.toUpdateRequest())
    }

    suspend fun getServerStatus(): Result<ServerStatusResponse> = runCatching {
        serverApi.getServerStatus()
    }

    suspend fun getServerSummary(): Result<ServerSummaryResponse> = runCatching {
        serverApi.getServerSummary()
    }
}
