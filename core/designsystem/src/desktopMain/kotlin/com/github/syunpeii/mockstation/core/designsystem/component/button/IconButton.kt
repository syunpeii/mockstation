package com.github.syunpeii.mockstation.core.designsystem.component.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun AppIconButton(
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = MockStationTheme.colors.onSurfaceVariant,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
        )
    }
}

@Preview
@Composable
private fun PreviewAppIconButton() {
    MockStationTheme {
        PreviewColumn {
            AppIconButton(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                onClick = {},
            )
            AppIconButton(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add (Disabled)",
                onClick = {},
                enabled = false,
            )
            AppIconButton(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings",
                onClick = {},
            )
            AppIconButton(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings with custom tint",
                onClick = {},
                tint = MockStationTheme.colors.primary,
            )
        }
    }
}
