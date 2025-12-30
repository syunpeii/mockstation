package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewRow
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun RemovableChip(
    label: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    removeIconContentDescription: String = "Remove",
) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides MockStationTheme.ripple.forSurfaceBackground(),
    ) {
        FilterChip(
            selected = true,
            onClick = { },
            label = {
                Text(
                    text = label,
                    style = MockStationTheme.typography.bodyMedium,
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(18.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = removeIconContentDescription,
                        tint = MockStationTheme.colors.onSecondaryContainer,
                        modifier = Modifier.size(16.dp),
                    )
                }
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
                selected = true,
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
private fun PreviewRemovableChip() {
    MockStationTheme {
        PreviewRow {
            RemovableChip(
                label = "login",
                onRemove = {},
            )
            RemovableChip(
                label = "authentication",
                onRemove = {},
            )
            RemovableChip(
                label = "test",
                onRemove = {},
            )
        }
    }
}
