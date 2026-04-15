package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.model.ServerSettings
import com.github.syunpeii.mockstation.core.model.api.ServerSummaryResponse

interface ServerSettingsRepository {
    suspend fun getSettings(): Result<ServerSettings>
    suspend fun updateSettings(
        settings: ServerSettings,
    ): Result<Unit>

    suspend fun getServerSummary(): Result<ServerSummaryResponse>
}
