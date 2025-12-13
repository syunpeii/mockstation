package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.SectionItemTitleText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SettingSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (showDivider) {
            HorizontalDivider(
                color = MockStationTheme.colors.outlineVariant,
                modifier = Modifier.padding(bottom = MockStationTheme.spacing.small),
            )
        }
        SectionItemTitleText(
            text = title,
            color = MockStationTheme.colors.onSurfaceVariant,
            modifier = Modifier.padding(vertical = MockStationTheme.spacing.small),
        )
    }
}

@Preview
@Composable
private fun PreviewSettingSectionHeader() {
    MockStationTheme {
        PreviewColumn {
            SettingSectionHeader(
                title = "Section Title",
                showDivider = true,
            )

            SettingSectionHeader(
                title = "Section Without Divider",
                showDivider = false,
            )
        }
    }
}
