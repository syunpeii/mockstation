package com.github.syunpeii.mockstation.core.designsystem.component.atom.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton as Material3IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewRow
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
    CompositionLocalProvider(
        LocalRippleConfiguration provides MockStationTheme.ripple.forPrimaryBackground(),
    ) {
        Material3IconButton(
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
}

@Preview
@Composable
private fun PreviewAppIconButton() {
    MockStationTheme {
        PreviewRow {
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

@Preview
@Composable
private fun PreviewAppIconButtonActions() {
    MockStationTheme {
        PreviewRow {
            AppIconButton(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                onClick = {},
            )
            AppIconButton(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                onClick = {},
                tint = MockStationTheme.colors.error,
            )
        }
    }
}
