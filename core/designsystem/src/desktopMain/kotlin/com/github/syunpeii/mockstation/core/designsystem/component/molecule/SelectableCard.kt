package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SelectableCard(
    name: String,
    url: String,
    description: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MockStationTheme.colors.surface,
        ),
        border = if (isSelected) {
            BorderStroke(2.dp, MockStationTheme.colors.primary)
        } else {
            BorderStroke(1.dp, MockStationTheme.colors.outlineVariant)
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MockStationTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = onSelect,
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                ) {
                    Text(
                        text = name,
                        style = MockStationTheme.typography.titleMedium,
                        color = MockStationTheme.colors.onSurface,
                    )
                    Text(
                        text = url,
                        style = MockStationTheme.typography.bodyMedium,
                        color = MockStationTheme.colors.onSurfaceVariant,
                    )
                    if (description.isNotEmpty()) {
                        Text(
                            text = description,
                            style = MockStationTheme.typography.bodySmall,
                            color = MockStationTheme.colors.onSurfaceVariant,
                        )
                    }
                }
            }

            Row {
                AppIconButton(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit connection",
                    onClick = onEdit,
                    tint = MockStationTheme.colors.onSurfaceVariant,
                )
                AppIconButton(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete connection",
                    onClick = onDelete,
                    tint = MockStationTheme.colors.error,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSelectableCard() {
    MockStationTheme {
        PreviewColumn {
            SelectableCard(
                name = "Local Dev",
                url = "http://localhost:8080",
                description = "Development server",
                isSelected = true,
                onSelect = {},
                onEdit = {},
                onDelete = {},
            )
            SelectableCard(
                name = "Staging",
                url = "https://staging.example.com",
                description = "",
                isSelected = false,
                onSelect = {},
                onEdit = {},
                onDelete = {},
            )
        }
    }
}
