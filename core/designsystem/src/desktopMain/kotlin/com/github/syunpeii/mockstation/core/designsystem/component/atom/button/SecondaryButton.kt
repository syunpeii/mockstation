package com.github.syunpeii.mockstation.core.designsystem.component.atom.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides MockStationTheme.ripple.forSecondaryBackground(),
    ) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = MockStationTheme.colors.secondary,
                contentColor = MockStationTheme.colors.onSecondary,
            ),
            contentPadding = PaddingValues(
                horizontal = MockStationTheme.spacing.medium,
                vertical = MockStationTheme.spacing.small,
            ),
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
private fun PreviewSecondaryButton() {
    MockStationTheme {
        PreviewColumn {
            SecondaryButton(
                text = "Secondary Button",
                onClick = {},
            )

            SecondaryButton(
                text = "Disabled Button",
                onClick = {},
                enabled = false,
            )

            SecondaryButton(
                text = "Very Long Button Text Example",
                onClick = {},
            )
        }
    }
}
