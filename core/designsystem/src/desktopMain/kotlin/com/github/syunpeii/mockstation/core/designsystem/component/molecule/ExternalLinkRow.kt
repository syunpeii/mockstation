package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyLargeText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodySmallText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun ExternalLinkRow(
    label: String,
    onLinkClick: () -> Unit,
    modifier: Modifier = Modifier,
    value: String = "",
    titleColor: Color = MockStationTheme.colors.onBackground,
    valueColor: Color = MockStationTheme.colors.onBackground,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            BodyLargeText(
                text = label,
                color = titleColor,
            )
            if (value.isNotBlank()) {
                BodySmallText(
                    text = value,
                    modifier = Modifier.padding(top = MockStationTheme.spacing.extraSmall),
                    color = valueColor,
                )
            }
        }
        AppIconButton(
            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
            contentDescription = "Open link",
            onClick = onLinkClick,
            tint = if (value.isNotBlank()) MockStationTheme.colors.primary else MockStationTheme.colors.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
private fun PreviewExternalLinkRow() {
    MockStationTheme {
        PreviewColumn {
            ExternalLinkRow(
                label = "Repository",
                value = "https://github.com/syunpeii/mockstation",
                onLinkClick = {},
            )
            ExternalLinkRow(
                label = "Website",
                onLinkClick = {},
            )
        }
    }
}
