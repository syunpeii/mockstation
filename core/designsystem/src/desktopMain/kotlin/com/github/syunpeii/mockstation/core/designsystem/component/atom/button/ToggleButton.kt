package com.github.syunpeii.mockstation.core.designsystem.component.atom.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewRow
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun ToggleButton(
    selected: Boolean,
    onToggle: () -> Unit,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides MockStationTheme.ripple.forPrimaryBackground(),
    ) {
        IconButton(
            onClick = onToggle,
            modifier = modifier,
        ) {
            Icon(
                imageVector = if (selected) selectedIcon else unselectedIcon,
                contentDescription = contentDescription,
                tint = if (selected) {
                    MockStationTheme.colors.primary
                } else {
                    MockStationTheme.colors.onSurfaceVariant
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewToggleButton() {
    MockStationTheme {
        PreviewRow {
            ToggleButton(
                selected = false,
                onToggle = {},
                selectedIcon = Icons.Filled.ArrowUpward,
                unselectedIcon = Icons.Filled.ArrowDownward,
                contentDescription = "Toggle sort order",
            )
            ToggleButton(
                selected = true,
                onToggle = {},
                selectedIcon = Icons.Filled.ArrowUpward,
                unselectedIcon = Icons.Filled.ArrowDownward,
                contentDescription = "Toggle sort order",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewToggleButtonInteractive() {
    MockStationTheme {
        PreviewRow {
            var selected by remember { mutableStateOf(false) }
            ToggleButton(
                selected = selected,
                onToggle = { selected = !selected },
                selectedIcon = Icons.Filled.ArrowUpward,
                unselectedIcon = Icons.Filled.ArrowDownward,
                contentDescription = "Toggle sort order",
            )
        }
    }
}
