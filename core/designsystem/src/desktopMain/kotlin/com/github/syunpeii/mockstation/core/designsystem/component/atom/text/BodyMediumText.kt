package com.github.syunpeii.mockstation.core.designsystem.component.atom.text

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun BodyMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MockStationTheme.colors.onSurface,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MockStationTheme.typography.bodyMedium,
        color = color,
    )
}

@Preview
@Composable
private fun PreviewBodyMediumText() {
    MockStationTheme {
        PreviewBox {
            BodyMediumText(text = "Body Medium Text")
        }
    }
}

@Preview
@Composable
private fun PreviewBodyMediumTextWithColor() {
    MockStationTheme {
        PreviewBox {
            BodyMediumText(
                text = "Colored Text",
                color = MockStationTheme.colors.primary,
            )
        }
    }
}
