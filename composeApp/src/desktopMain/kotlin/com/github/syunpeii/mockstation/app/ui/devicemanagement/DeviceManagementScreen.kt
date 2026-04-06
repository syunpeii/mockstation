package com.github.syunpeii.mockstation.app.ui.devicemanagement

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.app.ui.devicemanagement.model.DeviceManagementTab
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.DelaySettings
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.DelaySettingsDialog
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.DelayType
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.DeleteConfirmationDialog
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.EditDeviceNameDialog
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.MarkdownDialog
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.TabBar
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ApiRequestHistoryTab
import com.github.syunpeii.mockstation.core.designsystem.component.organism.DeviceRequestHistory
import com.github.syunpeii.mockstation.core.designsystem.component.organism.RegisteredDevice
import com.github.syunpeii.mockstation.core.designsystem.component.organism.RegisteredDevicesTab
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ServerDevice
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ServerDeviceListTab
import com.github.syunpeii.mockstation.core.designsystem.resources.ComposeStringResource
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.TimeRange
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.common_cancel
import mockstation.composeapp.generated.resources.common_close
import mockstation.composeapp.generated.resources.common_error
import mockstation.composeapp.generated.resources.common_save
import mockstation.composeapp.generated.resources.device_management_add_device
import mockstation.composeapp.generated.resources.device_management_adjust_filter
import mockstation.composeapp.generated.resources.device_management_delay_settings
import mockstation.composeapp.generated.resources.device_management_delay_type_custom
import mockstation.composeapp.generated.resources.device_management_delay_type_off
import mockstation.composeapp.generated.resources.device_management_delay_type_preset
import mockstation.composeapp.generated.resources.device_management_delete
import mockstation.composeapp.generated.resources.device_management_detail_duration
import mockstation.composeapp.generated.resources.device_management_detail_method
import mockstation.composeapp.generated.resources.device_management_detail_path
import mockstation.composeapp.generated.resources.device_management_detail_request_body
import mockstation.composeapp.generated.resources.device_management_detail_request_headers
import mockstation.composeapp.generated.resources.device_management_detail_response_body
import mockstation.composeapp.generated.resources.device_management_detail_response_headers
import mockstation.composeapp.generated.resources.device_management_detail_status
import mockstation.composeapp.generated.resources.device_management_detail_timestamp
import mockstation.composeapp.generated.resources.device_management_detail_title
import mockstation.composeapp.generated.resources.device_management_device
import mockstation.composeapp.generated.resources.device_management_device_id
import mockstation.composeapp.generated.resources.device_management_device_name
import mockstation.composeapp.generated.resources.device_management_dialog_delay_clear_all
import mockstation.composeapp.generated.resources.device_management_dialog_delay_custom
import mockstation.composeapp.generated.resources.device_management_dialog_delay_enable
import mockstation.composeapp.generated.resources.device_management_dialog_delay_no_files
import mockstation.composeapp.generated.resources.device_management_dialog_delay_placeholder
import mockstation.composeapp.generated.resources.device_management_dialog_delay_preset
import mockstation.composeapp.generated.resources.device_management_dialog_delay_select_all
import mockstation.composeapp.generated.resources.device_management_dialog_delay_target_files
import mockstation.composeapp.generated.resources.device_management_dialog_delay_title
import mockstation.composeapp.generated.resources.device_management_dialog_delay_type
import mockstation.composeapp.generated.resources.device_management_dialog_delete_message
import mockstation.composeapp.generated.resources.device_management_dialog_delete_title
import mockstation.composeapp.generated.resources.device_management_dialog_edit_name_title
import mockstation.composeapp.generated.resources.device_management_dialog_testcase_title
import mockstation.composeapp.generated.resources.device_management_disabled
import mockstation.composeapp.generated.resources.device_management_edit_name
import mockstation.composeapp.generated.resources.device_management_empty_devices
import mockstation.composeapp.generated.resources.device_management_empty_devices_hint
import mockstation.composeapp.generated.resources.device_management_enable_devices_hint
import mockstation.composeapp.generated.resources.device_management_enabled
import mockstation.composeapp.generated.resources.device_management_error_loading
import mockstation.composeapp.generated.resources.device_management_history_filter_advanced
import mockstation.composeapp.generated.resources.device_management_history_filter_methods
import mockstation.composeapp.generated.resources.device_management_history_filter_status
import mockstation.composeapp.generated.resources.device_management_history_filter_time_range
import mockstation.composeapp.generated.resources.device_management_history_search_placeholder
import mockstation.composeapp.generated.resources.device_management_history_sort_newest
import mockstation.composeapp.generated.resources.device_management_history_sort_oldest
import mockstation.composeapp.generated.resources.device_management_history_status_2xx
import mockstation.composeapp.generated.resources.device_management_history_status_3xx
import mockstation.composeapp.generated.resources.device_management_history_status_4xx
import mockstation.composeapp.generated.resources.device_management_history_status_5xx
import mockstation.composeapp.generated.resources.device_management_history_status_other
import mockstation.composeapp.generated.resources.device_management_history_time_all
import mockstation.composeapp.generated.resources.device_management_history_time_last_24h
import mockstation.composeapp.generated.resources.device_management_history_time_last_7d
import mockstation.composeapp.generated.resources.device_management_history_time_last_hour
import mockstation.composeapp.generated.resources.device_management_history_timestamp_days_ago
import mockstation.composeapp.generated.resources.device_management_history_timestamp_hours_ago
import mockstation.composeapp.generated.resources.device_management_history_timestamp_just_now
import mockstation.composeapp.generated.resources.device_management_history_timestamp_minutes_ago
import mockstation.composeapp.generated.resources.device_management_loading_devices
import mockstation.composeapp.generated.resources.device_management_no_devices_found
import mockstation.composeapp.generated.resources.device_management_no_history
import mockstation.composeapp.generated.resources.device_management_no_requests
import mockstation.composeapp.generated.resources.device_management_register
import mockstation.composeapp.generated.resources.device_management_registered
import mockstation.composeapp.generated.resources.device_management_requests
import mockstation.composeapp.generated.resources.device_management_search_hint
import mockstation.composeapp.generated.resources.device_management_tab_history
import mockstation.composeapp.generated.resources.device_management_tab_registered
import mockstation.composeapp.generated.resources.device_management_tab_server
import mockstation.composeapp.generated.resources.device_management_test_case
import mockstation.composeapp.generated.resources.device_management_title
import mockstation.composeapp.generated.resources.device_management_unnamed_device
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
internal fun DeviceManagementScreen(
    viewModel: DeviceManagementViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    DeviceManagementBaseScreen(
        uiState = uiState,
        onTabChange = viewModel::onTabChange,
        onFilterChange = viewModel::onFilterChange,
        onRegisterDevice = viewModel::onRegisterDevice,
        onEditName = viewModel::onEditDeviceName,
        onSaveDeviceName = viewModel::onSaveDeviceName,
        onDeleteDevice = viewModel::onDeleteDevice,
        onConfirmDelete = viewModel::onConfirmDelete,
        onToggleEnabled = viewModel::onToggleEnabled,
        onTestCaseClick = viewModel::onTestCaseClick,
        onDelaySettingsClick = viewModel::onDelaySettingsClick,
        onSaveDelaySettings = viewModel::onSaveDelaySettings,
        onDismissDialog = viewModel::onDismissDialog,
        onNavigateToServerTab = viewModel::onNavigateToServerTab,
        onRequestSearchChange = viewModel::onRequestSearchChange,
        onMethodToggle = viewModel::onMethodToggle,
        onStatusCategoryToggle = viewModel::onStatusCategoryToggle,
        onTimeRangeChange = viewModel::onTimeRangeChange,
        onSortOrderToggle = viewModel::onSortOrderToggle,
        onShowAdvancedFiltersChange = viewModel::onShowAdvancedFiltersChange,
        onRequestClick = viewModel::onRequestClick,
    )
}

@Composable
private fun DeviceManagementBaseScreen(
    uiState: DeviceManagementUiState,
    onTabChange: (Int) -> Unit,
    onFilterChange: (String) -> Unit,
    onRegisterDevice: (String) -> Unit,
    onEditName: (String) -> Unit,
    onSaveDeviceName: (String, String) -> Unit,
    onDeleteDevice: (String) -> Unit,
    onConfirmDelete: (String) -> Unit,
    onToggleEnabled: (String, Boolean) -> Unit,
    onTestCaseClick: (String) -> Unit,
    onDelaySettingsClick: (String) -> Unit,
    onSaveDelaySettings: (String, DelaySettingsDisplay) -> Unit,
    onDismissDialog: () -> Unit,
    onNavigateToServerTab: () -> Unit,
    onRequestSearchChange: (String) -> Unit,
    onMethodToggle: (com.github.syunpeii.mockstation.core.model.HttpMethod) -> Unit,
    onStatusCategoryToggle: (com.github.syunpeii.mockstation.core.model.StatusCategory) -> Unit,
    onTimeRangeChange: (TimeRange) -> Unit,
    onSortOrderToggle: () -> Unit,
    onShowAdvancedFiltersChange: (Boolean) -> Unit,
    onRequestClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MockStationTheme.colors.background),
    ) {
        when (uiState) {
            is DeviceManagementUiState.Loading -> DeviceManagementScreenLoading()
            is DeviceManagementUiState.Stable -> DeviceManagementScreenContent(
                uiState = uiState,
                onTabChange = onTabChange,
                onFilterChange = onFilterChange,
                onRegisterDevice = onRegisterDevice,
                onEditName = onEditName,
                onSaveDeviceName = onSaveDeviceName,
                onDeleteDevice = onDeleteDevice,
                onConfirmDelete = onConfirmDelete,
                onToggleEnabled = onToggleEnabled,
                onTestCaseClick = onTestCaseClick,
                onDelaySettingsClick = onDelaySettingsClick,
                onSaveDelaySettings = onSaveDelaySettings,
                onDismissDialog = onDismissDialog,
                onNavigateToServerTab = onNavigateToServerTab,
                onRequestSearchChange = onRequestSearchChange,
                onMethodToggle = onMethodToggle,
                onStatusCategoryToggle = onStatusCategoryToggle,
                onTimeRangeChange = onTimeRangeChange,
                onSortOrderToggle = onSortOrderToggle,
                onShowAdvancedFiltersChange = onShowAdvancedFiltersChange,
                onRequestClick = onRequestClick,
            )

            is DeviceManagementUiState.Error -> DeviceManagementScreenError(
                message = uiState.message,
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun DeviceManagementScreenContent(
    uiState: DeviceManagementUiState.Stable,
    onTabChange: (Int) -> Unit,
    onFilterChange: (String) -> Unit,
    onRegisterDevice: (String) -> Unit,
    onEditName: (String) -> Unit,
    onSaveDeviceName: (String, String) -> Unit,
    onDeleteDevice: (String) -> Unit,
    onConfirmDelete: (String) -> Unit,
    onToggleEnabled: (String, Boolean) -> Unit,
    onTestCaseClick: (String) -> Unit,
    onDelaySettingsClick: (String) -> Unit,
    onSaveDelaySettings: (String, DelaySettingsDisplay) -> Unit,
    onDismissDialog: () -> Unit,
    onNavigateToServerTab: () -> Unit,
    onRequestSearchChange: (String) -> Unit,
    onMethodToggle: (com.github.syunpeii.mockstation.core.model.HttpMethod) -> Unit,
    onStatusCategoryToggle: (com.github.syunpeii.mockstation.core.model.StatusCategory) -> Unit,
    onTimeRangeChange: (TimeRange) -> Unit,
    onSortOrderToggle: () -> Unit,
    onShowAdvancedFiltersChange: (Boolean) -> Unit,
    onRequestClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(Res.string.device_management_title),
            style = MockStationTheme.typography.headlineMedium,
            color = MockStationTheme.colors.onBackground,
            modifier = Modifier.padding(MockStationTheme.spacing.medium),
        )

        TabBar(
            tabs = listOf(
                stringResource(Res.string.device_management_tab_registered),
                stringResource(Res.string.device_management_tab_server),
                stringResource(Res.string.device_management_tab_history),
            ),
            selectedTabIndex = uiState.selectedTabIndex,
            onTabSelected = onTabChange,
            modifier = Modifier.fillMaxWidth(),
        )

        when (DeviceManagementTab.fromIndex(uiState.selectedTabIndex)) {
            DeviceManagementTab.REGISTERED_DEVICES -> RegisteredDevicesTab(
                devices = uiState.registeredDevices.map { device ->
                    RegisteredDevice(
                        id = device.id,
                        name = device.name,
                        testCaseId = device.testCaseId,
                        isEnabled = device.isEnabled,
                        delaySettingsDisplay = buildDelaySettingsDisplay(device.delaySettings),
                    )
                },
                onEditDeviceName = onEditName,
                onDeleteDevice = onDeleteDevice,
                onToggleEnabled = onToggleEnabled,
                onTestCaseClick = onTestCaseClick,
                onDelaySettingsClick = onDelaySettingsClick,
                onAddDevice = onNavigateToServerTab,
                emptyDevicesLabel = stringResource(Res.string.device_management_empty_devices),
                emptyDevicesHintLabel = stringResource(Res.string.device_management_empty_devices_hint),
                addDeviceLabel = stringResource(Res.string.device_management_add_device),
                deviceIdLabel = stringResource(Res.string.device_management_device_id),
                editNameLabel = stringResource(Res.string.device_management_edit_name),
                deleteLabel = stringResource(Res.string.device_management_delete),
                deviceNameLabel = stringResource(Res.string.device_management_device_name),
                testCaseLabel = stringResource(Res.string.device_management_test_case),
                delaySettingsLabel = stringResource(Res.string.device_management_delay_settings),
                enabledLabel = stringResource(Res.string.device_management_enabled),
                disabledLabel = stringResource(Res.string.device_management_disabled),
            )

            DeviceManagementTab.SERVER_DEVICES -> ServerDeviceListTab(
                devices = uiState.serverDevices.filteredDevices.map { device ->
                    ServerDevice(
                        deviceId = device.deviceId,
                        isRegistered = device.isRegistered,
                    )
                },
                filterText = uiState.serverDevices.filterText,
                onFilterChange = onFilterChange,
                onRegisterDevice = onRegisterDevice,
                isLoading = uiState.serverDevices.isLoading,
                error = uiState.serverDevices.error,
                searchPlaceholder = stringResource(Res.string.device_management_search_hint),
                loadingLabel = stringResource(Res.string.device_management_loading_devices),
                errorLabel = stringResource(Res.string.device_management_error_loading),
                noDevicesLabel = stringResource(Res.string.device_management_no_devices_found),
                adjustFilterLabel = stringResource(Res.string.device_management_adjust_filter),
                deviceIdLabel = stringResource(Res.string.device_management_device_id),
                registeredLabel = stringResource(Res.string.device_management_registered),
                registerButtonLabel = stringResource(Res.string.device_management_register),
            )

            DeviceManagementTab.REQUEST_HISTORY -> {
                val enabledDeviceIds = uiState.registeredDevices
                    .filter { it.isEnabled }
                    .map { it.id }
                    .toSet()

                val filteredDeviceColumns = uiState.requestHistory.filteredDeviceColumns
                    .filter { column -> enabledDeviceIds.contains(column.deviceId) }

                val deviceHistories = filteredDeviceColumns.map { column ->
                    DeviceRequestHistory(
                        deviceId = column.deviceId,
                        deviceName = column.deviceName,
                        requests = column.requests,
                    )
                }
                val requestsLabels = deviceHistories.map { device ->
                    ComposeStringResource(resourceId = Res.string.device_management_requests, device.requests.size).getString()
                }

                val statusLabels = mapOf(
                    com.github.syunpeii.mockstation.core.model.StatusCategory.SUCCESS_2XX to stringResource(Res.string.device_management_history_status_2xx),
                    com.github.syunpeii.mockstation.core.model.StatusCategory.REDIRECT_3XX to stringResource(Res.string.device_management_history_status_3xx),
                    com.github.syunpeii.mockstation.core.model.StatusCategory.CLIENT_ERROR_4XX to stringResource(Res.string.device_management_history_status_4xx),
                    com.github.syunpeii.mockstation.core.model.StatusCategory.SERVER_ERROR_5XX to stringResource(Res.string.device_management_history_status_5xx),
                    com.github.syunpeii.mockstation.core.model.StatusCategory.OTHER to stringResource(Res.string.device_management_history_status_other),
                )

                val timeRangeLabels = mapOf(
                    TimeRange.LAST_HOUR to stringResource(Res.string.device_management_history_time_last_hour),
                    TimeRange.LAST_24_HOURS to stringResource(Res.string.device_management_history_time_last_24h),
                    TimeRange.LAST_7_DAYS to stringResource(Res.string.device_management_history_time_last_7d),
                    TimeRange.ALL to stringResource(Res.string.device_management_history_time_all),
                )

                val timestampJustNow = stringResource(Res.string.device_management_history_timestamp_just_now)
                val timestampMinutesAgo = stringResource(Res.string.device_management_history_timestamp_minutes_ago)
                val timestampHoursAgo = stringResource(Res.string.device_management_history_timestamp_hours_ago)
                val timestampDaysAgo = stringResource(Res.string.device_management_history_timestamp_days_ago)

                ApiRequestHistoryTab(
                    deviceHistories = deviceHistories,
                    searchText = uiState.requestHistory.filters.searchText,
                    selectedMethods = uiState.requestHistory.filters.selectedMethods,
                    selectedStatusCategories = uiState.requestHistory.filters.selectedStatusCategories,
                    selectedTimeRange = uiState.requestHistory.filters.timeRange,
                    sortOrder = uiState.requestHistory.filters.sortOrder,
                    showAdvancedFilters = uiState.requestHistory.showAdvancedFilters,
                    selectedRequestIds = uiState.requestHistory.selectedRequestIds,
                    onSearchChange = onRequestSearchChange,
                    onMethodToggle = onMethodToggle,
                    onStatusCategoryToggle = onStatusCategoryToggle,
                    onTimeRangeChange = onTimeRangeChange,
                    onSortOrderToggle = onSortOrderToggle,
                    onShowAdvancedFiltersChange = onShowAdvancedFiltersChange,
                    onRequestClick = onRequestClick,
                    onCloseDetailPanel = onRequestClick, // Use onRequestClick for toggle behavior
                    getTimestampLabel = { request ->
                        val now = Clock.System.now()
                        val duration = now - request.timestamp
                        when {
                            duration.inWholeMinutes < 1 -> timestampJustNow
                            duration.inWholeMinutes < 60 -> String.format(timestampMinutesAgo, duration.inWholeMinutes)
                            duration.inWholeHours < 24 -> String.format(timestampHoursAgo, duration.inWholeHours)
                            else -> String.format(timestampDaysAgo, duration.inWholeDays)
                        }
                    },
                    noHistoryLabel = stringResource(Res.string.device_management_no_history),
                    enableDevicesHintLabel = stringResource(Res.string.device_management_enable_devices_hint),
                    deviceLabel = stringResource(Res.string.device_management_device),
                    unnamedDeviceLabel = stringResource(Res.string.device_management_unnamed_device),
                    requestsLabels = requestsLabels,
                    noRequestsLabel = stringResource(Res.string.device_management_no_requests),
                    searchPlaceholder = stringResource(Res.string.device_management_history_search_placeholder),
                    advancedFiltersLabel = stringResource(Res.string.device_management_history_filter_advanced),
                    methodsLabel = stringResource(Res.string.device_management_history_filter_methods),
                    statusLabel = stringResource(Res.string.device_management_history_filter_status),
                    timeRangeLabel = stringResource(Res.string.device_management_history_filter_time_range),
                    newestFirstLabel = stringResource(Res.string.device_management_history_sort_newest),
                    oldestFirstLabel = stringResource(Res.string.device_management_history_sort_oldest),
                    getMethodLabel = { it.toDisplayString() },
                    getStatusCategoryLabel = { category ->
                        statusLabels[category] ?: ""
                    },
                    getTimeRangeLabel = { range ->
                        timeRangeLabels[range] ?: ""
                    },
                    requestDetailTitleLabel = stringResource(Res.string.device_management_detail_title),
                    requestDetailMethodLabel = stringResource(Res.string.device_management_detail_method),
                    requestDetailPathLabel = stringResource(Res.string.device_management_detail_path),
                    requestDetailStatusLabel = stringResource(Res.string.device_management_detail_status),
                    requestDetailTimestampLabel = stringResource(Res.string.device_management_detail_timestamp),
                    requestDetailDurationLabel = stringResource(Res.string.device_management_detail_duration),
                    requestDetailRequestHeadersLabel = stringResource(Res.string.device_management_detail_request_headers),
                    requestDetailResponseHeadersLabel = stringResource(Res.string.device_management_detail_response_headers),
                    requestDetailRequestBodyLabel = stringResource(Res.string.device_management_detail_request_body),
                    requestDetailResponseBodyLabel = stringResource(Res.string.device_management_detail_response_body),
                )
            }

            null -> {
                // Invalid tab index
            }
        }
    }

    when (val dialog = uiState.dialogState) {
        is DialogState.None -> {
            // no-op
        }

        is DialogState.ShowMarkdown -> {
            MarkdownDialog(
                title = ComposeStringResource(
                    resourceId = Res.string.device_management_dialog_testcase_title,
                    dialog.testCaseId,
                ).getString(),
                markdownContent = dialog.content,
                onDismiss = onDismissDialog,
                closeLabel = stringResource(Res.string.common_close),
            )
        }

        is DialogState.EditDeviceName -> {
            EditDeviceNameDialog(
                currentName = dialog.currentName,
                onDismiss = onDismissDialog,
                onSave = { newName -> onSaveDeviceName(dialog.deviceId, newName) },
                title = stringResource(Res.string.device_management_dialog_edit_name_title),
                label = stringResource(Res.string.device_management_device_name),
                saveLabel = stringResource(Res.string.common_save),
                cancelLabel = stringResource(Res.string.common_cancel),
            )
        }

        is DialogState.DeleteConfirmation -> {
            DeleteConfirmationDialog(
                onDismiss = onDismissDialog,
                onConfirm = { onConfirmDelete(dialog.deviceId) },
                title = stringResource(Res.string.device_management_dialog_delete_title),
                message = ComposeStringResource(resourceId = Res.string.device_management_dialog_delete_message, dialog.deviceName).getString(),
                confirmLabel = stringResource(Res.string.device_management_delete),
                cancelLabel = stringResource(Res.string.common_cancel),
            )
        }

        is DialogState.EditDelaySettings -> {
            DelaySettingsDialog(
                initialSettings = DelaySettings(
                    type = when (dialog.currentSettings.type) {
                        com.github.syunpeii.mockstation.app.ui.devicemanagement.DelayType.OFF -> DelayType.OFF
                        com.github.syunpeii.mockstation.app.ui.devicemanagement.DelayType.PRESET -> DelayType.PRESET
                        com.github.syunpeii.mockstation.app.ui.devicemanagement.DelayType.CUSTOM -> DelayType.CUSTOM
                    },
                    delayMs = dialog.currentSettings.delayMs?.toString() ?: "",
                    isEnabled = dialog.currentSettings.isEnabled,
                    targetFiles = dialog.currentSettings.targetFiles.toSet(),
                ),
                availableFiles = dialog.availableFiles,
                onDismiss = onDismissDialog,
                onSave = { settings ->
                    val delaySettingsDisplay = DelaySettingsDisplay(
                        type = when (settings.type) {
                            DelayType.OFF -> com.github.syunpeii.mockstation.app.ui.devicemanagement.DelayType.OFF
                            DelayType.PRESET -> com.github.syunpeii.mockstation.app.ui.devicemanagement.DelayType.PRESET
                            DelayType.CUSTOM -> com.github.syunpeii.mockstation.app.ui.devicemanagement.DelayType.CUSTOM
                        },
                        delayMs = settings.delayMs.toIntOrNull(),
                        isEnabled = settings.isEnabled,
                        targetFiles = settings.targetFiles.toList(),
                    )
                    onSaveDelaySettings(dialog.deviceId, delaySettingsDisplay)
                },
                title = stringResource(Res.string.device_management_dialog_delay_title),
                delayTypeLabel = stringResource(Res.string.device_management_dialog_delay_type),
                delayTypeOffLabel = stringResource(Res.string.device_management_delay_type_off),
                delayTypePresetLabel = stringResource(Res.string.device_management_delay_type_preset),
                delayTypeCustomLabel = stringResource(Res.string.device_management_delay_type_custom),
                presetDelayLabel = stringResource(Res.string.device_management_dialog_delay_preset),
                customDelayLabel = stringResource(Res.string.device_management_dialog_delay_custom),
                targetFilesLabel = stringResource(Res.string.device_management_dialog_delay_target_files),
                selectAllLabel = stringResource(Res.string.device_management_dialog_delay_select_all),
                clearAllLabel = stringResource(Res.string.device_management_dialog_delay_clear_all),
                enableLabel = stringResource(Res.string.device_management_dialog_delay_enable),
                noFilesLabel = stringResource(Res.string.device_management_dialog_delay_no_files),
                placeholderLabel = stringResource(Res.string.device_management_dialog_delay_placeholder),
                saveLabel = stringResource(Res.string.common_save),
                cancelLabel = stringResource(Res.string.common_cancel),
            )
        }
    }
}

@Composable
private fun DeviceManagementScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = MockStationTheme.colors.primary)
    }
}

@Composable
private fun DeviceManagementScreenError(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(MockStationTheme.spacing.extraLarge),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(Res.string.common_error),
                style = MockStationTheme.typography.bodyLarge,
                color = MockStationTheme.colors.error,
            )
            Text(
                text = message,
                style = MockStationTheme.typography.bodyMedium,
                color = MockStationTheme.colors.onBackground,
                modifier = Modifier.padding(top = MockStationTheme.spacing.small),
            )
        }
    }
}

private fun buildDelaySettingsDisplay(settings: DelaySettingsDisplay): String {
    val typeStr = settings.type.name
    val delayStr = settings.getFormattedDelay()
    val filesStr = settings.getFormattedTargetFiles()

    return when (settings.type) {
        com.github.syunpeii.mockstation.app.ui.devicemanagement.DelayType.OFF -> "OFF"
        else -> "$typeStr: $delayStr ($filesStr)"
    }
}

@Preview
@Composable
private fun PreviewDeviceManagementScreenLoading() {
    MockStationTheme {
        DeviceManagementBaseScreen(
            uiState = DeviceManagementUiState.Loading,
            onTabChange = {},
            onFilterChange = {},
            onRegisterDevice = {},
            onEditName = {},
            onSaveDeviceName = { _, _ -> },
            onDeleteDevice = {},
            onConfirmDelete = {},
            onToggleEnabled = { _, _ -> },
            onTestCaseClick = {},
            onDelaySettingsClick = {},
            onSaveDelaySettings = { _, _ -> },
            onDismissDialog = {},
            onNavigateToServerTab = {},
            onRequestSearchChange = {},
            onMethodToggle = {},
            onStatusCategoryToggle = {},
            onTimeRangeChange = {},
            onSortOrderToggle = {},
            onShowAdvancedFiltersChange = {},
            onRequestClick = {},
        )
    }
}

@Preview
@Composable
private fun PreviewDeviceManagementScreenStable() {
    MockStationTheme {
        DeviceManagementBaseScreen(
            uiState = DeviceManagementUiState.Stable(
                selectedTabIndex = 0,
                registeredDevices = listOf(
                    RegisteredDeviceDisplay(
                        id = "550e8400-e29b-41d4-a716-446655440000",
                        name = "Test Device",
                        testCaseId = "test-case-abc",
                        isEnabled = true,
                        delaySettings = DelaySettingsDisplay(
                            type = com.github.syunpeii.mockstation.app.ui.devicemanagement.DelayType.PRESET,
                            delayMs = 5000,
                            targetFiles = emptyList(),
                            isEnabled = true,
                        ),
                    ),
                ),
                serverDevices = ServerDevicesState(
                    devices = listOf(
                        ServerDeviceDisplay(
                            deviceId = "550e8400-e29b-41d4-a716-446655440001",
                            isRegistered = false,
                        ),
                    ),
                    filterText = "",
                    isLoading = false,
                    error = null,
                ),
                requestHistory = RequestHistoryState(
                    deviceColumns = emptyList(),
                    filters = RequestFilters(
                        searchText = "",
                        selectedMethods = emptySet(),
                        selectedStatusCategories = emptySet(),
                        timeRange = TimeRange.ALL,
                        sortOrder = SortOrder.NEWEST_FIRST,
                    ),
                    showAdvancedFilters = false,
                    selectedRequestIds = emptySet(),
                ),
                dialogState = DialogState.None,
            ),
            onTabChange = {},
            onFilterChange = {},
            onRegisterDevice = {},
            onEditName = {},
            onSaveDeviceName = { _, _ -> },
            onDeleteDevice = {},
            onConfirmDelete = {},
            onToggleEnabled = { _, _ -> },
            onTestCaseClick = {},
            onDelaySettingsClick = {},
            onSaveDelaySettings = { _, _ -> },
            onDismissDialog = {},
            onNavigateToServerTab = {},
            onRequestSearchChange = {},
            onMethodToggle = {},
            onStatusCategoryToggle = {},
            onTimeRangeChange = {},
            onSortOrderToggle = {},
            onShowAdvancedFiltersChange = {},
            onRequestClick = {},
        )
    }
}

@Preview
@Composable
private fun PreviewDeviceManagementScreenError() {
    MockStationTheme {
        DeviceManagementBaseScreen(
            uiState = DeviceManagementUiState.Error("Failed to load devices"),
            onTabChange = {},
            onFilterChange = {},
            onRegisterDevice = {},
            onEditName = {},
            onSaveDeviceName = { _, _ -> },
            onDeleteDevice = {},
            onConfirmDelete = {},
            onToggleEnabled = { _, _ -> },
            onTestCaseClick = {},
            onDelaySettingsClick = {},
            onSaveDelaySettings = { _, _ -> },
            onDismissDialog = {},
            onNavigateToServerTab = {},
            onRequestSearchChange = {},
            onMethodToggle = {},
            onStatusCategoryToggle = {},
            onTimeRangeChange = {},
            onSortOrderToggle = {},
            onShowAdvancedFiltersChange = {},
            onRequestClick = {},
        )
    }
}
