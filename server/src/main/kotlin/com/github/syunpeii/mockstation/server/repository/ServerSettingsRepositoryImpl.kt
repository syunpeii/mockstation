package com.github.syunpeii.mockstation.server.repository

import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.model.ResFileFormat
import com.github.syunpeii.mockstation.core.model.ServerSettings
import com.github.syunpeii.mockstation.core.model.api.ServerSummaryResponse
import io.ktor.server.config.ApplicationConfig

class ServerSettingsRepositoryImpl(
    applicationConfig: ApplicationConfig,
) : ServerSettingsRepository {

    private var currentSettings: ServerSettings = ServerSettings(
        resFileFormat = ResFileFormat.METHOD_SUFFIX,
        testCaseDirectory = DEFAULT_TEST_CASE_DIR,
        defaultDelayMs = DEFAULT_DELAY_MS,
        port = DEFAULT_PORT,
    )

    init {
        val testCaseDirectory = applicationConfig.tryGetString("mockstation.testCaseDirectory") ?: DEFAULT_TEST_CASE_DIR
        val resFileFormatValue = applicationConfig.tryGetString("mockstation.settings.resFileFormat")?.toIntOrNull() ?: DEFAULT_RES_FILE_FORMAT
        val resFileFormat = ResFileFormat.entries.firstOrNull { it.value == resFileFormatValue } ?: ResFileFormat.METHOD_SUFFIX
        val defaultDelayMs = applicationConfig.tryGetString("mockstation.settings.defaultDelayMs")?.toLongOrNull() ?: DEFAULT_DELAY_MS
        val port = applicationConfig.tryGetString("ktor.deployment.port")?.toIntOrNull() ?: DEFAULT_PORT

        currentSettings = ServerSettings(
            resFileFormat = resFileFormat,
            testCaseDirectory = testCaseDirectory,
            defaultDelayMs = defaultDelayMs,
            port = port,
        )
    }

    override suspend fun getSettings(): Result<ServerSettings> {
        return Result.success(currentSettings)
    }

    override suspend fun updateSettings(settings: ServerSettings): Result<Unit> {
        currentSettings = settings
        return Result.success(Unit)
    }

    override suspend fun getServerSummary(): Result<ServerSummaryResponse> {
        throw UnsupportedOperationException("getServerSummary() should not be called on server side")
    }

    private fun ApplicationConfig.tryGetString(path: String): String? = runCatching {
        property(path).getString()
    }.getOrNull()

    companion object {
        private const val DEFAULT_TEST_CASE_DIR = "testCase"
        private const val DEFAULT_DELAY_MS = 0L
        private const val DEFAULT_PORT = 8080
        private const val DEFAULT_RES_FILE_FORMAT = 1
    }
}
