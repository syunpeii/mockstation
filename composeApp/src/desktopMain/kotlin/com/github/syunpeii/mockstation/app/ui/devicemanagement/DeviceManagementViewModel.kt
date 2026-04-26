package com.github.syunpeii.mockstation.app.ui.devicemanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.syunpeii.mockstation.core.data.repository.DeviceRepository
import com.github.syunpeii.mockstation.core.data.repository.TestCaseRepository
import com.github.syunpeii.mockstation.core.model.DelaySettings
import com.github.syunpeii.mockstation.core.model.DelayType
import com.github.syunpeii.mockstation.core.model.Device
import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange
import com.github.syunpeii.mockstation.data.repository.RequestHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DeviceManagementViewModel(
    private val deviceRepository: DeviceRepository,
    private val requestHistoryRepository: RequestHistoryRepository,
    private val testCaseRepository: TestCaseRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DeviceManagementUiState>(DeviceManagementUiState.Loading)
    val uiState: StateFlow<DeviceManagementUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    fun onTabChange(index: Int) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(selectedTabIndex = index)
        }
    }

    fun onFilterChange(text: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(
                serverDevices = currentState.serverDevices.copy(filterText = text),
            )
        }
    }

    fun onRegisterDevice(deviceId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            viewModelScope.launch {
                val now = Clock.System.now()
                val newDevice = Device(
                    id = deviceId,
                    name = "New Device",
                    testCaseId = "default-test",
                    isEnabled = true,
                    delaySettings = DelaySettings.NONE,
                    createdAt = now,
                    updatedAt = now,
                )
                val result = deviceRepository.saveDevice(newDevice)
                if (result.isSuccess) {
                    val currentState = _uiState.value
                    if (currentState is DeviceManagementUiState.Stable) {
                        val registeredDeviceDisplay = RegisteredDeviceDisplay(
                            id = deviceId,
                            name = "New Device",
                            testCaseId = "default-test",
                            isEnabled = true,
                            delaySettings = DelaySettingsDisplay(
                                type = DelayType.OFF,
                                delayMs = null,
                                targetFiles = emptyList(),
                                isEnabled = false,
                            ),
                        )
                        _uiState.value = currentState.copy(
                            registeredDevices = currentState.registeredDevices + registeredDeviceDisplay,
                            serverDevices = currentState.serverDevices.copy(
                                devices = currentState.serverDevices.devices.map {
                                    if (it.deviceId == deviceId) it.copy(isRegistered = true) else it
                                },
                            ),
                            selectedTabIndex = 0,
                        )
                    }
                }
            }
        }
    }

    fun onEditDeviceName(deviceId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val device = currentState.registeredDevices.find { it.id == deviceId }
            device?.let {
                _uiState.value = currentState.copy(
                    dialogState = DialogState.EditDeviceName(deviceId, it.name),
                )
            }
        }
    }

    fun onSaveDeviceName(deviceId: String, newName: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            viewModelScope.launch {
                val deviceResult = deviceRepository.getDeviceById(deviceId)
                if (deviceResult.isSuccess) {
                    val device = deviceResult.getOrNull()
                    if (device != null) {
                        val updatedDevice = device.copy(name = newName, updatedAt = Clock.System.now())
                        val updateResult = deviceRepository.updateDevice(updatedDevice)
                        if (updateResult.isSuccess) {
                            val currentState = _uiState.value
                            if (currentState is DeviceManagementUiState.Stable) {
                                _uiState.value = currentState.copy(
                                    registeredDevices = currentState.registeredDevices.map {
                                        if (it.id == deviceId) it.copy(name = newName) else it
                                    },
                                    dialogState = DialogState.None,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onDeleteDevice(deviceId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val device = currentState.registeredDevices.find { it.id == deviceId }
            device?.let {
                _uiState.value = currentState.copy(
                    dialogState = DialogState.DeleteConfirmation(deviceId, it.name),
                )
            }
        }
    }

    fun onConfirmDelete(deviceId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            viewModelScope.launch {
                val deleteResult = deviceRepository.deleteDevice(deviceId)
                if (deleteResult.isSuccess) {
                    val currentState = _uiState.value
                    if (currentState is DeviceManagementUiState.Stable) {
                        _uiState.value = currentState.copy(
                            registeredDevices = currentState.registeredDevices.filter { it.id != deviceId },
                            serverDevices = currentState.serverDevices.copy(
                                devices = currentState.serverDevices.devices.map {
                                    if (it.deviceId == deviceId) it.copy(isRegistered = false) else it
                                },
                            ),
                            dialogState = DialogState.None,
                        )
                    }
                }
            }
        }
    }

    fun onToggleEnabled(deviceId: String, enabled: Boolean) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            viewModelScope.launch {
                val deviceResult = deviceRepository.getDeviceById(deviceId)
                if (deviceResult.isSuccess) {
                    val device = deviceResult.getOrNull()
                    if (device != null) {
                        val updatedDevice = device.copy(isEnabled = enabled, updatedAt = Clock.System.now())
                        val updateResult = deviceRepository.updateDevice(updatedDevice)
                        if (updateResult.isSuccess) {
                            val currentState = _uiState.value
                            if (currentState is DeviceManagementUiState.Stable) {
                                _uiState.value = currentState.copy(
                                    registeredDevices = currentState.registeredDevices.map {
                                        if (it.id == deviceId) it.copy(isEnabled = enabled) else it
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onTestCaseClick(testCaseId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            viewModelScope.launch {
                val result = testCaseRepository.getTestCase(testCaseId)
                val testCase = result.getOrNull()
                val content = testCase?.description ?: "No description available"
                val currentState = _uiState.value
                if (currentState is DeviceManagementUiState.Stable) {
                    _uiState.value = currentState.copy(
                        dialogState = DialogState.ShowMarkdown(
                            testCaseId = testCaseId,
                            content = content,
                        ),
                    )
                }
            }
        }
    }

    fun onDelaySettingsClick(deviceId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val device = currentState.registeredDevices.find { it.id == deviceId }
            device?.let {
                viewModelScope.launch {
                    val availableFiles = if (device.testCaseId.isNotEmpty()) {
                        val result = testCaseRepository.getTestCase(device.testCaseId)
                        result.getOrNull()?.files ?: mockAvailableFiles()
                    } else {
                        mockAvailableFiles()
                    }

                    val currentState = _uiState.value
                    if (currentState is DeviceManagementUiState.Stable) {
                        _uiState.value = currentState.copy(
                            dialogState = DialogState.EditDelaySettings(
                                deviceId = deviceId,
                                currentSettings = device.delaySettings,
                                availableFiles = availableFiles,
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onSaveDelaySettings(deviceId: String, settings: DelaySettingsDisplay) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            viewModelScope.launch {
                val delaySettings = DelaySettings(
                    type = settings.type,
                    delayMs = settings.delayMs,
                    isEnabled = settings.isEnabled,
                    targetFiles = settings.targetFiles,
                )
                val saveResult = deviceRepository.saveDelayRule(deviceId, delaySettings)
                if (saveResult.isSuccess) {
                    val currentState = _uiState.value
                    if (currentState is DeviceManagementUiState.Stable) {
                        _uiState.value = currentState.copy(
                            registeredDevices = currentState.registeredDevices.map {
                                if (it.id == deviceId) it.copy(delaySettings = settings) else it
                            },
                            dialogState = DialogState.None,
                        )
                    }
                }
            }
        }
    }

    fun onDismissDialog() {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(dialogState = DialogState.None)
        }
    }

    fun onNavigateToServerTab() {
        onTabChange(1)
    }

    fun onRequestSearchChange(text: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(
                    filters = currentState.requestHistory.filters.copy(searchText = text),
                ),
            )
            applyFilters()
        }
    }

    fun onMethodToggle(method: HttpMethod) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val currentMethods = currentState.requestHistory.filters.selectedMethods
            val newMethods = if (method in currentMethods) {
                currentMethods - method
            } else {
                currentMethods + method
            }
            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(
                    filters = currentState.requestHistory.filters.copy(selectedMethods = newMethods),
                ),
            )
            applyFilters()
        }
    }

    fun onStatusCategoryToggle(category: StatusCategory) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val currentCategories = currentState.requestHistory.filters.selectedStatusCategories
            val newCategories = if (category in currentCategories) {
                currentCategories - category
            } else {
                currentCategories + category
            }
            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(
                    filters = currentState.requestHistory.filters.copy(selectedStatusCategories = newCategories),
                ),
            )
            applyFilters()
        }
    }

    fun onTimeRangeChange(timeRange: TimeRange) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(
                    filters = currentState.requestHistory.filters.copy(timeRange = timeRange),
                ),
            )
            applyFilters()
        }
    }

    fun onSortOrderToggle() {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val currentOrder = currentState.requestHistory.filters.sortOrder
            val newOrder = if (currentOrder == SortOrder.NEWEST_FIRST) {
                SortOrder.OLDEST_FIRST
            } else {
                SortOrder.NEWEST_FIRST
            }
            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(
                    filters = currentState.requestHistory.filters.copy(sortOrder = newOrder),
                ),
            )
            applyFilters()
        }
    }

    fun onShowAdvancedFiltersChange(show: Boolean) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(showAdvancedFilters = show),
            )
        }
    }

    fun onClearHistory() {
        viewModelScope.launch {
            val clearResult = requestHistoryRepository.deleteAllHistory()
            if (clearResult.isSuccess) {
                // Reload history after clearing
                val historyResult = requestHistoryRepository.getRequestHistory()
                if (historyResult.isSuccess) {
                    val currentState = _uiState.value
                    if (currentState is DeviceManagementUiState.Stable) {
                        _uiState.value = currentState.copy(
                            requestHistory = currentState.requestHistory.copy(
                                deviceColumns = emptyList(),
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onRetry() {
        _uiState.value = DeviceManagementUiState.Loading
        loadInitialData()
    }

    fun onRequestClick(requestId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val currentIds = currentState.requestHistory.selectedRequestIds
            val clickedRequest = currentState.requestHistory.deviceColumns
                .flatMap { it.requests }
                .find { it.id == requestId }

            val newSelectedIds = if (requestId in currentIds) {
                currentIds - requestId
            } else if (clickedRequest != null) {
                val sameDeviceRequestIds = currentState.requestHistory.deviceColumns
                    .filter { it.deviceId == clickedRequest.deviceId }
                    .flatMap { it.requests }
                    .map { it.id }
                    .toSet()

                (currentIds - sameDeviceRequestIds) + requestId
            } else {
                currentIds + requestId
            }

            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(selectedRequestIds = newSelectedIds),
            )
        }
    }

    private fun applyFilters() {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            viewModelScope.launch {
                val filters = currentState.requestHistory.filters
                val historyResult = requestHistoryRepository.getRequestHistory(
                    search = filters.searchText.ifEmpty { null },
                    methods = filters.selectedMethods.toList().ifEmpty { null },
                    statusCategories = filters.selectedStatusCategories.toList().ifEmpty { null },
                    timeRange = filters.timeRange,
                    sortOrder = filters.sortOrder,
                )

                if (historyResult.isSuccess) {
                    val history = historyResult.getOrNull() ?: emptyList()
                    val currentState = _uiState.value
                    if (currentState is DeviceManagementUiState.Stable) {
                        val deviceColumns = history
                            .groupBy { it.deviceId }
                            .map { (deviceId, requests) ->
                                val deviceName = currentState.registeredDevices.find { it.id == deviceId }?.name
                                DeviceRequestColumn(
                                    deviceId = deviceId,
                                    deviceName = deviceName,
                                    requests = requests,
                                )
                            }

                        _uiState.value = currentState.copy(
                            requestHistory = currentState.requestHistory.copy(
                                deviceColumns = deviceColumns,
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val devicesResult = deviceRepository.getAllDevices()
            val historyResult = requestHistoryRepository.getRequestHistory()

            if (devicesResult.isSuccess && historyResult.isSuccess) {
                val devices = devicesResult.getOrNull() ?: emptyList()
                val history = historyResult.getOrNull() ?: emptyList()

                val registeredDevices = devices.map { device ->
                    RegisteredDeviceDisplay(
                        id = device.id,
                        name = device.name,
                        testCaseId = device.testCaseId,
                        isEnabled = device.isEnabled,
                        delaySettings = DelaySettingsDisplay(
                            type = device.delaySettings.type,
                            delayMs = device.delaySettings.delayMs,
                            targetFiles = device.delaySettings.targetFiles,
                            isEnabled = device.delaySettings.isEnabled,
                        ),
                    )
                }

                // Group history by device
                val deviceColumns = history.take(100)
                    .groupBy { it.deviceId }
                    .map { (deviceId, requests) ->
                        val deviceName = registeredDevices.find { it.id == deviceId }?.name
                        DeviceRequestColumn(
                            deviceId = deviceId,
                            deviceName = deviceName,
                            requests = requests,
                        )
                    }

                // Generate ServerDevicesState from actual devices data
                val allDevices = devicesResult.getOrNull() ?: emptyList()
                val serverDevices = ServerDevicesState(
                    devices = allDevices.map { device ->
                        ServerDeviceDisplay(
                            deviceId = device.id,
                            isRegistered = registeredDevices.any { it.id == device.id },
                        )
                    },
                    filterText = "",
                    isLoading = false,
                    error = null,
                )

                _uiState.value = DeviceManagementUiState.Stable(
                    selectedTabIndex = 0,
                    registeredDevices = registeredDevices,
                    serverDevices = serverDevices,
                    requestHistory = RequestHistoryState(
                        deviceColumns = deviceColumns,
                        filters = RequestFilters(
                            searchText = "",
                            selectedMethods = emptySet(),
                            selectedStatusCategories = emptySet(),
                            timeRange = TimeRange.ALL,
                            sortOrder = SortOrder.NEWEST_FIRST,
                        ),
                        selectedRequestIds = emptySet(),
                        showAdvancedFilters = false,
                    ),
                    dialogState = DialogState.None,
                )
            } else {
                _uiState.value = DeviceManagementUiState.Error("Failed to load data")
            }
        }
    }

    private fun mockAvailableFiles() = listOf(
        "api/user.json",
        "api/device.json",
        "api/test.json",
        "api/settings.json",
        "api/config.json",
    )
}
