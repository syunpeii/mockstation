package com.github.syunpeii.mockstation.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.syunpeii.mockstation.core.data.repository.DeviceRepository
import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

class HomeViewModel(
    private val serverSettingsRepository: ServerSettingsRepository,
    private val deviceRepository: DeviceRepository,
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

        if (settingsResult.isSuccess && devicesResult.isSuccess) {
            val devices = devicesResult.getOrNull() ?: emptyList()
            val activeDevices = devices.take(3).mapIndexed { index, device ->
                ActiveDevice(
                    deviceId = device.id,
                    deviceName = device.name,
                    lastRequestTimeMillis = device.updatedAt.toEpochMilliseconds(),
                )
            }

            _uiState.value = HomeUiState.Stable(
                connection = ConnectionStatus(
                    name = "Server",
                    url = "http://localhost:8080",
                ),
                activeDevices = activeDevices,
                currentTestCaseId = devices.firstOrNull()?.testCaseId ?: "",
                serverSummary = ServerSummary(
                    totalDeviceCount = devices.size,
                    recentRequestCount = 0,
                ),
            )
        } else {
            _uiState.value = HomeUiState.Error("Failed to load data")
        }
    }

    fun onNavigateToSettings() {
        println("Navigate to settings")
    }
}
