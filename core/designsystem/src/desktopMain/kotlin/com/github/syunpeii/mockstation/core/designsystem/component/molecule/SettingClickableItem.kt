package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SettingClickableItem(
    label: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = MockStationTheme.spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            style = MockStationTheme.typography.bodyLarge,
            color = MockStationTheme.colors.onSurface,
            text = label,
            modifier = Modifier.weight(1f),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
        ) {
            Text(
                style = MockStationTheme.typography.bodyMedium,
                text = value,
                color = MockStationTheme.colors.onSurfaceVariant,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MockStationTheme.colors.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSettingClickableItem() {
    MockStationTheme {
        PreviewColumn {
            SettingClickableItem(
                label = "Language",
                value = "English",
                onClick = {},
            )

            SettingClickableItem(
                label = "Version",
                value = "1.0.0",
                onClick = {},
            )

            SettingClickableItem(
                label = "Test Case Directory",
                value = "Not set",
                onClick = {},
            )
        }
    }
}
