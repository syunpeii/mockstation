package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumMonospaceText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodySmallText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.SectionItemTitleText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun DeviceInfoCard(
    deviceIdLabel: String,
    deviceId: String,
    deviceNameLabel: String,
    deviceName: String?,
    lastRequestLabel: String,
    lastRequestTime: String,
    notSetText: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MockStationTheme.colors.surface,
        ),
        border = BorderStroke(1.dp, MockStationTheme.colors.outlineVariant),
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
                BodySmallText(
                    text = deviceIdLabel,
                    color = MockStationTheme.colors.onSurfaceVariant,
                )
                BodyMediumMonospaceText(
                    text = deviceId,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BodySmallText(
                    text = deviceNameLabel,
                    color = MockStationTheme.colors.onSurfaceVariant,
                )
                if (deviceName != null) {
                    SectionItemTitleText(
                        text = deviceName,
                    )
                } else {
                    BodyMediumText(
                        text = notSetText,
                        color = MockStationTheme.colors.onSurfaceVariant,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BodySmallText(
                    text = lastRequestLabel,
                    color = MockStationTheme.colors.onSurfaceVariant,
                )
                BodyMediumMonospaceText(
                    text = lastRequestTime,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDeviceInfoCard() {
    MockStationTheme {
        PreviewColumn {
            DeviceInfoCard(
                deviceIdLabel = "Device ID",
                deviceId = "device-001",
                deviceNameLabel = "Device Name",
                deviceName = "Test Device 1",
                lastRequestLabel = "Last Request",
                lastRequestTime = "2025-12-13 22:30:15.123",
                notSetText = "Not set",
            )
            DeviceInfoCard(
                deviceIdLabel = "Device ID",
                deviceId = "device-002",
                deviceNameLabel = "Device Name",
                deviceName = null,
                lastRequestLabel = "Last Request",
                lastRequestTime = "2025-12-13 22:29:15.456",
                notSetText = "Not set",
            )
        }
    }
}
