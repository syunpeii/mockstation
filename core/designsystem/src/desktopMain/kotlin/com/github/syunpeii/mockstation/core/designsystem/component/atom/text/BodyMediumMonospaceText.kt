package com.github.syunpeii.mockstation.core.designsystem.component.atom.text

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun BodyMediumMonospaceText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MockStationTheme.colors.onSurface,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MockStationTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily.Monospace,
        ),
        color = color,
    )
}

@Preview
@Composable
private fun PreviewBodyMediumMonospaceText() {
    MockStationTheme {
        PreviewBox {
            BodyMediumMonospaceText(text = "/Users/username/projects/mockstation")
        }
    }
}
