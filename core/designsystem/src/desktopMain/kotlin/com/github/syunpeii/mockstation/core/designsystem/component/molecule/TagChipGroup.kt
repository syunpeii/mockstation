package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.TextButton
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun TagChipGroup(
    tags: List<String>,
    onTagRemove: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    onClearAll: () -> Unit = {},
    clearAllLabel: String = "Clear all",
) {
    Column(
        modifier = modifier,
    ) {
        if (label.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = label,
                    style = MockStationTheme.typography.labelMedium,
                    color = MockStationTheme.colors.onSurfaceVariant,
                )

                TextButton(
                    text = clearAllLabel,
                    onClick = onClearAll,
                )
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
        ) {
            tags.forEach { tag ->
                RemovableChip(
                    label = tag,
                    onRemove = { onTagRemove(tag) },
                    removeIconContentDescription = "Remove tag",
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTagChipGroup() {
    MockStationTheme {
        PreviewColumn {
            TagChipGroup(
                tags = listOf("login", "authentication", "api"),
                onTagRemove = {},
                label = "Search Tags",
                onClearAll = {},
                clearAllLabel = "Clear all",
            )
            TagChipGroup(
                tags = emptyList(),
                onTagRemove = {},
                label = "Search Tags",
                onClearAll = {},
                clearAllLabel = "Clear all",
            )
        }
    }
}
