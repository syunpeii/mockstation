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
fun BodySmallMonospaceText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MockStationTheme.colors.onSurface,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MockStationTheme.typography.bodySmall.copy(
            fontFamily = FontFamily.Monospace,
        ),
        color = color,
    )
}

@Preview
@Composable
private fun PreviewBodySmallMonospaceText() {
    MockStationTheme {
        PreviewBox {
            BodySmallMonospaceText(text = "Body Small Monospace Text\nLine 2\nLine 3")
        }
    }
}
