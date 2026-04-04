package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton as MaterialTextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun IconTextButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides MockStationTheme.ripple.forSurfaceBackground(),
    ) {
        MaterialTextButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MockStationTheme.colors.primary,
            ),
            contentPadding = PaddingValues(
                horizontal = MockStationTheme.spacing.medium,
                vertical = MockStationTheme.spacing.small,
            ),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                )
                Text(
                    text = text,
                    style = MockStationTheme.typography.bodyMedium,
                    color = MockStationTheme.colors.onSurface,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewIconTextButton() {
    MockStationTheme {
        PreviewColumn {
            IconTextButton(
                text = "Advanced Filters",
                icon = Icons.Filled.KeyboardArrowDown,
                onClick = {},
            )

            IconTextButton(
                text = "Newest First",
                icon = Icons.Filled.ArrowDownward,
                onClick = {},
            )

            IconTextButton(
                text = "Disabled Button",
                icon = Icons.Filled.KeyboardArrowDown,
                onClick = {},
                enabled = false,
            )
        }
    }
}
