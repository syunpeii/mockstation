package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SettingControlRow(
    label: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MockStationTheme.spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(end = MockStationTheme.spacing.medium),
        ) {
            Text(
                text = label,
                style = MockStationTheme.typography.bodyLarge,
                color = MockStationTheme.colors.onSurface,
            )
            if (description != null) {
                Text(
                    text = description,
                    style = MockStationTheme.typography.bodyMedium,
                    color = MockStationTheme.colors.onSurfaceVariant,
                    modifier = Modifier.padding(top = MockStationTheme.spacing.extraSmall),
                )
            }
        }
        content()
    }
}

@Preview
@Composable
private fun PreviewSettingControlRow() {
    MockStationTheme {
        PreviewColumn {
            var checked by remember { mutableStateOf(false) }

            SettingControlRow(
                label = "Enable Notifications",
                description = "Receive alerts when new updates are available",
            ) {
                Switch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                )
            }

            SettingControlRow(
                label = "Simple Setting",
            ) {
                Switch(
                    checked = true,
                    onCheckedChange = {},
                )
            }
        }
    }
}
