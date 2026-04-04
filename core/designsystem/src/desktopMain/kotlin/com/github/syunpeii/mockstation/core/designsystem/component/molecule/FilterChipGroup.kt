package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.SelectionChip
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun <T> FilterChipGroup(
    label: String,
    items: List<T>,
    selectedItems: Set<T>,
    onItemToggle: (T) -> Unit,
    getItemLabel: (T) -> String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
    ) {
        Text(
            text = label,
            style = MockStationTheme.typography.labelMedium,
            color = MockStationTheme.colors.onSurfaceVariant,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.none),
        ) {
            items.forEach { item ->
                SelectionChip(
                    label = getItemLabel(item),
                    selected = item in selectedItems,
                    onClick = { onItemToggle(item) },
                )
            }
        }
    }
}

@Composable
fun <T> FilterChipGroup(
    label: String,
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    getItemLabel: (T) -> String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
    ) {
        Text(
            text = label,
            style = MockStationTheme.typography.labelMedium,
            color = MockStationTheme.colors.onSurfaceVariant,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.none),
        ) {
            items.forEach { item ->
                SelectionChip(
                    label = getItemLabel(item),
                    selected = item == selectedItem,
                    onClick = { onItemSelected(item) },
                )
            }
        }
    }
}

private enum class FilterChipPreviewOption {
    OPTION_A,
    OPTION_B,
    OPTION_C,
    OPTION_D,
}

@Preview
@Composable
private fun PreviewFilterChipGroupMultiSelect() {
    MockStationTheme {
        PreviewColumn {
            var selectedItems by remember { mutableStateOf(setOf<FilterChipPreviewOption>()) }

            FilterChipGroup(
                label = "Multi-Select Options",
                items = FilterChipPreviewOption.entries,
                selectedItems = selectedItems,
                onItemToggle = { option ->
                    selectedItems = if (option in selectedItems) {
                        selectedItems - option
                    } else {
                        selectedItems + option
                    }
                },
                getItemLabel = { it.name },
            )

            var selectedMethods by remember { mutableStateOf(setOf(FilterChipPreviewOption.OPTION_A, FilterChipPreviewOption.OPTION_B)) }
            FilterChipGroup(
                label = "Methods",
                items = FilterChipPreviewOption.entries,
                selectedItems = selectedMethods,
                onItemToggle = { option ->
                    selectedMethods = if (option in selectedMethods) {
                        selectedMethods - option
                    } else {
                        selectedMethods + option
                    }
                },
                getItemLabel = { it.name.lowercase().replaceFirstChar { char -> char.uppercase() } },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFilterChipGroupSingleSelect() {
    MockStationTheme {
        PreviewColumn {
            var selectedItem by remember { mutableStateOf(FilterChipPreviewOption.OPTION_A) }

            FilterChipGroup(
                label = "Single-Select Options",
                items = FilterChipPreviewOption.entries,
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it },
                getItemLabel = { it.name },
            )

            var selectedTimeRange by remember { mutableStateOf(FilterChipPreviewOption.OPTION_C) }
            FilterChipGroup(
                label = "Time Range",
                items = FilterChipPreviewOption.entries,
                selectedItem = selectedTimeRange,
                onItemSelected = { selectedTimeRange = it },
                getItemLabel = { it.name.lowercase().replaceFirstChar { char -> char.uppercase() } },
            )
        }
    }
}
