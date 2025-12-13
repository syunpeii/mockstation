package com.github.syunpeii.mockstation.core.designsystem.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun PreviewColumn(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MockStationTheme.colors.background,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        content = content,
    )
}

@Composable
fun PreviewRow(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MockStationTheme.colors.background,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = content,
    )
}

@Composable
fun PreviewBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MockStationTheme.colors.background,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        content = content,
    )
}
