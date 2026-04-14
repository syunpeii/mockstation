package com.github.syunpeii.mockstation.app.ui.devicemanagement

import com.github.syunpeii.mockstation.core.model.DelayType
import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange
import com.github.syunpeii.mockstation.core.model.getStatusCategory
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

sealed interface DeviceManagementUiState {
    data object Loading : DeviceManagementUiState
    data class Stable(
        val selectedTabIndex: Int,
        val registeredDevices: List<RegisteredDeviceDisplay>,
        val serverDevices: ServerDevicesState,
        val requestHistory: RequestHistoryState,
        val dialogState: DialogState,
    ) : DeviceManagementUiState

    data class Error(
        val message: String,
    ) : DeviceManagementUiState
}

data class RegisteredDeviceDisplay(
    val id: String,
    val name: String,
    val testCaseId: String,
    val isEnabled: Boolean,
    val delaySettings: DelaySettingsDisplay,
)

data class DelaySettingsDisplay(
    val type: DelayType,
    val delayMs: Int?,
    val targetFiles: List<String>,
    val isEnabled: Boolean,
) {
    fun getFormattedDelay(): String = when {
        type == DelayType.OFF -> "OFF"
        delayMs == null -> "N/A"
        else -> "${delayMs}ms"
    }

    fun getFormattedTargetFiles(): String = when {
        targetFiles.isEmpty() -> "All files"
        targetFiles.size == 1 -> targetFiles.first()
        else -> "${targetFiles.size} files"
    }
}

data class ServerDevicesState(
    val devices: List<ServerDeviceDisplay>,
    val filterText: String,
    val isLoading: Boolean,
    val error: String?,
) {
    val filteredDevices: List<ServerDeviceDisplay>
        get() = devices.filter {
            it.deviceId.contains(filterText, ignoreCase = true)
        }
}

data class ServerDeviceDisplay(
    val deviceId: String,
    val isRegistered: Boolean,
)

@OptIn(ExperimentalTime::class)
data class RequestHistoryState(
    val deviceColumns: List<DeviceRequestColumn>,
    val filters: RequestFilters,
    val selectedRequestIds: Set<String>,
    val showAdvancedFilters: Boolean,
) {
    val filteredDeviceColumns: List<DeviceRequestColumn>
        get() = deviceColumns
            .asSequence()
            .map { column ->
                column.copy(
                    requests = column.requests
                        .filter { request -> filters.matches(request) }
                        .let { filtered ->
                            when (filters.sortOrder) {
                                SortOrder.NEWEST_FIRST -> filtered.sortedByDescending { it.timestamp }
                                SortOrder.OLDEST_FIRST -> filtered.sortedBy { it.timestamp }
                            }
                        },
                )
            }
            .filter { it.requests.isNotEmpty() }
            .toList()
}

@OptIn(ExperimentalTime::class)
data class RequestFilters(
    val searchText: String,
    val selectedMethods: Set<HttpMethod>,
    val selectedStatusCategories: Set<StatusCategory>,
    val timeRange: TimeRange,
    val sortOrder: SortOrder,
) {
    fun matches(request: RequestInfo): Boolean {
        if (searchText.isNotEmpty() &&
            !request.path.contains(searchText, ignoreCase = true)
        ) {
            return false
        }

        if (selectedMethods.isNotEmpty() &&
            request.method !in selectedMethods
        ) {
            return false
        }

        if (selectedStatusCategories.isNotEmpty() &&
            request.statusCode.getStatusCategory() !in selectedStatusCategories
        ) {
            return false
        }

        val now = Clock.System.now()
        val startTime = when (timeRange) {
            TimeRange.LAST_HOUR -> now.minus(1.hours)
            TimeRange.LAST_24_HOURS -> now.minus(1.days)
            TimeRange.LAST_7_DAYS -> now.minus(7.days)
            TimeRange.ALL -> null
        }

        return !(startTime != null && request.timestamp < startTime)
    }
}

data class DeviceRequestColumn(
    val deviceId: String,
    val deviceName: String?,
    val requests: List<RequestInfo>,
)

sealed interface DialogState {
    data object None : DialogState
    data class ShowMarkdown(
        val testCaseId: String,
        val content: String,
    ) : DialogState

    data class EditDeviceName(
        val deviceId: String,
        val currentName: String,
    ) : DialogState

    data class DeleteConfirmation(
        val deviceId: String,
        val deviceName: String,
    ) : DialogState

    data class EditDelaySettings(
        val deviceId: String,
        val currentSettings: DelaySettingsDisplay,
        val availableFiles: List<String>,
    ) : DialogState
}
