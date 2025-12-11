package com.github.syunpeii.mockstation.app.ui.settings

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
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyLargeText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumMonospaceText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.HeadlineMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SettingButtonSection
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SettingFilterSection
import com.github.syunpeii.mockstation.core.designsystem.component.organism.AppInfoSection
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ConnectionSettingsSection
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsBaseScreen(
        uiState = uiState,
        onThemeModeChange = viewModel::onThemeModeChange,
        onNavigationDisplayModeChange = viewModel::onNavigationDisplayModeChange,
        onChooseDirectory = viewModel::onTestCaseDirectoryClick,
        onSelectConnection = viewModel::onSelectConnection,
        onAddConnection = viewModel::onAddConnection,
        onEditConnection = viewModel::onEditConnection,
        onDeleteConnection = viewModel::onDeleteConnection,
    )
}

@Composable
private fun SettingsBaseScreen(
    uiState: SettingsUiState,
    onThemeModeChange: (ThemeMode) -> Unit = {},
    onNavigationDisplayModeChange: (NavigationDisplayMode) -> Unit = {},
    onChooseDirectory: () -> Unit = {},
    onSelectConnection: (Int) -> Unit = {},
    onAddConnection: () -> Unit = {},
    onEditConnection: (Int) -> Unit = {},
    onDeleteConnection: (Int) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(MockStationTheme.colors.background)
            .fillMaxSize(),
    ) {
        when (uiState) {
            is SettingsUiState.Loading -> SettingsScreenLoading()
            is SettingsUiState.Stable -> SettingsScreenContent(
                uiState = uiState,
                onThemeModeChange = onThemeModeChange,
                onNavigationDisplayModeChange = onNavigationDisplayModeChange,
                onChooseDirectory = onChooseDirectory,
                onSelectConnection = onSelectConnection,
                onAddConnection = onAddConnection,
                onEditConnection = onEditConnection,
                onDeleteConnection = onDeleteConnection,
            )

            is SettingsUiState.Error -> SettingsScreenError(
                message = uiState.message,
            )
        }
    }
}

@Composable
private fun SettingsScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MockStationTheme.colors.primary,
        )
    }
}

@Composable
private fun SettingsScreenError(
    message: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BodyLargeText(
            text = message,
            color = MockStationTheme.colors.error,
        )
    }
}

@Composable
private fun SettingsScreenContent(
    uiState: SettingsUiState.Stable,
    onThemeModeChange: (ThemeMode) -> Unit,
    onNavigationDisplayModeChange: (NavigationDisplayMode) -> Unit,
    onChooseDirectory: () -> Unit,
    onSelectConnection: (Int) -> Unit,
    onAddConnection: () -> Unit,
    onEditConnection: (Int) -> Unit,
    onDeleteConnection: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MockStationTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.large),
    ) {
        item {
            HeadlineMediumText(
                text = "Settings",
            )
        }

        item {
            AppInfoSection(
                version = uiState.appVersion,
                license = uiState.license,
                repositoryUrl = uiState.repositoryUrl,
                onRepositoryClick = { /* no-op for now */ },
            )
        }

        item {
            SettingFilterSection(
                sectionTitle = "Appearance",
                label = "Theme",
                selectedOption = uiState.themeMode,
                options = ThemeMode.entries.toTypedArray(),
                onOptionChange = onThemeModeChange,
                getDisplayName = { it.displayName },
            )
        }

        item {
            SettingFilterSection(
                sectionTitle = "Navigation Display",
                label = "Navigation Position",
                selectedOption = uiState.navigationDisplayMode,
                options = NavigationDisplayMode.entries.toTypedArray(),
                onOptionChange = onNavigationDisplayModeChange,
                getDisplayName = { it.displayName },
            )
        }

        item {
            SettingButtonSection(
                sectionTitle = "Test Case Directory",
                description = "Select the directory containing test case files",
                buttonText = "Choose Directory",
                onButtonClick = onChooseDirectory,
            ) {
                if (uiState.testCaseDirectory != null) {
                    BodyMediumMonospaceText(
                        text = uiState.testCaseDirectory,
                        modifier = Modifier.padding(
                            vertical = MockStationTheme.spacing.small,
                        ),
                    )
                } else {
                    BodyMediumText(
                        text = "Not set",
                        color = MockStationTheme.colors.onSurfaceVariant,
                        modifier = Modifier.padding(
                            vertical = MockStationTheme.spacing.small,
                        ),
                    )
                }
            }
        }

        item {
            ConnectionSettingsSection(
                connections = uiState.connections,
                selectedIndex = uiState.selectedConnectionIndex,
                canAddConnection = uiState.canAddConnection,
                maxConnections = SettingsUiState.Stable.MAX_CONNECTIONS,
                onSelectConnection = onSelectConnection,
                onEditConnection = onEditConnection,
                onDeleteConnection = onDeleteConnection,
                onAddConnection = onAddConnection,
                getName = { it.name },
                getUrl = { it.url },
                getDescription = { it.description },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsScreenStable() {
    MockStationTheme {
        SettingsBaseScreen(
            uiState = SettingsUiState.Stable(
                themeMode = ThemeMode.SYSTEM,
                navigationDisplayMode = NavigationDisplayMode.AUTO,
                testCaseDirectory = null,
                connections = listOf(
                    Connection(
                        id = "1",
                        name = "Local",
                        url = "http://localhost:8080",
                        description = "Dev server",
                    ),
                ),
                selectedConnectionIndex = 0,
                appVersion = "1.0.0",
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsScreenLoading() {
    MockStationTheme {
        SettingsBaseScreen(
            uiState = SettingsUiState.Loading,
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsScreenError() {
    MockStationTheme {
        SettingsBaseScreen(
            uiState = SettingsUiState.Error("Failed to load settings"),
        )
    }
}
