package com.github.syunpeii.mockstation.core.designsystem.component.atom.text

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun LabelSmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MockStationTheme.colors.onSurfaceVariant,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MockStationTheme.typography.labelSmall,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Preview
@Composable
private fun PreviewLabelSmallText() {
    MockStationTheme {
        PreviewBox {
            LabelSmallText(text = "Label Small Text")
        }
    }
}

@Preview
@Composable
private fun PreviewLabelSmallTextWithColor() {
    MockStationTheme {
        PreviewBox {
            LabelSmallText(
                text = "Primary Label Small",
                color = MockStationTheme.colors.primary,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLabelSmallTextTruncated() {
    MockStationTheme {
        PreviewBox {
            LabelSmallText(
                text = "This is a very long label that should be truncated when maxLines is set to 1",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
