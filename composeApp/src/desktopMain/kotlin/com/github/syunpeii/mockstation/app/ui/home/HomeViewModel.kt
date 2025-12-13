package com.github.syunpeii.mockstation.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1_000)
            _uiState.value = HomeUiState.Stable(
                connection = ConnectionStatus(
                    name = "Local Server",
                    url = "http://localhost:8080",
                ),
                activeDevices = listOf(
                    ActiveDevice(
                        deviceId = "device-001",
                        deviceName = "Test Device 1",
                        lastRequestTimeMillis = System.currentTimeMillis(),
                    ),
                    ActiveDevice(
                        deviceId = "device-002",
                        deviceName = null,
                        lastRequestTimeMillis = System.currentTimeMillis() - 60000,
                    ),
                    ActiveDevice(
                        deviceId = "device-003",
                        deviceName = "Production Device",
                        lastRequestTimeMillis = System.currentTimeMillis() - 300000,
                    ),
                ),
                currentTestCaseId = "test-case-123",
                serverSummary = ServerSummary(
                    totalDeviceCount = 5,
                    recentRequestCount = 42,
                ),
            )
        }
    }

    fun onNavigateToSettings() {
        println("Navigate to settings")
        // TODO: Implement actual navigation
    }
}
