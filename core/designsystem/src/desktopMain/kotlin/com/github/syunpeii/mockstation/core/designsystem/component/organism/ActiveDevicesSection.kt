package com.github.syunpeii.mockstation.core.designsystem.component.organism

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.DeviceInfoCard
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SettingSectionHeader
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

data class DeviceInfo(
    val deviceId: String,
    val deviceName: String?,
    val lastRequestTime: String,
)

@Composable
fun ActiveDevicesSection(
    sectionTitle: String,
    devices: List<DeviceInfo>,
    emptyMessage: String,
    deviceIdLabel: String,
    deviceNameLabel: String,
    lastRequestLabel: String,
    notSetText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
    ) {
        SettingSectionHeader(
            title = sectionTitle,
        )

        if (devices.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MockStationTheme.colors.surface,
                ),
            ) {
                BodyMediumText(
                    text = emptyMessage,
                    color = MockStationTheme.colors.onSurfaceVariant,
                    modifier = Modifier.padding(MockStationTheme.spacing.medium),
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                devices.forEach { device ->
                    DeviceInfoCard(
                        deviceIdLabel = deviceIdLabel,
                        deviceId = device.deviceId,
                        deviceNameLabel = deviceNameLabel,
                        deviceName = device.deviceName,
                        lastRequestLabel = lastRequestLabel,
                        lastRequestTime = device.lastRequestTime,
                        notSetText = notSetText,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewActiveDevicesSection() {
    MockStationTheme {
        PreviewColumn {
            ActiveDevicesSection(
                sectionTitle = "Active Devices",
                devices = listOf(
                    DeviceInfo(
                        deviceId = "device-001",
                        deviceName = "Test Device 1",
                        lastRequestTime = "2025-12-13 22:30:15.123",
                    ),
                    DeviceInfo(
                        deviceId = "device-002",
                        deviceName = null,
                        lastRequestTime = "2025-12-13 22:29:15.456",
                    ),
                ),
                emptyMessage = "No active devices",
                deviceIdLabel = "Device ID",
                deviceNameLabel = "Device Name",
                lastRequestLabel = "Last Request",
                notSetText = "Not set",
            )

            ActiveDevicesSection(
                sectionTitle = "Active Devices",
                devices = emptyList(),
                emptyMessage = "No active devices",
                deviceIdLabel = "Device ID",
                deviceNameLabel = "Device Name",
                lastRequestLabel = "Last Request",
                notSetText = "Not set",
            )
        }
    }
}
