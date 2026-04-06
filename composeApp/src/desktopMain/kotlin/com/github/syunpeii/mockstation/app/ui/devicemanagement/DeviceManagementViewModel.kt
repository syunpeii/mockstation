package com.github.syunpeii.mockstation.app.ui.devicemanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DeviceManagementViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<DeviceManagementUiState>(DeviceManagementUiState.Loading)
    val uiState: StateFlow<DeviceManagementUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            delay(1_000)
            _uiState.value = DeviceManagementUiState.Stable(
                selectedTabIndex = 0,
                registeredDevices = mockRegisteredDevices(),
                serverDevices = mockServerDevices(),
                requestHistory = mockRequestHistory(),
                dialogState = DialogState.None,
            )
        }
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
            // Add to registered devices
            val newDevice = RegisteredDeviceDisplay(
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
                registeredDevices = currentState.registeredDevices + newDevice,
                serverDevices = currentState.serverDevices.copy(
                    devices = currentState.serverDevices.devices.map {
                        if (it.deviceId == deviceId) it.copy(isRegistered = true) else it
                    },
                ),
                selectedTabIndex = 0, // Switch to registered devices tab
            )
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
            _uiState.value = currentState.copy(
                registeredDevices = currentState.registeredDevices.map {
                    if (it.id == deviceId) it.copy(name = newName) else it
                },
                dialogState = DialogState.None,
            )
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

    fun onToggleEnabled(deviceId: String, enabled: Boolean) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(
                registeredDevices = currentState.registeredDevices.map {
                    if (it.id == deviceId) it.copy(isEnabled = enabled) else it
                },
            )
        }
    }

    fun onTestCaseClick(testCaseId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(
                dialogState = DialogState.ShowMarkdown(
                    testCaseId = testCaseId,
                    content = mockMarkdownContent(testCaseId),
                ),
            )
        }
    }

    fun onDelaySettingsClick(deviceId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val device = currentState.registeredDevices.find { it.id == deviceId }
            device?.let {
                _uiState.value = currentState.copy(
                    dialogState = DialogState.EditDelaySettings(
                        deviceId = deviceId,
                        currentSettings = it.delaySettings,
                        availableFiles = mockAvailableFiles(),
                    ),
                )
            }
        }
    }

    fun onSaveDelaySettings(deviceId: String, settings: DelaySettingsDisplay) {
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

    fun onDismissDialog() {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(dialogState = DialogState.None)
        }
    }

    fun onNavigateToServerTab() {
        onTabChange(1)
    }

    // Request History Filter Handlers
    fun onRequestSearchChange(text: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(
                    filters = currentState.requestHistory.filters.copy(searchText = text),
                ),
            )
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

    fun onRequestClick(requestId: String) {
        val currentState = _uiState.value
        if (currentState is DeviceManagementUiState.Stable) {
            val currentIds = currentState.requestHistory.selectedRequestIds

            // Find which device this request belongs to
            val clickedRequest = currentState.requestHistory.deviceColumns
                .flatMap { it.requests }
                .find { it.id == requestId }

            val newSelectedIds = if (requestId in currentIds) {
                // Toggle off: remove this request
                currentIds - requestId
            } else if (clickedRequest != null) {
                // Find all requests from the same device
                val sameDeviceRequestIds = currentState.requestHistory.deviceColumns
                    .filter { it.deviceId == clickedRequest.deviceId }
                    .flatMap { it.requests }
                    .map { it.id }
                    .toSet()

                // Remove all requests from the same device, then add the clicked request
                (currentIds - sameDeviceRequestIds) + requestId
            } else {
                // Fallback: just add the request
                currentIds + requestId
            }

            _uiState.value = currentState.copy(
                requestHistory = currentState.requestHistory.copy(selectedRequestIds = newSelectedIds),
            )
        }
    }

    // Mock Data Functions
    private fun mockRegisteredDevices() = listOf(
        RegisteredDeviceDisplay(
            id = "550e8400-e29b-41d4-a716-446655440000",
            name = "Test Device 1",
            testCaseId = "test-case-abc",
            isEnabled = true,
            delaySettings = DelaySettingsDisplay(
                type = DelayType.PRESET,
                delayMs = 5000,
                targetFiles = emptyList(),
                isEnabled = true,
            ),
        ),
        RegisteredDeviceDisplay(
            id = "660f9511-f3ac-52e5-b827-557766551111",
            name = "Production Device",
            testCaseId = "prod-test-001",
            isEnabled = false,
            delaySettings = DelaySettingsDisplay(
                type = DelayType.OFF,
                delayMs = null,
                targetFiles = emptyList(),
                isEnabled = false,
            ),
        ),
        RegisteredDeviceDisplay(
            id = "770g0622-g4bd-63f6-c938-668877662222",
            name = "Development Device",
            testCaseId = "dev-test-123",
            isEnabled = true,
            delaySettings = DelaySettingsDisplay(
                type = DelayType.CUSTOM,
                delayMs = 30000,
                targetFiles = listOf("api/user.json", "api/device.json", "api/test.json"),
                isEnabled = true,
            ),
        ),
    )

    private fun mockServerDevices() = ServerDevicesState(
        devices = (1..20).map { index ->
            val deviceId = "device-${index.toString().padStart(3, '0')}"
            ServerDeviceDisplay(
                deviceId = deviceId,
                isRegistered = index <= 3, // First 3 are registered
            )
        } + listOf(
            ServerDeviceDisplay("550e8400-e29b-41d4-a716-446655440000", true),
            ServerDeviceDisplay("660f9511-f3ac-52e5-b827-557766551111", true),
            ServerDeviceDisplay("770g0622-g4bd-63f6-c938-668877662222", true),
        ),
        filterText = "",
        isLoading = false,
        error = null,
    )

    private fun mockRequestHistory() = RequestHistoryState(
        deviceColumns = listOf(
            DeviceRequestColumn(
                deviceId = "550e8400-e29b-41d4-a716-446655440000",
                deviceName = "Test Device 1",
                requests = listOf(
                    RequestInfo(
                        id = "req-001",
                        method = HttpMethod.GET,
                        path = "/api/user",
                        statusCode = 200,
                        statusText = "OK",
                        timestamp = Clock.System.now().minus(5.minutes),
                        deviceId = "550e8400-e29b-41d4-a716-446655440000",
                        responseBody = """{"id": 1, "name": "John Doe"}""",
                        durationMs = 45,
                    ),
                    RequestInfo(
                        id = "req-002",
                        method = HttpMethod.POST,
                        path = "/api/device",
                        statusCode = 201,
                        statusText = "Created",
                        timestamp = Clock.System.now().minus(10.minutes),
                        deviceId = "550e8400-e29b-41d4-a716-446655440000",
                        requestBody = """{"name": "New Device"}""",
                        responseBody = """{"id": 2, "name": "New Device"}""",
                        durationMs = 123,
                    ),
                    RequestInfo(
                        id = "req-003",
                        method = HttpMethod.GET,
                        path = "/api/settings",
                        statusCode = 200,
                        statusText = "OK",
                        timestamp = Clock.System.now().minus(15.minutes),
                        deviceId = "550e8400-e29b-41d4-a716-446655440000",
                        durationMs = 32,
                    ),
                    RequestInfo(
                        id = "req-004",
                        method = HttpMethod.DELETE,
                        path = "/api/user/123",
                        statusCode = 404,
                        statusText = "Not Found",
                        timestamp = Clock.System.now().minus(1.hours),
                        deviceId = "550e8400-e29b-41d4-a716-446655440000",
                        durationMs = 28,
                    ),
                ),
            ),
            DeviceRequestColumn(
                deviceId = "770g0622-g4bd-63f6-c938-668877662222",
                deviceName = "Development Device",
                requests = listOf(
                    RequestInfo(
                        id = "req-005",
                        method = HttpMethod.GET,
                        path = "/api/config",
                        statusCode = 200,
                        statusText = "OK",
                        timestamp = Clock.System.now().minus(2.minutes),
                        deviceId = "770g0622-g4bd-63f6-c938-668877662222",
                        durationMs = 54,
                    ),
                    RequestInfo(
                        id = "req-006",
                        method = HttpMethod.PUT,
                        path = "/api/config",
                        statusCode = 500,
                        statusText = "Internal Server Error",
                        timestamp = Clock.System.now().minus(25.hours),
                        deviceId = "770g0622-g4bd-63f6-c938-668877662222",
                        durationMs = 234,
                    ),
                ),
            ),
        ),
        filters = RequestFilters(
            searchText = "",
            selectedMethods = emptySet(),
            selectedStatusCategories = emptySet(),
            timeRange = TimeRange.ALL,
            sortOrder = SortOrder.NEWEST_FIRST,
        ),
        showAdvancedFilters = false,
        selectedRequestIds = emptySet(),
    )

    private fun mockMarkdownContent(testCaseId: String) = """
        # Test Case: $testCaseId

        ## Overview
        This is a mock test case for device management.

        ## Test Steps
        1. Configure the device settings
        2. Enable the device
        3. Verify API responses
        4. Check delay settings

        ## Expected Results
        - Device should be enabled
        - API requests should be delayed according to settings
        - All responses should return successfully

        ## Notes
        - This is mock data for demonstration purposes
        - Real test cases would be loaded from README.md files
    """.trimIndent()

    private fun mockAvailableFiles() = listOf(
        "api/user.json",
        "api/device.json",
        "api/test.json",
        "api/settings.json",
        "api/config.json",
    )
}
