package com.github.syunpeii.mockstation.app.ui.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.HeadlineMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ActiveDevicesSection
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ConnectionStatusSection
import com.github.syunpeii.mockstation.core.designsystem.component.organism.DeviceInfo
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ServerSummarySection
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.common_not_set
import mockstation.composeapp.generated.resources.home_connection_button
import mockstation.composeapp.generated.resources.home_connection_name
import mockstation.composeapp.generated.resources.home_connection_url
import mockstation.composeapp.generated.resources.home_devices_device_id
import mockstation.composeapp.generated.resources.home_devices_device_name
import mockstation.composeapp.generated.resources.home_devices_empty
import mockstation.composeapp.generated.resources.home_devices_last_request
import mockstation.composeapp.generated.resources.home_section_connection
import mockstation.composeapp.generated.resources.home_section_devices
import mockstation.composeapp.generated.resources.home_section_summary
import mockstation.composeapp.generated.resources.home_summary_current_testcase
import mockstation.composeapp.generated.resources.home_summary_devices_count
import mockstation.composeapp.generated.resources.home_summary_recent_requests
import mockstation.composeapp.generated.resources.home_summary_requests_count
import mockstation.composeapp.generated.resources.home_summary_total_devices
import mockstation.composeapp.generated.resources.nav_home
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeBaseScreen(
        uiState = uiState,
        onNavigateToSettings = viewModel::onNavigateToSettings,
    )
}

@Composable
private fun HomeBaseScreen(
    uiState: HomeUiState,
    onNavigateToSettings: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(MockStationTheme.colors.background)
            .fillMaxSize(),
    ) {
        when (uiState) {
            is HomeUiState.Loading -> HomeScreenLoading()
            is HomeUiState.Stable -> HomeScreenContent(
                uiState = uiState,
                onNavigateToSettings = onNavigateToSettings,
            )

            is HomeUiState.Error -> HomeScreenError(
                message = uiState.message,
            )
        }
    }
}

@Composable
private fun HomeScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState.Stable,
    onNavigateToSettings: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MockStationTheme.spacing.medium),
        contentPadding = PaddingValues(vertical = MockStationTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
    ) {
        item {
            HeadlineMediumText(
                text = stringResource(Res.string.nav_home),
            )
        }

        item {
            ConnectionStatusSection(
                sectionTitle = stringResource(Res.string.home_section_connection),
                connectionNameLabel = stringResource(Res.string.home_connection_name),
                connectionName = uiState.connection.name,
                connectionUrlLabel = stringResource(Res.string.home_connection_url),
                connectionUrl = uiState.connection.url,
                buttonText = stringResource(Res.string.home_connection_button),
                onNavigateToSettings = onNavigateToSettings,
            )
        }

        item {
            ActiveDevicesSection(
                sectionTitle = stringResource(Res.string.home_section_devices),
                devices = uiState.activeDevices.map { device ->
                    DeviceInfo(
                        deviceId = device.deviceId,
                        deviceName = device.deviceName,
                        lastRequestTime = device.getFormattedLastRequestTime(),
                    )
                },
                emptyMessage = stringResource(Res.string.home_devices_empty),
                deviceIdLabel = stringResource(Res.string.home_devices_device_id),
                deviceNameLabel = stringResource(Res.string.home_devices_device_name),
                lastRequestLabel = stringResource(Res.string.home_devices_last_request),
                notSetText = stringResource(Res.string.common_not_set),
            )
        }

        item {
            ServerSummarySection(
                sectionTitle = stringResource(Res.string.home_section_summary),
                currentTestCaseLabel = stringResource(Res.string.home_summary_current_testcase),
                currentTestCaseId = uiState.currentTestCaseId,
                totalDevicesLabel = stringResource(Res.string.home_summary_total_devices),
                totalDevicesValue = stringResource(
                    Res.string.home_summary_devices_count,
                    uiState.serverSummary.totalDeviceCount,
                ),
                recentRequestsLabel = stringResource(Res.string.home_summary_recent_requests),
                recentRequestsValue = stringResource(
                    Res.string.home_summary_requests_count,
                    uiState.serverSummary.recentRequestCount,
                ),
                notSetText = stringResource(Res.string.common_not_set),
            )
        }
    }
}

@Composable
private fun HomeScreenError(
    message: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BodyMediumText(
            text = message,
            color = MockStationTheme.colors.error,
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreenLoading() {
    MockStationTheme {
        HomeBaseScreen(
            uiState = HomeUiState.Loading,
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreenStable() {
    MockStationTheme {
        HomeBaseScreen(
            uiState = HomeUiState.Stable(
                connection = ConnectionStatus(
                    name = "Local Server",
                    url = "http://localhost:8080",
                ),
                activeDevices = listOf(
                    ActiveDevice(
                        deviceId = "device-001",
                        deviceName = "Test Device 1",
                        lastRequestTimeMillis = System.currentTimeMillis(),
                    ),
                    ActiveDevice(
                        deviceId = "device-002",
                        deviceName = null,
                        lastRequestTimeMillis = System.currentTimeMillis() - 60000,
                    ),
                    ActiveDevice(
                        deviceId = "device-003",
                        deviceName = "Production Device",
                        lastRequestTimeMillis = System.currentTimeMillis() - 300000,
                    ),
                ),
                currentTestCaseId = "test-case-123",
                serverSummary = ServerSummary(
                    totalDeviceCount = 5,
                    recentRequestCount = 42,
                ),
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreenStableEmpty() {
    MockStationTheme {
        HomeBaseScreen(
            uiState = HomeUiState.Stable(
                connection = ConnectionStatus(
                    name = "Local Server",
                    url = "http://localhost:8080",
                ),
                activeDevices = emptyList(),
                currentTestCaseId = null,
                serverSummary = ServerSummary(
                    totalDeviceCount = 0,
                    recentRequestCount = 0,
                ),
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreenError() {
    MockStationTheme {
        HomeBaseScreen(
            uiState = HomeUiState.Error(
                message = "Failed to load home screen data",
            ),
        )
    }
}
