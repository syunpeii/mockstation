package com.github.syunpeii.mockstation.app.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.UUID
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(100)
            _uiState.value = SettingsUiState.Stable(
                themeMode = ThemeMode.SYSTEM,
                navigationDisplayMode = NavigationDisplayMode.AUTO,
                testCaseDirectory = null,
                connections = emptyList(),
                selectedConnectionIndex = -1,
                appVersion = "1.0.0",
            )
        }
    }

    fun onThemeModeChange(mode: ThemeMode) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            _uiState.value = currentState.copy(themeMode = mode)
        }
    }

    fun onNavigationDisplayModeChange(mode: NavigationDisplayMode) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            _uiState.value = currentState.copy(navigationDisplayMode = mode)
        }
    }

    fun onTestCaseDirectoryClick() {
        println("Choose directory clicked")
    }

    fun onSelectConnection(index: Int) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            _uiState.value = currentState.copy(selectedConnectionIndex = index)
        }
    }

    fun onAddConnection() {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable && currentState.canAddConnection) {
            val newConnection = Connection(
                id = UUID.randomUUID().toString(),
                name = "New Connection ${currentState.connections.size + 1}",
                url = "http://localhost:8080",
                description = "",
            )
            _uiState.value = currentState.copy(
                connections = currentState.connections + newConnection,
            )
        }
    }

    fun onEditConnection(index: Int) {
        println("Edit connection at index: $index")
    }

    fun onDeleteConnection(index: Int) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            val newConnections = currentState.connections.toMutableList().apply {
                removeAt(index)
            }
            val newSelectedIndex = when {
                currentState.selectedConnectionIndex == index -> -1
                currentState.selectedConnectionIndex > index -> currentState.selectedConnectionIndex - 1
                else -> currentState.selectedConnectionIndex
            }
            _uiState.value = currentState.copy(
                connections = newConnections,
                selectedConnectionIndex = newSelectedIndex,
            )
        }
    }
}
