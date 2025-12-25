package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
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
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumMonospaceText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodySmallText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.LabelText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun ServerDeviceCard(
    deviceId: String,
    isRegistered: Boolean,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
    deviceIdLabel: String,
    registeredLabel: String,
    registerButtonLabel: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isRegistered) {
                    MockStationTheme.colors.primary.copy(alpha = 0.3f)
                } else {
                    MockStationTheme.colors.outline
                },
                shape = MockStationTheme.shapes.medium,
            ),
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
                LabelText(text = deviceIdLabel)
                BodyMediumMonospaceText(
                    text = deviceId,
                    color = MockStationTheme.colors.onSurface,
                )
                if (isRegistered) {
                    BodySmallText(
                        text = "✓ $registeredLabel",
                        color = MockStationTheme.colors.primary,
                    )
                }
            }

            if (!isRegistered) {
                PrimaryButton(
                    text = registerButtonLabel,
                    onClick = onRegister,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewServerDeviceCard() {
    MockStationTheme {
        PreviewColumn {
            ServerDeviceCard(
                deviceId = "550e8400-e29b-41d4-a716-446655440000",
                isRegistered = false,
                onRegister = {},
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewServerDeviceCardRegistered() {
    MockStationTheme {
        PreviewColumn {
            ServerDeviceCard(
                deviceId = "660f9511-f3ac-52e5-b827-557766551111",
                isRegistered = true,
                onRegister = {},
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewServerDeviceCardMultiple() {
    MockStationTheme {
        PreviewColumn {
            ServerDeviceCard(
                deviceId = "device-001",
                isRegistered = false,
                onRegister = {},
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
            ServerDeviceCard(
                deviceId = "device-002",
                isRegistered = true,
                onRegister = {},
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
            ServerDeviceCard(
                deviceId = "device-003",
                isRegistered = false,
                onRegister = {},
                deviceIdLabel = "Device ID",
                registeredLabel = "Registered",
                registerButtonLabel = "Register",
            )
        }
    }
}
