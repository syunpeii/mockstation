package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun InfoDisplayRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = MockStationTheme.colors.onSurfaceVariant,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MockStationTheme.typography.bodyLarge,
            color = MockStationTheme.colors.onSurface,
        )
        Text(
            text = value,
            style = MockStationTheme.typography.bodyMedium,
            color = valueColor,
        )
    }
}

@Preview
@Composable
private fun PreviewInfoDisplayRow() {
    MockStationTheme {
        PreviewColumn {
            InfoDisplayRow(
                label = "Version",
                value = "1.0.0",
            )
        }
    }
}
