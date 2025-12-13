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
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.InfoDisplayRow
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SettingSectionHeader
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun ServerSummarySection(
    sectionTitle: String,
    currentTestCaseLabel: String,
    currentTestCaseId: String?,
    totalDevicesLabel: String,
    totalDevicesValue: String,
    recentRequestsLabel: String,
    recentRequestsValue: String,
    notSetText: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SettingSectionHeader(
            title = sectionTitle,
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
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
                InfoDisplayRow(
                    label = currentTestCaseLabel,
                    value = currentTestCaseId ?: notSetText,
                    valueColor = if (currentTestCaseId != null) {
                        MockStationTheme.colors.onSurfaceVariant
                    } else {
                        MockStationTheme.colors.onSurfaceVariant.copy(alpha = 0.6f)
                    },
                )
                InfoDisplayRow(
                    label = totalDevicesLabel,
                    value = totalDevicesValue,
                )
                InfoDisplayRow(
                    label = recentRequestsLabel,
                    value = recentRequestsValue,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewServerSummarySection() {
    MockStationTheme {
        PreviewColumn {
            ServerSummarySection(
                sectionTitle = "Server Summary",
                currentTestCaseLabel = "Current Test Case",
                currentTestCaseId = "test-case-123",
                totalDevicesLabel = "Total Devices",
                totalDevicesValue = "5 devices",
                recentRequestsLabel = "Recent Requests",
                recentRequestsValue = "42 requests",
                notSetText = "Not set",
            )

            ServerSummarySection(
                sectionTitle = "Server Summary",
                currentTestCaseLabel = "Current Test Case",
                currentTestCaseId = null,
                totalDevicesLabel = "Total Devices",
                totalDevicesValue = "0 devices",
                recentRequestsLabel = "Recent Requests",
                recentRequestsValue = "0 requests",
                notSetText = "Not set",
            )
        }
    }
}
