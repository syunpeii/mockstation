package com.github.syunpeii.mockstation.app.ui.home

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Stable(
        val connection: ConnectionStatus,
        val activeDevices: List<ActiveDevice>,
        val currentTestCaseId: String?,
        val serverSummary: ServerSummary,
    ) : HomeUiState

    data class Error(
        val message: String,
    ) : HomeUiState
}

data class ConnectionStatus(
    val name: String,
    val url: String,
)

data class ActiveDevice(
    val deviceId: String,
    val deviceName: String?,
    val lastRequestTimeMillis: Long,
) {
    fun getFormattedLastRequestTime(): String {
        val instant = Instant.ofEpochMilli(lastRequestTimeMillis)
        val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val millisPart = (lastRequestTimeMillis % 1000).toString().padStart(3, '0')

        return "${formatter.format(localDateTime)}.$millisPart"
    }
}

data class ServerSummary(
    val totalDeviceCount: Int,
    val recentRequestCount: Int,
)
