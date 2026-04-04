package com.github.syunpeii.mockstation.core.designsystem.component.organism

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SelectableCard
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SettingSectionHeader
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun ConnectionSettingsSection(
    sectionTitle: String,
    description: String,
    addButtonText: String,
    connections: List<ConnectionDisplay>,
    selectedIndex: Int,
    canAddConnection: Boolean,
    maxConnections: Int,
    onSelectConnection: (Int) -> Unit,
    onEditConnection: (Int) -> Unit,
    onDeleteConnection: (Int) -> Unit,
    onAddConnection: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
    ) {
        SettingSectionHeader(title = sectionTitle)

        Text(
            text = description,
            style = MockStationTheme.typography.bodyMedium,
            color = MockStationTheme.colors.onSurfaceVariant,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
        ) {
            repeat(maxConnections) { index ->
                if (index < connections.size) {
                    val connection = connections[index]
                    SelectableCard(
                        name = connection.name,
                        url = connection.url,
                        description = connection.description,
                        isSelected = index == selectedIndex,
                        onSelect = { onSelectConnection(index) },
                        onEdit = { onEditConnection(index) },
                        onDelete = { onDeleteConnection(index) },
                    )
                }
            }
        }

        PrimaryButton(
            text = addButtonText,
            onClick = onAddConnection,
            enabled = canAddConnection,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

data class ConnectionDisplay(
    val name: String,
    val url: String,
    val description: String,
)

@Preview
@Composable
private fun PreviewConnectionSettingsSectionEmpty() {
    MockStationTheme {
        PreviewBox {
            ConnectionSettingsSection(
                sectionTitle = "Connection Settings",
                description = "Manage up to 5 server connections",
                addButtonText = "Add Connection (0/5)",
                connections = emptyList(),
                selectedIndex = -1,
                canAddConnection = true,
                maxConnections = 5,
                onSelectConnection = {},
                onEditConnection = {},
                onDeleteConnection = {},
                onAddConnection = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewConnectionSettingsSectionWithConnections() {
    MockStationTheme {
        PreviewBox {
            ConnectionSettingsSection(
                sectionTitle = "Connection Settings",
                description = "Manage up to 5 server connections",
                addButtonText = "Add Connection (2/5)",
                connections = listOf(
                    ConnectionDisplay("Local Dev", "http://localhost:8080", "Development server"),
                    ConnectionDisplay("Staging", "https://staging.example.com", "Staging environment"),
                ),
                selectedIndex = 0,
                canAddConnection = true,
                maxConnections = 5,
                onSelectConnection = {},
                onEditConnection = {},
                onDeleteConnection = {},
                onAddConnection = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewConnectionSettingsSectionFull() {
    MockStationTheme {
        PreviewBox {
            ConnectionSettingsSection(
                sectionTitle = "Connection Settings",
                description = "Manage up to 5 server connections",
                addButtonText = "Add Connection (5/5)",
                connections = listOf(
                    ConnectionDisplay("Local Dev", "http://localhost:8080", "Development"),
                    ConnectionDisplay("Staging", "https://staging.example.com", "Staging"),
                    ConnectionDisplay("Production", "https://api.example.com", "Production"),
                    ConnectionDisplay("Test", "https://test.example.com", "Test"),
                    ConnectionDisplay("Backup", "https://backup.example.com", "Backup"),
                ),
                selectedIndex = 2,
                canAddConnection = false,
                maxConnections = 5,
                onSelectConnection = {},
                onEditConnection = {},
                onDeleteConnection = {},
                onAddConnection = {},
            )
        }
    }
}
