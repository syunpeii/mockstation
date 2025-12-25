package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumMonospaceText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodySmallText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.LabelText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun DeviceCard(
    deviceId: String,
    deviceName: String,
    testCaseId: String,
    isEnabled: Boolean,
    delaySettingsDisplay: String,
    onEditName: () -> Unit,
    onDelete: () -> Unit,
    onToggleEnabled: (Boolean) -> Unit,
    onTestCaseClick: () -> Unit,
    onDelaySettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    deviceIdLabel: String,
    editNameLabel: String,
    deleteLabel: String,
    deviceNameLabel: String,
    testCaseLabel: String,
    delaySettingsLabel: String,
    enabledLabel: String,
    disabledLabel: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isEnabled) MockStationTheme.colors.primary.copy(alpha = 0.5f) else MockStationTheme.colors.outline,
                shape = MockStationTheme.shapes.medium,
            ),
        colors = CardDefaults.cardColors(
            containerColor = MockStationTheme.colors.surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MockStationTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                ) {
                    LabelText(text = deviceIdLabel)
                    BodyMediumMonospaceText(
                        text = deviceId,
                        color = MockStationTheme.colors.onSurface,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onEditName) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = editNameLabel,
                            tint = MockStationTheme.colors.primary,
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = deleteLabel,
                            tint = MockStationTheme.colors.error,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                ) {
                    LabelText(text = deviceNameLabel)
                    BodyMediumText(text = deviceName)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                ) {
                    LabelText(text = testCaseLabel)
                    TextButton(onClick = onTestCaseClick) {
                        BodyMediumText(
                            text = testCaseId,
                            color = MockStationTheme.colors.primary,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                ) {
                    LabelText(text = delaySettingsLabel)
                    TextButton(onClick = onDelaySettingsClick) {
                        BodySmallText(
                            text = delaySettingsDisplay,
                            color = MockStationTheme.colors.primary,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LabelText(
                    text = if (isEnabled) {
                        enabledLabel
                    } else {
                        disabledLabel
                    },
                )
                Switch(
                    checked = isEnabled,
                    onCheckedChange = onToggleEnabled,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDeviceCardEnabled() {
    MockStationTheme {
        PreviewColumn {
            DeviceCard(
                deviceId = "550e8400-e29b-41d4-a716-446655440000",
                deviceName = "Test Device 1",
                testCaseId = "test-case-abc",
                isEnabled = true,
                delaySettingsDisplay = "Preset: 5000ms (All files)",
                onEditName = {},
                onDelete = {},
                onToggleEnabled = {},
                onTestCaseClick = {},
                onDelaySettingsClick = {},
                deviceIdLabel = "Device ID",
                editNameLabel = "Edit Device Name",
                deleteLabel = "Delete Device",
                deviceNameLabel = "Device Name",
                testCaseLabel = "Test Case ID",
                delaySettingsLabel = "Delay Settings",
                enabledLabel = "Enabled",
                disabledLabel = "Disabled",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDeviceCardDisabled() {
    MockStationTheme {
        PreviewColumn {
            DeviceCard(
                deviceId = "660f9511-f3ac-52e5-b827-557766551111",
                deviceName = "Production Device",
                testCaseId = "prod-test-001",
                isEnabled = false,
                delaySettingsDisplay = "OFF",
                onEditName = {},
                onDelete = {},
                onToggleEnabled = {},
                onTestCaseClick = {},
                onDelaySettingsClick = {},
                deviceIdLabel = "Device ID",
                editNameLabel = "Edit Device Name",
                deleteLabel = "Delete Device",
                deviceNameLabel = "Device Name",
                testCaseLabel = "Test Case ID",
                delaySettingsLabel = "Delay Settings",
                enabledLabel = "Enabled",
                disabledLabel = "Disabled",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDeviceCardLongName() {
    MockStationTheme {
        PreviewColumn {
            DeviceCard(
                deviceId = "770g0622-g4bd-63f6-c938-668877662222",
                deviceName = "Very Long Device Name That Might Cause Layout Issues",
                testCaseId = "very-long-test-case-id-example",
                isEnabled = true,
                delaySettingsDisplay = "Custom: 30000ms (3 files)",
                onEditName = {},
                onDelete = {},
                onToggleEnabled = {},
                onTestCaseClick = {},
                onDelaySettingsClick = {},
                deviceIdLabel = "Device ID",
                editNameLabel = "Edit Device Name",
                deleteLabel = "Delete Device",
                deviceNameLabel = "Device Name",
                testCaseLabel = "Test Case ID",
                delaySettingsLabel = "Delay Settings",
                enabledLabel = "Enabled",
                disabledLabel = "Disabled",
            )
        }
    }
}
