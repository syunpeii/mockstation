package com.github.syunpeii.mockstation.core.designsystem.component.organism

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyLargeText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.DeviceCard
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun RegisteredDevicesTab(
    devices: List<RegisteredDevice>,
    onEditName: (String) -> Unit,
    onDelete: (String) -> Unit,
    onToggleEnabled: (String, Boolean) -> Unit,
    onTestCaseClick: (String) -> Unit,
    onDelaySettingsClick: (String) -> Unit,
    onAddDevice: () -> Unit,
    modifier: Modifier = Modifier,
    emptyDevicesLabel: String,
    emptyDevicesHintLabel: String,
    addDeviceLabel: String,
    deviceIdLabel: String,
    editNameLabel: String,
    deleteLabel: String,
    deviceNameLabel: String,
    testCaseLabel: String,
    delaySettingsLabel: String,
    enabledLabel: String,
    disabledLabel: String,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        if (devices.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MockStationTheme.spacing.extraLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                BodyLargeText(
                    text = emptyDevicesLabel,
                    color = MockStationTheme.colors.onBackground,
                )
                BodyMediumText(
                    text = emptyDevicesHintLabel,
                    color = MockStationTheme.colors.onBackground,
                    modifier = Modifier.padding(top = MockStationTheme.spacing.small),
                )
                PrimaryButton(
                    text = addDeviceLabel,
                    onClick = onAddDevice,
                    modifier = Modifier.padding(top = MockStationTheme.spacing.medium),
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    MockStationTheme.spacing.medium,
                ),
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
            ) {
                items(devices, key = { it.id }) { device ->
                    DeviceCard(
                        deviceId = device.id,
                        deviceName = device.name,
                        testCaseId = device.testCaseId,
                        isEnabled = device.isEnabled,
                        delaySettingsDisplay = device.delaySettingsDisplay,
                        onEditName = { onEditName(device.id) },
                        onDelete = { onDelete(device.id) },
                        onToggleEnabled = { enabled -> onToggleEnabled(device.id, enabled) },
                        onTestCaseClick = { onTestCaseClick(device.testCaseId) },
                        onDelaySettingsClick = { onDelaySettingsClick(device.id) },
                        deviceIdLabel = deviceIdLabel,
                        editNameLabel = editNameLabel,
                        deleteLabel = deleteLabel,
                        deviceNameLabel = deviceNameLabel,
                        testCaseLabel = testCaseLabel,
                        delaySettingsLabel = delaySettingsLabel,
                        enabledLabel = enabledLabel,
                        disabledLabel = disabledLabel,
                    )
                }
            }
        }
    }
}

data class RegisteredDevice(
    val id: String,
    val name: String,
    val testCaseId: String,
    val isEnabled: Boolean,
    val delaySettingsDisplay: String,
)

@Preview
@Composable
private fun PreviewRegisteredDevicesTabEmpty() {
    MockStationTheme {
        PreviewBox {
            RegisteredDevicesTab(
                devices = emptyList(),
                onEditName = {},
                onDelete = {},
                onToggleEnabled = { _, _ -> },
                onTestCaseClick = {},
                onDelaySettingsClick = {},
                onAddDevice = {},
                emptyDevicesLabel = "No devices registered",
                emptyDevicesHintLabel = "Tap the button above to add a new device.",
                addDeviceLabel = "Add Device",
                deviceIdLabel = "",
                editNameLabel = "",
                deleteLabel = "",
                deviceNameLabel = "",
                testCaseLabel = "",
                delaySettingsLabel = "",
                enabledLabel = "",
                disabledLabel = "",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRegisteredDevicesTabWithDevices() {
    MockStationTheme {
        PreviewBox {
            RegisteredDevicesTab(
                devices = listOf(
                    RegisteredDevice(
                        id = "550e8400-e29b-41d4-a716-446655440000",
                        name = "Test Device 1",
                        testCaseId = "test-case-abc",
                        isEnabled = true,
                        delaySettingsDisplay = "Preset: 5000ms (All files)",
                    ),
                    RegisteredDevice(
                        id = "660f9511-f3ac-52e5-b827-557766551111",
                        name = "Production Device",
                        testCaseId = "prod-test-001",
                        isEnabled = false,
                        delaySettingsDisplay = "OFF",
                    ),
                    RegisteredDevice(
                        id = "770g0622-g4bd-63f6-c938-668877662222",
                        name = "Development Device",
                        testCaseId = "dev-test-123",
                        isEnabled = true,
                        delaySettingsDisplay = "Custom: 30000ms (3 files)",
                    ),
                ),
                onEditName = {},
                onDelete = {},
                onToggleEnabled = { _, _ -> },
                onTestCaseClick = {},
                onDelaySettingsClick = {},
                onAddDevice = {},
                emptyDevicesLabel = "No devices registered",
                emptyDevicesHintLabel = "Tap the button above to add a new device.",
                addDeviceLabel = "Add Device",
                deviceIdLabel = "",
                editNameLabel = "",
                deleteLabel = "",
                deviceNameLabel = "",
                testCaseLabel = "",
                delaySettingsLabel = "",
                enabledLabel = "",
                disabledLabel = "",
            )
        }
    }
}
