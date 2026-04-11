package com.github.syunpeii.mockstation.core.designsystem.component.organism

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.badge.MethodBadge
import com.github.syunpeii.mockstation.core.designsystem.component.atom.badge.StatusBadge
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.util.BoxContainer
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.RequestHistoryFilters
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.RequestListItem
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import com.github.syunpeii.mockstation.core.designsystem.theme.bodySmallMonospace
import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@Composable
fun ApiRequestHistoryTab(
    deviceHistories: List<DeviceRequestHistory>,
    searchText: String,
    selectedMethods: Set<HttpMethod>,
    selectedStatusCategories: Set<StatusCategory>,
    selectedTimeRange: TimeRange,
    sortOrder: SortOrder,
    showAdvancedFilters: Boolean,
    selectedRequestIds: Set<String>,
    onSearchChange: (String) -> Unit,
    onMethodToggle: (HttpMethod) -> Unit,
    onStatusCategoryToggle: (StatusCategory) -> Unit,
    onTimeRangeChange: (TimeRange) -> Unit,
    onSortOrderToggle: () -> Unit,
    onShowAdvancedFiltersChange: (Boolean) -> Unit,
    onRequestClick: (String) -> Unit,
    onCloseDetailPanel: (String) -> Unit,
    getTimestampLabel: (RequestInfo) -> String,
    modifier: Modifier = Modifier,
    noHistoryLabel: String,
    enableDevicesHintLabel: String,
    deviceLabel: String,
    unnamedDeviceLabel: String,
    requestsLabels: List<String>,
    noRequestsLabel: String,
    searchPlaceholder: String,
    advancedFiltersLabel: String,
    methodsLabel: String,
    statusLabel: String,
    timeRangeLabel: String,
    newestFirstLabel: String,
    oldestFirstLabel: String,
    getMethodLabel: (HttpMethod) -> String,
    getStatusCategoryLabel: (StatusCategory) -> String,
    getTimeRangeLabel: (TimeRange) -> String,
    requestDetailTitleLabel: String,
    requestDetailMethodLabel: String,
    requestDetailPathLabel: String,
    requestDetailStatusLabel: String,
    requestDetailTimestampLabel: String,
    requestDetailDurationLabel: String,
    requestDetailRequestHeadersLabel: String,
    requestDetailResponseHeadersLabel: String,
    requestDetailRequestBodyLabel: String,
    requestDetailResponseBodyLabel: String,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        RequestHistoryFilters(
            searchText = searchText,
            selectedMethods = selectedMethods,
            selectedStatusCategories = selectedStatusCategories,
            selectedTimeRange = selectedTimeRange,
            sortOrder = sortOrder,
            showAdvancedFilters = showAdvancedFilters,
            onSearchChange = onSearchChange,
            onMethodToggle = onMethodToggle,
            onStatusCategoryToggle = onStatusCategoryToggle,
            onTimeRangeChange = onTimeRangeChange,
            onSortOrderToggle = onSortOrderToggle,
            onShowAdvancedFiltersChange = onShowAdvancedFiltersChange,
            searchPlaceholder = searchPlaceholder,
            advancedFiltersLabel = advancedFiltersLabel,
            methodsLabel = methodsLabel,
            statusLabel = statusLabel,
            timeRangeLabel = timeRangeLabel,
            newestFirstLabel = newestFirstLabel,
            oldestFirstLabel = oldestFirstLabel,
            getMethodLabel = getMethodLabel,
            getStatusCategoryLabel = getStatusCategoryLabel,
            getTimeRangeLabel = getTimeRangeLabel,
        )

        if (deviceHistories.isEmpty()) {
            ApiRequestHistoryEmptyContent(
                noHistoryLabel = noHistoryLabel,
                enableDevicesHintLabel = enableDevicesHintLabel,
                modifier = Modifier.weight(1f),
            )
        } else {
            ApiRequestHistoriesContent(
                deviceHistories = deviceHistories,
                selectedRequestIds = selectedRequestIds,
                onRequestClick = onRequestClick,
                onCloseDetailPanel = onCloseDetailPanel,
                getTimestampLabel = getTimestampLabel,
                deviceLabel = deviceLabel,
                unnamedDeviceLabel = unnamedDeviceLabel,
                requestsLabels = requestsLabels,
                noRequestsLabel = noRequestsLabel,
                requestDetailTitleLabel = requestDetailTitleLabel,
                requestDetailMethodLabel = requestDetailMethodLabel,
                requestDetailPathLabel = requestDetailPathLabel,
                requestDetailStatusLabel = requestDetailStatusLabel,
                requestDetailTimestampLabel = requestDetailTimestampLabel,
                requestDetailDurationLabel = requestDetailDurationLabel,
                requestDetailRequestHeadersLabel = requestDetailRequestHeadersLabel,
                requestDetailResponseHeadersLabel = requestDetailResponseHeadersLabel,
                requestDetailRequestBodyLabel = requestDetailRequestBodyLabel,
                requestDetailResponseBodyLabel = requestDetailResponseBodyLabel,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ApiRequestHistoryEmptyContent(
    noHistoryLabel: String,
    enableDevicesHintLabel: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MockStationTheme.spacing.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = noHistoryLabel,
            style = MockStationTheme.typography.bodyLarge,
            color = MockStationTheme.colors.onBackground,
        )
        Text(
            text = enableDevicesHintLabel,
            style = MockStationTheme.typography.bodyMedium,
            color = MockStationTheme.colors.onBackground,
            modifier = Modifier.padding(top = MockStationTheme.spacing.small),
        )
    }
}

@Composable
private fun ApiRequestHistoriesContent(
    deviceHistories: List<DeviceRequestHistory>,
    selectedRequestIds: Set<String>,
    onRequestClick: (String) -> Unit,
    onCloseDetailPanel: (String) -> Unit,
    getTimestampLabel: (RequestInfo) -> String,
    deviceLabel: String,
    unnamedDeviceLabel: String,
    requestsLabels: List<String>,
    noRequestsLabel: String,
    requestDetailTitleLabel: String,
    requestDetailMethodLabel: String,
    requestDetailPathLabel: String,
    requestDetailStatusLabel: String,
    requestDetailTimestampLabel: String,
    requestDetailDurationLabel: String,
    requestDetailRequestHeadersLabel: String,
    requestDetailResponseHeadersLabel: String,
    requestDetailRequestBodyLabel: String,
    requestDetailResponseBodyLabel: String,
    modifier: Modifier = Modifier,
) {
    BoxContainer(modifier = modifier) {
        val deviceHasDetailPanel = remember(selectedRequestIds, deviceHistories) {
            deviceHistories.map { deviceHistory ->
                deviceHistory.requests.any { it.id in selectedRequestIds }
            }
        }

        val deviceMinWidths = deviceHasDetailPanel.map { hasDetailPanel ->
            if (hasDetailPanel) 1000.dp else 500.dp
        }

        val totalRequiredWidth = deviceMinWidths.fold(0f) { acc, dp -> acc + dp.value }
        val shouldScroll = maxWidth.value < totalRequiredWidth

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(MockStationTheme.spacing.medium)
                .then(
                    if (shouldScroll) {
                        Modifier.horizontalScroll(rememberScrollState())
                    } else {
                        Modifier
                    },
                ),
            horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
        ) {
            deviceHistories.forEachIndexed { index, deviceHistory ->
                val minWidth = deviceMinWidths[index]
                val hasDetailPanel = deviceHasDetailPanel[index]

                DeviceHistoryColumn(
                    deviceId = deviceHistory.deviceId,
                    deviceName = deviceHistory.deviceName,
                    requests = deviceHistory.requests,
                    selectedRequestIds = selectedRequestIds,
                    onRequestClick = onRequestClick,
                    onCloseDetailPanel = onCloseDetailPanel,
                    getTimestampLabel = getTimestampLabel,
                    deviceLabel = deviceLabel,
                    unnamedDeviceLabel = unnamedDeviceLabel,
                    requestsLabel = requestsLabels.getOrNull(index) ?: "",
                    noRequestsLabel = noRequestsLabel,
                    requestDetailTitleLabel = requestDetailTitleLabel,
                    requestDetailMethodLabel = requestDetailMethodLabel,
                    requestDetailPathLabel = requestDetailPathLabel,
                    requestDetailStatusLabel = requestDetailStatusLabel,
                    requestDetailTimestampLabel = requestDetailTimestampLabel,
                    requestDetailDurationLabel = requestDetailDurationLabel,
                    requestDetailRequestHeadersLabel = requestDetailRequestHeadersLabel,
                    requestDetailResponseHeadersLabel = requestDetailResponseHeadersLabel,
                    requestDetailRequestBodyLabel = requestDetailRequestBodyLabel,
                    requestDetailResponseBodyLabel = requestDetailResponseBodyLabel,
                    modifier = if (shouldScroll) {
                        Modifier.width(minWidth).fillMaxHeight()
                    } else {
                        val weight = if (hasDetailPanel) 2f else 1f
                        Modifier.weight(weight).fillMaxHeight()
                    },
                )
            }
        }
    }
}

@Composable
private fun DeviceHistoryColumn(
    deviceId: String,
    deviceName: String?,
    requests: List<RequestInfo>,
    selectedRequestIds: Set<String>,
    onRequestClick: (String) -> Unit,
    onCloseDetailPanel: (String) -> Unit,
    getTimestampLabel: (RequestInfo) -> String,
    deviceLabel: String,
    unnamedDeviceLabel: String,
    requestsLabel: String,
    noRequestsLabel: String,
    requestDetailTitleLabel: String,
    requestDetailMethodLabel: String,
    requestDetailPathLabel: String,
    requestDetailStatusLabel: String,
    requestDetailTimestampLabel: String,
    requestDetailDurationLabel: String,
    requestDetailRequestHeadersLabel: String,
    requestDetailResponseHeadersLabel: String,
    requestDetailRequestBodyLabel: String,
    requestDetailResponseBodyLabel: String,
    modifier: Modifier = Modifier,
) {
    val selectedRequest = requests.find { it.id in selectedRequestIds }

    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MockStationTheme.colors.outline,
                shape = MockStationTheme.shapes.medium,
            ),
        colors = CardDefaults.cardColors(
            containerColor = MockStationTheme.colors.surface,
        ),
    ) {
        if (selectedRequest != null) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight()
                        .padding(MockStationTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                ) {
                    DeviceHistoryHeader(
                        title = deviceName ?: deviceId,
                        subTitle = requestsLabel,
                    )

                    RequestList(
                        requests = requests,
                        selectedRequestIds = selectedRequestIds,
                        onRequestClick = onRequestClick,
                        getTimestampLabel = getTimestampLabel,
                        noRequestsLabel = noRequestsLabel,
                        modifier = Modifier.weight(1f),
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                        .padding(
                            top = MockStationTheme.spacing.medium,
                            end = MockStationTheme.spacing.medium,
                            bottom = MockStationTheme.spacing.medium,
                        ),
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                ) {
                    DeviceHistoryHeader(
                        title = requestDetailTitleLabel,
                        actionButton = {
                            AppIconButton(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                onClick = { onCloseDetailPanel(selectedRequest.id) },
                            )
                        },
                    )

                    RequestDetailContent(
                        request = selectedRequest,
                        timestampValue = getTimestampLabel(selectedRequest),
                        methodLabel = requestDetailMethodLabel,
                        pathLabel = requestDetailPathLabel,
                        statusLabel = requestDetailStatusLabel,
                        timestampLabel = requestDetailTimestampLabel,
                        durationLabel = requestDetailDurationLabel,
                        requestHeadersLabel = requestDetailRequestHeadersLabel,
                        responseHeadersLabel = requestDetailResponseHeadersLabel,
                        requestBodyLabel = requestDetailRequestBodyLabel,
                        responseBodyLabel = requestDetailResponseBodyLabel,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MockStationTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                DeviceHistoryHeader(
                    title = deviceName ?: deviceId,
                    subTitle = requestsLabel,
                )

                RequestList(
                    requests = requests,
                    selectedRequestIds = selectedRequestIds,
                    onRequestClick = onRequestClick,
                    getTimestampLabel = getTimestampLabel,
                    noRequestsLabel = noRequestsLabel,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun DeviceHistoryHeader(
    title: String,
    subTitle: String? = null,
    actionButton: (@Composable () -> Unit)? = null,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MockStationTheme.typography.bodyMedium,
                color = MockStationTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (actionButton != null) {
                actionButton()
            } else {
                Box(modifier = Modifier.size(48.dp))
            }
        }

        Text(
            text = subTitle ?: "",
            style = MockStationTheme.typography.labelMedium,
            color = MockStationTheme.colors.onSurfaceVariant,
            modifier = Modifier.padding(bottom = MockStationTheme.spacing.extraSmall),
        )
        HorizontalDivider()
    }
}

@Composable
private fun RequestDetailContent(
    request: RequestInfo,
    timestampValue: String,
    methodLabel: String,
    pathLabel: String,
    statusLabel: String,
    timestampLabel: String,
    durationLabel: String,
    requestHeadersLabel: String,
    responseHeadersLabel: String,
    requestBodyLabel: String,
    responseBodyLabel: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MockStationTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
    ) {
        DetailSection(
            label = methodLabel,
            content = {
                MethodBadge(method = request.method)
            },
        )

        DetailSection(
            label = pathLabel,
            content = {
                SelectionContainer {
                    Text(
                        text = request.path,
                        style = MockStationTheme.typography.bodyMedium,
                        color = MockStationTheme.colors.onSurface,
                    )
                }
            },
        )

        DetailSection(
            label = statusLabel,
            content = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    StatusBadge(statusCode = request.statusCode)
                    Text(
                        text = request.statusText,
                        style = MockStationTheme.typography.bodyMedium,
                        color = MockStationTheme.colors.onSurface,
                    )
                }
            },
        )

        DetailSection(
            label = timestampLabel,
            content = {
                Text(
                    text = timestampValue,
                    style = MockStationTheme.typography.bodyMedium,
                    color = MockStationTheme.colors.onSurface,
                )
            },
        )

        request.durationMs?.let { duration ->
            DetailSection(
                label = durationLabel,
                content = {
                    Text(
                        text = "${duration}ms",
                        style = MockStationTheme.typography.bodyMedium,
                        color = MockStationTheme.colors.onSurface,
                    )
                },
            )
        }

        if (request.headers.isNotEmpty()) {
            DetailSection(
                label = requestHeadersLabel,
                content = {
                    SelectionContainer {
                        Column {
                            request.headers.forEach { (key, value) ->
                                Text(
                                    text = "$key: $value",
                                    style = MockStationTheme.typography.bodySmallMonospace,
                                    color = MockStationTheme.colors.onSurfaceVariant,
                                )
                            }
                        }
                    }
                },
            )
        }

        if (request.responseHeaders.isNotEmpty()) {
            DetailSection(
                label = responseHeadersLabel,
                content = {
                    SelectionContainer {
                        Column {
                            request.responseHeaders.forEach { (key, value) ->
                                Text(
                                    text = "$key: $value",
                                    style = MockStationTheme.typography.bodySmallMonospace,
                                    color = MockStationTheme.colors.onSurfaceVariant,
                                )
                            }
                        }
                    }
                },
            )
        }

        request.requestBody?.let { body ->
            DetailSection(
                label = requestBodyLabel,
                content = {
                    SelectionContainer {
                        Text(
                            text = body,
                            style = MockStationTheme.typography.bodySmallMonospace,
                            color = MockStationTheme.colors.onSurface,
                        )
                    }
                },
            )
        }

        request.responseBody?.let { body ->
            DetailSection(
                label = responseBodyLabel,
                content = {
                    SelectionContainer {
                        Text(
                            text = body,
                            style = MockStationTheme.typography.bodySmallMonospace,
                            color = MockStationTheme.colors.onSurface,
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun DetailSection(
    label: String,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
    ) {
        Text(
            text = label,
            style = MockStationTheme.typography.labelMedium,
            color = MockStationTheme.colors.onSurfaceVariant,
        )
        content()
    }
}

@Composable
private fun RequestList(
    requests: List<RequestInfo>,
    selectedRequestIds: Set<String>,
    onRequestClick: (String) -> Unit,
    getTimestampLabel: (RequestInfo) -> String,
    noRequestsLabel: String,
    modifier: Modifier = Modifier,
) {
    if (requests.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(MockStationTheme.spacing.large),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = noRequestsLabel,
                style = MockStationTheme.typography.bodySmall,
                color = MockStationTheme.colors.onSurface,
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
        ) {
            items(
                items = requests,
                key = { it.id },
            ) { request ->
                RequestListItem(
                    request = request,
                    selected = request.id in selectedRequestIds,
                    onClick = { onRequestClick(request.id) },
                    timestampLabel = getTimestampLabel(request),
                )
            }
        }
    }
}

data class DeviceRequestHistory(
    val deviceId: String,
    val deviceName: String?,
    val requests: List<RequestInfo>,
)

@Preview
@Composable
private fun PreviewApiRequestHistoryTabEmpty() {
    MockStationTheme {
        PreviewBox {
            var searchText by remember { mutableStateOf("") }
            var selectedMethods by remember { mutableStateOf(setOf<HttpMethod>()) }
            var selectedStatuses by remember { mutableStateOf(setOf<StatusCategory>()) }
            var selectedTimeRange by remember { mutableStateOf(TimeRange.ALL) }
            var sortOrder by remember { mutableStateOf(SortOrder.NEWEST_FIRST) }
            var showAdvancedFilters by remember { mutableStateOf(false) }

            ApiRequestHistoryTab(
                deviceHistories = emptyList(),
                searchText = searchText,
                selectedMethods = selectedMethods,
                selectedStatusCategories = selectedStatuses,
                selectedTimeRange = selectedTimeRange,
                sortOrder = sortOrder,
                showAdvancedFilters = showAdvancedFilters,
                selectedRequestIds = emptySet(),
                onSearchChange = { searchText = it },
                onMethodToggle = { method ->
                    selectedMethods = if (method in selectedMethods) {
                        selectedMethods - method
                    } else {
                        selectedMethods + method
                    }
                },
                onStatusCategoryToggle = { status ->
                    selectedStatuses = if (status in selectedStatuses) {
                        selectedStatuses - status
                    } else {
                        selectedStatuses + status
                    }
                },
                onTimeRangeChange = { selectedTimeRange = it },
                onSortOrderToggle = {
                    sortOrder = if (sortOrder == SortOrder.NEWEST_FIRST) SortOrder.OLDEST_FIRST else SortOrder.NEWEST_FIRST
                },
                onShowAdvancedFiltersChange = { showAdvancedFilters = it },
                onRequestClick = {},
                onCloseDetailPanel = {},
                getTimestampLabel = { "Just now" },
                noHistoryLabel = "No history available",
                enableDevicesHintLabel = "Enable devices to see history",
                deviceLabel = "Device",
                unnamedDeviceLabel = "Unnamed Device",
                requestsLabels = emptyList(),
                noRequestsLabel = "No requests",
                searchPlaceholder = "Search by path...",
                advancedFiltersLabel = "Advanced Filters",
                methodsLabel = "Methods",
                statusLabel = "Status",
                timeRangeLabel = "Time Range",
                newestFirstLabel = "Newest First",
                oldestFirstLabel = "Oldest First",
                getMethodLabel = { it.toDisplayString() },
                getStatusCategoryLabel = {
                    when (it) {
                        StatusCategory.SUCCESS_2XX -> "2xx"
                        StatusCategory.REDIRECT_3XX -> "3xx"
                        StatusCategory.CLIENT_ERROR_4XX -> "4xx"
                        StatusCategory.SERVER_ERROR_5XX -> "5xx"
                        StatusCategory.OTHER -> "Other"
                    }
                },
                getTimeRangeLabel = {
                    when (it) {
                        TimeRange.LAST_HOUR -> "Last 1h"
                        TimeRange.LAST_24_HOURS -> "Last 24h"
                        TimeRange.LAST_7_DAYS -> "Last 7d"
                        TimeRange.ALL -> "All"
                    }
                },
                requestDetailTitleLabel = "Request Details",
                requestDetailMethodLabel = "Method",
                requestDetailPathLabel = "Path",
                requestDetailStatusLabel = "Status",
                requestDetailTimestampLabel = "Timestamp",
                requestDetailDurationLabel = "Duration",
                requestDetailRequestHeadersLabel = "Request Headers",
                requestDetailResponseHeadersLabel = "Response Headers",
                requestDetailRequestBodyLabel = "Request Body",
                requestDetailResponseBodyLabel = "Response Body",
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun PreviewApiRequestHistoryTabWithHistory() {
    MockStationTheme {
        PreviewBox {
            var searchText by remember { mutableStateOf("") }
            var selectedMethods by remember { mutableStateOf(setOf<HttpMethod>()) }
            var selectedStatuses by remember { mutableStateOf(setOf<StatusCategory>()) }
            var selectedTimeRange by remember { mutableStateOf(TimeRange.ALL) }
            var sortOrder by remember { mutableStateOf(SortOrder.NEWEST_FIRST) }
            var showAdvancedFilters by remember { mutableStateOf(false) }
            var selectedRequestIds by remember { mutableStateOf<Set<String>>(emptySet()) }

            ApiRequestHistoryTab(
                deviceHistories = listOf(
                    DeviceRequestHistory(
                        deviceId = "device-001",
                        deviceName = "Test Device 1",
                        requests = listOf(
                            RequestInfo(
                                id = "req-001",
                                method = HttpMethod.GET,
                                path = "/api/user",
                                statusCode = 200,
                                statusText = "OK",
                                timestamp = Clock.System.now().minus(5.minutes),
                                deviceId = "device-001",
                                requestBody = null,
                                responseBody = """{"id":1,"name":"John"}""",
                                headers = emptyMap(),
                                responseHeaders = mapOf("Content-Type" to "application/json"),
                                durationMs = 45,
                            ),
                            RequestInfo(
                                id = "req-002",
                                method = HttpMethod.POST,
                                path = "/api/device",
                                statusCode = 201,
                                statusText = "Created",
                                timestamp = Clock.System.now().minus(10.minutes),
                                deviceId = "device-001",
                                requestBody = """{"name":"Device"}""",
                                responseBody = """{"id":2,"name":"Device"}""",
                                headers = mapOf("Content-Type" to "application/json"),
                                responseHeaders = mapOf("Content-Type" to "application/json"),
                                durationMs = 123,
                            ),
                        ),
                    ),
                    DeviceRequestHistory(
                        deviceId = "device-002",
                        deviceName = null,
                        requests = listOf(
                            RequestInfo(
                                id = "req-003",
                                method = HttpMethod.PUT,
                                path = "/api/settings",
                                statusCode = 500,
                                statusText = "Internal Server Error",
                                timestamp = Clock.System.now().minus(1.hours),
                                deviceId = "device-002",
                                requestBody = """{"setting":"value"}""",
                                responseBody = """{"error":"Internal Server Error"}""",
                                headers = mapOf("Content-Type" to "application/json"),
                                responseHeaders = mapOf("Content-Type" to "application/json"),
                                durationMs = 234,
                            ),
                        ),
                    ),
                ),
                searchText = searchText,
                selectedMethods = selectedMethods,
                selectedStatusCategories = selectedStatuses,
                selectedTimeRange = selectedTimeRange,
                sortOrder = sortOrder,
                showAdvancedFilters = showAdvancedFilters,
                selectedRequestIds = selectedRequestIds,
                onSearchChange = { searchText = it },
                onMethodToggle = { method ->
                    selectedMethods = if (method in selectedMethods) {
                        selectedMethods - method
                    } else {
                        selectedMethods + method
                    }
                },
                onStatusCategoryToggle = { status ->
                    selectedStatuses = if (status in selectedStatuses) {
                        selectedStatuses - status
                    } else {
                        selectedStatuses + status
                    }
                },
                onTimeRangeChange = { selectedTimeRange = it },
                onSortOrderToggle = {
                    sortOrder = if (sortOrder == SortOrder.NEWEST_FIRST) {
                        SortOrder.OLDEST_FIRST
                    } else {
                        SortOrder.NEWEST_FIRST
                    }
                },
                onShowAdvancedFiltersChange = { showAdvancedFilters = it },
                onRequestClick = { id ->
                    selectedRequestIds = if (id in selectedRequestIds) {
                        selectedRequestIds - id
                    } else {
                        selectedRequestIds + id
                    }
                },
                onCloseDetailPanel = { id ->
                    selectedRequestIds = selectedRequestIds - id
                },
                getTimestampLabel = { "Just now" },
                noHistoryLabel = "No history available",
                enableDevicesHintLabel = "Enable devices to see history",
                deviceLabel = "Device",
                unnamedDeviceLabel = "Unnamed Device",
                requestsLabels = listOf("2 requests", "1 request"),
                noRequestsLabel = "No requests",
                searchPlaceholder = "Search by path...",
                advancedFiltersLabel = "Advanced Filters",
                methodsLabel = "Methods",
                statusLabel = "Status",
                timeRangeLabel = "Time Range",
                newestFirstLabel = "Newest First",
                oldestFirstLabel = "Oldest First",
                getMethodLabel = { it.toDisplayString() },
                getStatusCategoryLabel = {
                    when (it) {
                        StatusCategory.SUCCESS_2XX -> "2xx"
                        StatusCategory.REDIRECT_3XX -> "3xx"
                        StatusCategory.CLIENT_ERROR_4XX -> "4xx"
                        StatusCategory.SERVER_ERROR_5XX -> "5xx"
                        StatusCategory.OTHER -> "Other"
                    }
                },
                getTimeRangeLabel = {
                    when (it) {
                        TimeRange.LAST_HOUR -> "Last 1h"
                        TimeRange.LAST_24_HOURS -> "Last 24h"
                        TimeRange.LAST_7_DAYS -> "Last 7d"
                        TimeRange.ALL -> "All"
                    }
                },
                requestDetailTitleLabel = "Request Details",
                requestDetailMethodLabel = "Method",
                requestDetailPathLabel = "Path",
                requestDetailStatusLabel = "Status",
                requestDetailTimestampLabel = "Timestamp",
                requestDetailDurationLabel = "Duration",
                requestDetailRequestHeadersLabel = "Request Headers",
                requestDetailResponseHeadersLabel = "Response Headers",
                requestDetailRequestBodyLabel = "Request Body",
                requestDetailResponseBodyLabel = "Response Body",
            )
        }
    }
}
