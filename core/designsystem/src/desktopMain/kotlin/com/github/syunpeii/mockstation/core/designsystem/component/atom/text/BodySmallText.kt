package com.github.syunpeii.mockstation.core.designsystem.component.atom.text

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun BodySmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MockStationTheme.colors.onSurfaceVariant,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MockStationTheme.typography.bodySmall,
        color = color,
    )
}

@Preview
@Composable
private fun PreviewBodySmallText() {
    MockStationTheme {
        PreviewBox {
            BodySmallText(text = "Body Small Text")
        }
    }
}
