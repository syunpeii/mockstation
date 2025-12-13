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
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ConnectionDisplay
import com.github.syunpeii.mockstation.core.designsystem.component.organism.ConnectionSettingsSection
import com.github.syunpeii.mockstation.core.designsystem.resources.ComposeStringResource
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.common_license
import mockstation.composeapp.generated.resources.common_not_set
import mockstation.composeapp.generated.resources.common_repository
import mockstation.composeapp.generated.resources.common_version
import mockstation.composeapp.generated.resources.settings_connection_add_button
import mockstation.composeapp.generated.resources.settings_connection_description
import mockstation.composeapp.generated.resources.settings_connection_new_name
import mockstation.composeapp.generated.resources.settings_connection_title
import mockstation.composeapp.generated.resources.settings_navigation_label
import mockstation.composeapp.generated.resources.settings_section_about
import mockstation.composeapp.generated.resources.settings_section_appearance
import mockstation.composeapp.generated.resources.settings_section_navigation
import mockstation.composeapp.generated.resources.settings_section_testcase_dir
import mockstation.composeapp.generated.resources.settings_testcase_dir_button
import mockstation.composeapp.generated.resources.settings_testcase_dir_description
import mockstation.composeapp.generated.resources.settings_theme_label
import mockstation.composeapp.generated.resources.settings_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MockStationTheme.spacing.medium),
        contentPadding = PaddingValues(vertical = MockStationTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
    ) {
        item {
            HeadlineMediumText(
                text = stringResource(Res.string.settings_title),
            )
        }

        item {
            AppInfoSection(
                sectionTitle = stringResource(Res.string.settings_section_about),
                versionTitle = stringResource(Res.string.common_version),
                licenseTitle = stringResource(Res.string.common_license),
                repositoryTitle = stringResource(Res.string.common_repository),
                version = uiState.appVersion,
                license = uiState.license,
                repositoryUrl = uiState.repositoryUrl,
                onRepositoryClick = { /* no-op for now */ },
            )
        }

        item {
            SettingFilterSection(
                sectionTitle = stringResource(Res.string.settings_section_appearance),
                label = stringResource(Res.string.settings_theme_label),
                selectedOption = uiState.themeMode,
                options = ThemeMode.entries.toTypedArray(),
                onOptionChange = onThemeModeChange,
                getDisplayName = { it.toDisplayString() },
            )
        }

        item {
            SettingFilterSection(
                sectionTitle = stringResource(Res.string.settings_section_navigation),
                label = stringResource(Res.string.settings_navigation_label),
                selectedOption = uiState.navigationDisplayMode,
                options = NavigationDisplayMode.entries.toTypedArray(),
                onOptionChange = onNavigationDisplayModeChange,
                getDisplayName = { it.toDisplayString() },
            )
        }

        item {
            SettingButtonSection(
                sectionTitle = stringResource(Res.string.settings_section_testcase_dir),
                description = stringResource(Res.string.settings_testcase_dir_description),
                buttonText = stringResource(Res.string.settings_testcase_dir_button),
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
                        text = stringResource(Res.string.common_not_set),
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
                sectionTitle = stringResource(Res.string.settings_connection_title),
                description = ComposeStringResource(
                    resourceId = Res.string.settings_connection_description,
                    SettingsUiState.Stable.MAX_CONNECTIONS,
                ).getString(),
                addButtonText = stringResource(
                    Res.string.settings_connection_add_button,
                    uiState.connections.size,
                    SettingsUiState.Stable.MAX_CONNECTIONS,
                ),
                connections = uiState.connections.map { connection ->
                    ConnectionDisplay(
                        name = connection.nameResource.getString(),
                        url = connection.url,
                        description = connection.description,
                    )
                },
                selectedIndex = uiState.selectedConnectionIndex,
                canAddConnection = uiState.canAddConnection,
                maxConnections = SettingsUiState.Stable.MAX_CONNECTIONS,
                onSelectConnection = onSelectConnection,
                onEditConnection = onEditConnection,
                onDeleteConnection = onDeleteConnection,
                onAddConnection = onAddConnection,
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
                        url = "http://localhost:8080",
                        description = "Dev server",
                        nameResource = ComposeStringResource(
                            resourceId = Res.string.settings_connection_new_name,
                            1,
                        ),
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
