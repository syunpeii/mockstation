package com.github.syunpeii.mockstation.core.designsystem.component.atom.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides MockStationTheme.ripple.forSurfaceBackground(),
    ) {
        FilterChip(
            selected = selected,
            onClick = onClick,
            label = {
                Text(
                    text = label,
                    style = MockStationTheme.typography.bodyMedium,
                )
            },
            modifier = modifier,
            shape = FilterChipDefaults.shape,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = MockStationTheme.colors.surface,
                labelColor = MockStationTheme.colors.onSurfaceVariant,
                selectedContainerColor = MockStationTheme.colors.secondaryContainer,
                selectedLabelColor = MockStationTheme.colors.onSecondaryContainer,
            ),
            border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = selected,
                borderColor = MockStationTheme.colors.outline,
                selectedBorderColor = MockStationTheme.colors.primary,
                disabledBorderColor = MockStationTheme.colors.outline.copy(alpha = 0.38f),
                disabledSelectedBorderColor = MockStationTheme.colors.onSurface.copy(alpha = 0.12f),
                borderWidth = 1.dp,
                selectedBorderWidth = 1.dp,
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewSelectionChip() {
    MockStationTheme {
        PreviewBox {
            Row(
                modifier = Modifier.padding(MockStationTheme.spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                SelectionChip(
                    label = "Selected",
                    selected = true,
                    onClick = {},
                )
                SelectionChip(
                    label = "Unselected",
                    selected = false,
                    onClick = {},
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSelectionChipInteractive() {
    MockStationTheme {
        PreviewBox {
            var selected by remember { mutableStateOf(false) }
            SelectionChip(
                label = "Click me",
                selected = selected,
                onClick = { selected = !selected },
            )
        }
    }
}
