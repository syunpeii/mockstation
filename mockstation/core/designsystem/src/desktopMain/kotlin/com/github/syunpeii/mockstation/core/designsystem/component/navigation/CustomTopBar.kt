package com.github.syunpeii.mockstation.core.designsystem.component.navigation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun CustomTopBar(
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MockStationTheme.colors.surfaceVariant,
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .padding(horizontal = MockStationTheme.spacing.medium)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }
    }
}

@Preview
@Composable
private fun PreviewCustomTopBar() {
    MockStationTheme {
        PreviewBox {
            CustomTopBar(
                actions = {
                    AppIconButton(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        onClick = {},
                    )
                    AppIconButton(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More",
                        onClick = {},
                    )
                },
            )
        }
    }
}
