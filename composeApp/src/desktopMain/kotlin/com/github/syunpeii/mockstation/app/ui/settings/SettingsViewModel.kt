package com.github.syunpeii.mockstation.app.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.datastore.AppSettings
import com.github.syunpeii.mockstation.core.datastore.ConnectionSettings
import com.github.syunpeii.mockstation.core.datastore.ServerConnection
import com.github.syunpeii.mockstation.core.designsystem.resources.ComposeStringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.settings_connection_new_name
import java.util.UUID

class SettingsViewModel(
    private val appSettings: AppSettings,
    private val connectionSettings: ConnectionSettings,
    private val serverSettingsRepository: ServerSettingsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadInitialSettings()
        }
    }

    private suspend fun loadInitialSettings() {
        try {
            val themeMode = appSettings.themeMode.first()
            val navMode = appSettings.navigationDisplayMode.first()
            val connections = connectionSettings.connections.first()
            val selectedId = connectionSettings.selectedConnectionId.first()

            val themeModeEnum = when (themeMode) {
                "light" -> ThemeMode.LIGHT
                "dark" -> ThemeMode.DARK
                else -> ThemeMode.SYSTEM
            }

            val navModeEnum = when (navMode) {
                "left" -> NavigationDisplayMode.LEFT
                "bottom" -> NavigationDisplayMode.BOTTOM
                else -> NavigationDisplayMode.AUTO
            }

            val convertedConnections = connections.map { connection ->
                Connection(
                    url = connection.url,
                    description = connection.description,
                    nameResource = ComposeStringResource(
                        resourceId = Res.string.settings_connection_new_name,
                        connection.name,
                    ),
                )
            }

            val selectedIndex = if (selectedId != null) {
                convertedConnections.indexOfFirst { it.url.contains(selectedId) }
            } else {
                -1
            }

            var serverTestCaseDir: String? = null
            var serverResFileFormat: String? = null
            val settingsResult = serverSettingsRepository.getSettings()
            if (settingsResult.isSuccess) {
                val settings = settingsResult.getOrNull()
                serverTestCaseDir = settings?.testCaseDirectory
                serverResFileFormat = when (settings?.resFileFormat?.value) {
                    1 -> "METHOD_SUFFIX"
                    2 -> "SIMPLE"
                    else -> null
                }
            }

            _uiState.value = SettingsUiState.Stable(
                themeMode = themeModeEnum,
                navigationDisplayMode = navModeEnum,
                testCaseDirectory = null,
                connections = convertedConnections,
                selectedConnectionIndex = if (selectedIndex >= 0) selectedIndex else -1,
                appVersion = "1.0.0",
                isTestingConnection = false,
                connectionTestSuccess = null,
                connectionTestError = null,
                serverTestCaseDirectory = serverTestCaseDir,
                serverResFileFormat = serverResFileFormat,
                isLoadingServerSettings = false,
            )

            viewModelScope.launch {
                appSettings.themeMode.collectLatest { mode ->
                    updateThemeMode(mode)
                }
            }
            viewModelScope.launch {
                appSettings.navigationDisplayMode.collectLatest { mode ->
                    updateNavigationMode(mode)
                }
            }
            viewModelScope.launch {
                connectionSettings.connections.collectLatest { connections ->
                    updateConnections(connections)
                }
            }
            viewModelScope.launch {
                connectionSettings.selectedConnectionId.collectLatest { selectedId ->
                    updateSelectedConnection(selectedId)
                }
            }
        } catch (e: Exception) {
            _uiState.value = SettingsUiState.Error(e.message ?: "Failed to load settings")
        }
    }

    private fun updateThemeMode(mode: String) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            val themeMode = when (mode) {
                "light" -> ThemeMode.LIGHT
                "dark" -> ThemeMode.DARK
                else -> ThemeMode.SYSTEM
            }
            _uiState.value = currentState.copy(themeMode = themeMode)
        }
    }

    private fun updateNavigationMode(mode: String) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            val navMode = when (mode) {
                "left" -> NavigationDisplayMode.LEFT
                "bottom" -> NavigationDisplayMode.BOTTOM
                else -> NavigationDisplayMode.AUTO
            }
            _uiState.value = currentState.copy(navigationDisplayMode = navMode)
        }
    }

    private fun updateConnections(connections: List<ServerConnection>) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            val convertedConnections = connections.map { connection ->
                Connection(
                    url = connection.url,
                    description = connection.description,
                    nameResource = ComposeStringResource(
                        resourceId = Res.string.settings_connection_new_name,
                        connection.name,
                    ),
                )
            }
            _uiState.value = currentState.copy(connections = convertedConnections)
        }
    }

    private fun updateSelectedConnection(selectedId: String?) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            val index = currentState.connections.indexOfFirst { it.url.contains(selectedId ?: "") }
            val newIndex = if (index >= 0) index else -1
            _uiState.value = currentState.copy(selectedConnectionIndex = newIndex)
        }
    }

    fun onThemeModeChange(mode: ThemeMode) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            _uiState.value = currentState.copy(themeMode = mode)
            viewModelScope.launch {
                val modeString = when (mode) {
                    ThemeMode.LIGHT -> "light"
                    ThemeMode.DARK -> "dark"
                    ThemeMode.SYSTEM -> "system"
                }
                appSettings.setThemeMode(modeString)
            }
        }
    }

    fun onNavigationDisplayModeChange(mode: NavigationDisplayMode) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            _uiState.value = currentState.copy(navigationDisplayMode = mode)
            viewModelScope.launch {
                val modeString = when (mode) {
                    NavigationDisplayMode.LEFT -> "left"
                    NavigationDisplayMode.BOTTOM -> "bottom"
                    NavigationDisplayMode.AUTO -> "auto"
                }
                appSettings.setNavigationDisplayMode(modeString)
            }
        }
    }

    fun onTestCaseDirectoryClick() {
        println("Choose directory clicked")
    }

    fun onSelectConnection(index: Int) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable && index >= 0 && index < currentState.connections.size) {
            _uiState.value = currentState.copy(selectedConnectionIndex = index)
            viewModelScope.launch {
                connectionSettings.setSelectedConnection(UUID.randomUUID().toString())
            }
        }
    }

    fun onAddConnection() {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable && currentState.canAddConnection) {
            val newConnection = Connection(
                url = "http://localhost:8080",
                description = "",
                nameResource = ComposeStringResource(
                    resourceId = Res.string.settings_connection_new_name,
                    currentState.connections.size + 1,
                ),
            )
            _uiState.value = currentState.copy(
                connections = currentState.connections + newConnection,
            )
            viewModelScope.launch {
                connectionSettings.addConnection(
                    ServerConnection(
                        id = UUID.randomUUID().toString(),
                        name = "Connection ${currentState.connections.size + 1}",
                        url = "http://localhost:8080",
                        description = "",
                    ),
                )
            }
        }
    }

    fun onEditConnection(index: Int) {
        println("Edit connection at index: $index")
    }

    fun onDeleteConnection(index: Int) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable && index >= 0 && index < currentState.connections.size) {
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
            viewModelScope.launch {
                if (index < currentState.connections.size) {
                    connectionSettings.deleteConnection(UUID.randomUUID().toString())
                }
            }
        }
    }

    fun onTestConnection(connectionUrl: String) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            _uiState.value = currentState.copy(
                isTestingConnection = true,
                connectionTestSuccess = null,
                connectionTestError = null,
            )
            viewModelScope.launch {
                try {
                    serverSettingsRepository.getSettings()
                    _uiState.value = (_uiState.value as? SettingsUiState.Stable)?.copy(
                        isTestingConnection = false,
                        connectionTestSuccess = true,
                    ) ?: return@launch
                } catch (e: Exception) {
                    _uiState.value = (_uiState.value as? SettingsUiState.Stable)?.copy(
                        isTestingConnection = false,
                        connectionTestSuccess = false,
                        connectionTestError = e.message ?: "Connection failed",
                    ) ?: return@launch
                }
            }
        }
    }

    fun onClearConnectionTestResult() {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            _uiState.value = currentState.copy(
                connectionTestSuccess = null,
                connectionTestError = null,
            )
        }
    }

    fun onRetry() {
        _uiState.value = SettingsUiState.Loading
        viewModelScope.launch {
            loadInitialSettings()
        }
    }

    fun onChangeResFileFormat(format: String) {
        val currentState = _uiState.value
        if (currentState is SettingsUiState.Stable) {
            _uiState.value = currentState.copy(
                serverResFileFormat = format,
                isLoadingServerSettings = true,
            )
            viewModelScope.launch {
                try {
                    val settings = serverSettingsRepository.getSettings().getOrNull()
                    if (settings != null) {
                        val resFileFormatValue = when (format) {
                            "METHOD_SUFFIX" -> 1
                            "SIMPLE" -> 2
                            else -> 1
                        }
                        val updatedSettings = settings.copy(
                            resFileFormat = com.github.syunpeii.mockstation.core.model.ResFileFormat.entries
                                .firstOrNull { it.value == resFileFormatValue }
                                ?: com.github.syunpeii.mockstation.core.model.ResFileFormat.METHOD_SUFFIX,
                        )
                        val result = serverSettingsRepository.updateSettings(updatedSettings)
                        if (result.isSuccess) {
                            _uiState.value = (_uiState.value as? SettingsUiState.Stable)?.copy(
                                isLoadingServerSettings = false,
                            ) ?: return@launch
                        } else {
                            _uiState.value = (_uiState.value as? SettingsUiState.Stable)?.copy(
                                isLoadingServerSettings = false,
                                serverResFileFormat = currentState.serverResFileFormat,
                            ) ?: return@launch
                        }
                    }
                } catch (e: Exception) {
                    _uiState.value = (_uiState.value as? SettingsUiState.Stable)?.copy(
                        isLoadingServerSettings = false,
                        serverResFileFormat = currentState.serverResFileFormat,
                    ) ?: return@launch
                }
            }
        }
    }
}
