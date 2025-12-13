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
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.InfoDisplayRow
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SettingSectionHeader
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun ConnectionStatusSection(
    sectionTitle: String,
    connectionNameLabel: String,
    connectionName: String,
    connectionUrlLabel: String,
    connectionUrl: String,
    buttonText: String,
    onNavigateToSettings: () -> Unit,
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
                    label = connectionNameLabel,
                    value = connectionName,
                )
                InfoDisplayRow(
                    label = connectionUrlLabel,
                    value = connectionUrl,
                )

                PrimaryButton(
                    text = buttonText,
                    onClick = onNavigateToSettings,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MockStationTheme.spacing.small),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewConnectionStatusSection() {
    MockStationTheme {
        PreviewColumn {
            ConnectionStatusSection(
                sectionTitle = "Current Connection",
                connectionNameLabel = "Connection Name",
                connectionName = "Local Server",
                connectionUrlLabel = "URL",
                connectionUrl = "http://localhost:8080",
                buttonText = "Go to Settings",
                onNavigateToSettings = {},
            )
        }
    }
}
