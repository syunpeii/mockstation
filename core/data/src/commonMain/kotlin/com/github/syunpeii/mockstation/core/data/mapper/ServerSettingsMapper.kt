package com.github.syunpeii.mockstation.core.data.mapper

import com.github.syunpeii.mockstation.core.model.ResFileFormat
import com.github.syunpeii.mockstation.core.model.ServerSettings
import com.github.syunpeii.mockstation.core.model.api.ServerSettingsResponse
import com.github.syunpeii.mockstation.core.model.api.UpdateServerSettingsRequest

fun ServerSettingsResponse.toDomainModel(): ServerSettings {
    val format = ResFileFormat.entries
        .firstOrNull { it.value == resFileFormat }
        ?: ResFileFormat.METHOD_SUFFIX

    return ServerSettings(
        resFileFormat = format,
        testCaseDirectory = testCaseDirectory,
        defaultDelayMs = defaultDelayMs,
        port = port,
    )
}

fun ServerSettings.toUpdateRequest(): UpdateServerSettingsRequest = UpdateServerSettingsRequest(
    resFileFormat = resFileFormat.value,
    testCaseDirectory = testCaseDirectory,
    defaultDelayMs = defaultDelayMs,
    port = port,
)
