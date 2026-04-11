package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.model.ResFileFormat
import com.github.syunpeii.mockstation.core.model.ServerSettings

class ServerSettingsRepositoryImpl : ServerSettingsRepository {

    private var currentSettings = ServerSettings(
        resFileFormat = ResFileFormat.METHOD_SUFFIX,
        testCaseDirectory = DEFAULT_TEST_CASE_DIR,
        defaultDelayMs = 0,
        port = DEFAULT_PORT,
    )

    override suspend fun getSettings(): Result<ServerSettings> = runCatching {
        currentSettings
    }

    override suspend fun updateSettings(settings: ServerSettings): Result<Unit> = runCatching {
        currentSettings = settings
    }

    companion object {
        private const val DEFAULT_TEST_CASE_DIR = "~/.mockstation/test-cases"
        private const val DEFAULT_PORT = 8080
    }
}
