package com.github.syunpeii.mockstation.core.designsystem.component.atom.text

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
) {
    Text(
        text = text,
        modifier = modifier.widthIn(max = 56.dp),
        color = color,
        style = MockStationTheme.typography.labelMedium,
        textAlign = TextAlign.Center,
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
