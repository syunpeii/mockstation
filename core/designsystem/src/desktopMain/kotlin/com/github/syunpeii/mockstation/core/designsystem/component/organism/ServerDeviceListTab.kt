package com.github.syunpeii.mockstation.core.designsystem.component.organism

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyLargeText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SearchBar
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.ServerDeviceCard
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun ServerDeviceListTab(
    devices: List<ServerDevice>,
    filterText: String,
    onFilterChange: (String) -> Unit,
    onRegisterDevice: (String) -> Unit,
    isLoading: Boolean,
    error: String?,
    modifier: Modifier = Modifier,
    searchPlaceholder: String,
    loadingLabel: String,
    errorLabel: String,
    noDevicesLabel: String,
    adjustFilterLabel: String,
    deviceIdLabel: String,
    registeredLabel: String,
    registerButtonLabel: String,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        SearchBar(
            value = filterText,
            onValueChange = onFilterChange,
            placeholder = searchPlaceholder,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MockStationTheme.spacing.medium),
        )

        when {
            isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MockStationTheme.spacing.extraLarge),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(
                        color = MockStationTheme.colors.primary,
                    )
                    BodyMediumText(
                        text = loadingLabel,
                        modifier = Modifier.padding(top = MockStationTheme.spacing.medium),
                        color = MockStationTheme.colors.onBackground,
                    )
                }
            }

            error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MockStationTheme.spacing.extraLarge),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    BodyLargeText(
                        text = errorLabel,
                        color = MockStationTheme.colors.error,
                    )
                    BodyMediumText(
                        text = error,
                        color = MockStationTheme.colors.onBackground,
                        modifier = Modifier.padding(top = MockStationTheme.spacing.small),
                    )
                }
            }

            devices.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MockStationTheme.spacing.extraLarge),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    BodyLargeText(
                        text = noDevicesLabel,
                        color = MockStationTheme.colors.onBackground,
                    )
                    BodyMediumText(
                        text = adjustFilterLabel,
                        color = MockStationTheme.colors.onBackground,
                        modifier = Modifier.padding(top = MockStationTheme.spacing.small),
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = MockStationTheme.spacing.medium,
                        vertical = MockStationTheme.spacing.small,
                    ),
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                ) {
                    items(devices, key = { it.deviceId }) { device ->
                        ServerDeviceCard(
                            deviceId = device.deviceId,
                            isRegistered = device.isRegistered,
                            onRegister = { onRegisterDevice(device.deviceId) },
                            deviceIdLabel = deviceIdLabel,
                            registeredLabel = registeredLabel,
                            registerButtonLabel = registerButtonLabel,
                        )
                    }
                }
            }
        }
    }
}

data class ServerDevice(
    val deviceId: String,
    val isRegistered: Boolean,
)

@Preview
@Composable
private fun PreviewServerDeviceListTabLoading() {
    MockStationTheme {
        PreviewBox {
            ServerDeviceListTab(
                devices = emptyList(),
                filterText = "",
                onFilterChange = {},
                onRegisterDevice = {},
                isLoading = true,
                error = null,
                searchPlaceholder = "Search devices...",
                loadingLabel = "Loading devices...",
                errorLabel = "Error loading devices",
                noDevicesLabel = "No devices found",
                adjustFilterLabel = "Try adjusting your filter",
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewServerDeviceListTabError() {
    MockStationTheme {
        PreviewBox {
            ServerDeviceListTab(
                devices = emptyList(),
                filterText = "",
                onFilterChange = {},
                onRegisterDevice = {},
                isLoading = false,
                error = "Failed to connect to server",
                searchPlaceholder = "Search devices...",
                loadingLabel = "Loading devices...",
                errorLabel = "Error loading devices",
                noDevicesLabel = "No devices found",
                adjustFilterLabel = "Try adjusting your filter",
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewServerDeviceListTabEmpty() {
    MockStationTheme {
        PreviewBox {
            var filterText by remember { mutableStateOf("nonexistent") }
            ServerDeviceListTab(
                devices = emptyList(),
                filterText = filterText,
                onFilterChange = { filterText = it },
                onRegisterDevice = {},
                isLoading = false,
                error = null,
                searchPlaceholder = "Search devices...",
                loadingLabel = "Loading devices...",
                errorLabel = "Error loading devices",
                noDevicesLabel = "No devices found",
                adjustFilterLabel = "Try adjusting your filter",
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewServerDeviceListTabWithDevices() {
    MockStationTheme {
        PreviewBox {
            var filterText by remember { mutableStateOf("") }
            ServerDeviceListTab(
                devices = listOf(
                    ServerDevice("device-001", false),
                    ServerDevice("device-002", true),
                    ServerDevice("device-003", false),
                    ServerDevice("device-004", false),
                    ServerDevice("device-005", true),
                    ServerDevice("550e8400-e29b-41d4-a716-446655440000", false),
                    ServerDevice("660f9511-f3ac-52e5-b827-557766551111", true),
                ),
                filterText = filterText,
                onFilterChange = { filterText = it },
                onRegisterDevice = {},
                isLoading = false,
                error = null,
                searchPlaceholder = "Search devices...",
                loadingLabel = "Loading devices...",
                errorLabel = "Error loading devices",
                noDevicesLabel = "No devices found",
                adjustFilterLabel = "Try adjusting your filter",
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
        }
    }
}
