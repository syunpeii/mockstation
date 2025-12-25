package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange

@Composable
fun RequestHistoryFilters(
    searchText: String,
    selectedMethods: Set<HttpMethod>,
    selectedStatusCategories: Set<StatusCategory>,
    selectedTimeRange: TimeRange,
    sortOrder: SortOrder,
    showAdvancedFilters: Boolean,
    onSearchChange: (String) -> Unit,
    onMethodToggle: (HttpMethod) -> Unit,
    onStatusCategoryToggle: (StatusCategory) -> Unit,
    onTimeRangeChange: (TimeRange) -> Unit,
    onSortOrderToggle: () -> Unit,
    onShowAdvancedFiltersChange: (Boolean) -> Unit,
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MockStationTheme.spacing.medium),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SearchBar(
                value = searchText,
                onValueChange = onSearchChange,
                placeholder = searchPlaceholder,
                modifier = Modifier.weight(1f),
            )

            IconTextButton(
                text = advancedFiltersLabel,
                icon = if (showAdvancedFilters) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                onClick = { onShowAdvancedFiltersChange(!showAdvancedFilters) },
            )
        }

        AnimatedVisibility(
            visible = showAdvancedFilters,
        ) {
            Column(
                modifier = Modifier.padding(top = MockStationTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
            ) {
                FilterChipGroup(
                    label = methodsLabel,
                    items = HttpMethod.entries,
                    selectedItems = selectedMethods,
                    onItemToggle = onMethodToggle,
                    getItemLabel = getMethodLabel,
                )

                FilterChipGroup(
                    label = statusLabel,
                    items = StatusCategory.entries,
                    selectedItems = selectedStatusCategories,
                    onItemToggle = onStatusCategoryToggle,
                    getItemLabel = getStatusCategoryLabel,
                )

                FilterChipGroup(
                    label = timeRangeLabel,
                    items = TimeRange.entries,
                    selectedItem = selectedTimeRange,
                    onItemSelected = onTimeRangeChange,
                    getItemLabel = getTimeRangeLabel,
                )

                IconTextButton(
                    text = if (sortOrder == SortOrder.NEWEST_FIRST) newestFirstLabel else oldestFirstLabel,
                    icon = if (sortOrder == SortOrder.NEWEST_FIRST) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
                    onClick = onSortOrderToggle,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRequestHistoryFilters() {
    MockStationTheme {
        PreviewBox {
            var searchText by remember { mutableStateOf("") }
            var selectedMethods by remember { mutableStateOf(setOf<HttpMethod>()) }
            var selectedStatuses by remember { mutableStateOf(setOf<StatusCategory>()) }
            var selectedTimeRange by remember { mutableStateOf(TimeRange.ALL) }
            var sortOrder by remember { mutableStateOf(SortOrder.NEWEST_FIRST) }
            var showAdvancedFilters by remember { mutableStateOf(false) }

            RequestHistoryFilters(
                searchText = searchText,
                selectedMethods = selectedMethods,
                selectedStatusCategories = selectedStatuses,
                selectedTimeRange = selectedTimeRange,
                sortOrder = sortOrder,
                showAdvancedFilters = showAdvancedFilters,
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
            )
        }
    }
}
