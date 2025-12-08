package com.github.syunpeii.mockstation.core.designsystem.component.text

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun NavigationItemText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MockStationTheme.colors.onSurfaceVariant,
    style: TextStyle = MockStationTheme.typography.labelMedium,
    textAlign: TextAlign? = TextAlign.Center,
) {
    Text(
        text = text,
        modifier = modifier.widthIn(max = 56.dp),
        color = color,
        style = style,
        textAlign = textAlign,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Composable
private fun PreviewNavigationItemText() {
    MockStationTheme {
        PreviewColumn {
            NavigationItemText(text = "Home")
            NavigationItemText(text = "Settings")
            NavigationItemText(text = "Very Long Navigation Item Text")
        }
    }
}
