package com.github.syunpeii.mockstation.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.syunpeii.mockstation.core.data.repository.DeviceRepository
import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.datastore.AppSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

class HomeViewModel(
    private val serverSettingsRepository: ServerSettingsRepository,
    private val deviceRepository: DeviceRepository,
    private val appSettings: AppSettings,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    @OptIn(ExperimentalTime::class)
    private fun loadData() = viewModelScope.launch {
        val settingsResult = serverSettingsRepository.getSettings()
        val devicesResult = deviceRepository.getAllDevices()
        val connectionUrl = appSettings.baseUrl.first()

        if (settingsResult.isSuccess && devicesResult.isSuccess) {
            val devices = devicesResult.getOrNull() ?: emptyList()
            val activeDevices = devices.take(3).mapIndexed { index, device ->
                ActiveDevice(
                    deviceId = device.id,
                    deviceName = device.name,
                    lastRequestTimeMillis = device.updatedAt.toEpochMilliseconds(),
                )
            }

            val summaryResult = serverSettingsRepository.getServerSummary()
            val recentRequestCount = summaryResult.getOrNull()?.historyCount ?: 0

            _uiState.value = HomeUiState.Stable(
                connection = ConnectionStatus(
                    name = "Server",
                    url = connectionUrl,
                ),
                activeDevices = activeDevices,
                currentTestCaseId = devices.firstOrNull()?.testCaseId ?: "",
                serverSummary = ServerSummary(
                    totalDeviceCount = devices.size,
                    recentRequestCount = recentRequestCount,
                ),
            )
        } else {
            _uiState.value = HomeUiState.Error("Failed to load data")
        }
    }

    fun onNavigateToSettings() {
        println("Navigate to settings")
    }

    fun onRetry() {
        _uiState.value = HomeUiState.Loading
        loadData()
    }
}
