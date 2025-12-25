package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodySmallText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.LabelText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun DelaySettingsCard(
    delayType: String,
    delayValue: String,
    targetFiles: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    titleLabel: String,
    typeLabel: String,
    delayLabel: String,
    targetLabel: String,
    enabledLabel: String,
    disabledLabel: String,
    editSettingsContentDescription: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isEnabled) {
                    MockStationTheme.colors.primary.copy(alpha = 0.3f)
                } else {
                    MockStationTheme.colors.outlineVariant
                },
                shape = MockStationTheme.shapes.medium,
            )
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MockStationTheme.colors.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MockStationTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
            ) {
                LabelText(text = titleLabel)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BodyMediumText(text = typeLabel)
                    BodySmallText(
                        text = delayType,
                        color = if (isEnabled) {
                            MockStationTheme.colors.primary
                        } else {
                            MockStationTheme.colors.onSurfaceVariant
                        },
                    )
                }

                if (delayType != "OFF") {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BodyMediumText(text = delayLabel)
                        BodySmallText(
                            text = delayValue,
                            color = MockStationTheme.colors.onSurface,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BodyMediumText(text = targetLabel)
                        BodySmallText(
                            text = targetFiles,
                            color = MockStationTheme.colors.onSurfaceVariant,
                        )
                    }
                }

                BodySmallText(
                    text = if (isEnabled) enabledLabel else disabledLabel,
                    color = if (isEnabled) {
                        MockStationTheme.colors.primary
                    } else {
                        MockStationTheme.colors.onSurfaceVariant
                    },
                )
            }

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = editSettingsContentDescription,
                tint = MockStationTheme.colors.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDelaySettingsCardOff() {
    MockStationTheme {
        PreviewColumn {
            DelaySettingsCard(
                delayType = "OFF",
                delayValue = "",
                targetFiles = "",
                isEnabled = false,
                onClick = {},
                titleLabel = "Delay Settings",
                typeLabel = "Type:",
                delayLabel = "Delay:",
                targetLabel = "Target:",
                enabledLabel = "Enabled",
                disabledLabel = "Disabled",
                editSettingsContentDescription = "Edit Settings",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDelaySettingsCardPreset() {
    MockStationTheme {
        PreviewColumn {
            DelaySettingsCard(
                delayType = "Preset",
                delayValue = "5000ms",
                targetFiles = "All files",
                isEnabled = true,
                onClick = {},
                titleLabel = "Delay Settings",
                typeLabel = "Type:",
                delayLabel = "Delay:",
                targetLabel = "Target:",
                enabledLabel = "Enabled",
                disabledLabel = "Disabled",
                editSettingsContentDescription = "Edit Settings",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDelaySettingsCardCustom() {
    MockStationTheme {
        PreviewColumn {
            DelaySettingsCard(
                delayType = "Custom",
                delayValue = "30000ms",
                targetFiles = "3 files",
                isEnabled = true,
                onClick = {},
                titleLabel = "Delay Settings",
                typeLabel = "Type:",
                delayLabel = "Delay:",
                targetLabel = "Target:",
                enabledLabel = "Enabled",
                disabledLabel = "Disabled",
                editSettingsContentDescription = "Edit Settings",
            )
        }
    }
}
