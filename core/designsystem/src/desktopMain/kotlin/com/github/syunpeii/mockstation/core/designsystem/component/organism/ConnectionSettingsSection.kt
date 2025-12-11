package com.github.syunpeii.mockstation.core.designsystem.component.organism

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SelectableCard
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SettingSectionHeader
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import kotlinx.serialization.Serializable

// For core-designsystem module, we need to define a minimal Connection data class
// The actual Connection from the app module will be passed as a generic type
@Serializable
data class ConnectionData(
    val id: String,
    val name: String,
    val url: String,
    val description: String,
)

@Composable
fun <T> ConnectionSettingsSection(
    connections: List<T>,
    selectedIndex: Int,
    canAddConnection: Boolean,
    maxConnections: Int,
    onSelectConnection: (Int) -> Unit,
    onEditConnection: (Int) -> Unit,
    onDeleteConnection: (Int) -> Unit,
    onAddConnection: () -> Unit,
    getName: (T) -> String,
    getUrl: (T) -> String,
    getDescription: (T) -> String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SettingSectionHeader(title = "Connection Settings")

        BodyMediumText(
            text = "Manage up to $maxConnections server connections",
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
                        name = getName(connection),
                        url = getUrl(connection),
                        description = getDescription(connection),
                        isSelected = index == selectedIndex,
                        onSelect = { onSelectConnection(index) },
                        onEdit = { onEditConnection(index) },
                        onDelete = { onDeleteConnection(index) },
                    )
                }
            }
        }

        PrimaryButton(
            text = "Add Connection (${connections.size}/$maxConnections)",
            onClick = onAddConnection,
            enabled = canAddConnection,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun PreviewConnectionSettingsSectionEmpty() {
    MockStationTheme {
        PreviewColumn {
            ConnectionSettingsSection(
                connections = emptyList<ConnectionData>(),
                selectedIndex = -1,
                canAddConnection = true,
                maxConnections = 5,
                onSelectConnection = {},
                onEditConnection = {},
                onDeleteConnection = {},
                onAddConnection = {},
                getName = { it.name },
                getUrl = { it.url },
                getDescription = { it.description },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewConnectionSettingsSectionWithConnections() {
    MockStationTheme {
        PreviewColumn {
            ConnectionSettingsSection(
                connections = listOf(
                    ConnectionData("1", "Local Dev", "http://localhost:8080", "Development server"),
                    ConnectionData("2", "Staging", "https://staging.example.com", "Staging environment"),
                ),
                selectedIndex = 0,
                canAddConnection = true,
                maxConnections = 5,
                onSelectConnection = {},
                onEditConnection = {},
                onDeleteConnection = {},
                onAddConnection = {},
                getName = { it.name },
                getUrl = { it.url },
                getDescription = { it.description },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewConnectionSettingsSectionFull() {
    MockStationTheme {
        PreviewColumn {
            ConnectionSettingsSection(
                connections = listOf(
                    ConnectionData("1", "Local Dev", "http://localhost:8080", "Development"),
                    ConnectionData("2", "Staging", "https://staging.example.com", "Staging"),
                    ConnectionData("3", "Production", "https://api.example.com", "Production"),
                    ConnectionData("4", "Test", "https://test.example.com", "Test"),
                    ConnectionData("5", "Backup", "https://backup.example.com", "Backup"),
                ),
                selectedIndex = 2,
                canAddConnection = false,
                maxConnections = 5,
                onSelectConnection = {},
                onEditConnection = {},
                onDeleteConnection = {},
                onAddConnection = {},
                getName = { it.name },
                getUrl = { it.url },
                getDescription = { it.description },
            )
        }
    }
}
