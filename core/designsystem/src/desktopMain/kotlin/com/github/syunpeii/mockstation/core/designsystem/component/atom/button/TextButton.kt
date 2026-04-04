package com.github.syunpeii.mockstation.core.designsystem.component.atom.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton as MaterialTextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun TextButton(
    text: String,
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
            Text(
                text = text,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTextButton() {
    MockStationTheme {
        PreviewColumn {
            TextButton(
                text = "Text Button",
                onClick = {},
            )

            TextButton(
                text = "Disabled Button",
                onClick = {},
                enabled = false,
            )

            TextButton(
                text = "Cancel",
                onClick = {},
            )
        }
    }
}
