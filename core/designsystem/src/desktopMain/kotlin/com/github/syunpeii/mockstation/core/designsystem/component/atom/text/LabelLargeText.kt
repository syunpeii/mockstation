package com.github.syunpeii.mockstation.core.designsystem.component.atom.text

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun LabelLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MockStationTheme.colors.onSurface,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MockStationTheme.typography.labelLarge,
        color = color,
    )
}

@Preview
@Composable
private fun PreviewLabelLargeText() {
    MockStationTheme {
        PreviewBox {
            LabelLargeText(text = "Label Large Text")
        }
    }
}

@Preview
@Composable
private fun PreviewLabelLargeTextWithColor() {
    MockStationTheme {
        PreviewBox {
            LabelLargeText(
                text = "Primary Label",
                color = MockStationTheme.colors.primary,
            )
        }
    }
}
