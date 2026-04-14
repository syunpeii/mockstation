package com.github.syunpeii.mockstation.app.ui.settings

import androidx.compose.runtime.Composable
import com.github.syunpeii.mockstation.core.designsystem.resources.ComposeStringResource
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.settings_nav_mode_auto
import mockstation.composeapp.generated.resources.settings_nav_mode_bottom
import mockstation.composeapp.generated.resources.settings_nav_mode_left
import mockstation.composeapp.generated.resources.settings_theme_mode_dark
import mockstation.composeapp.generated.resources.settings_theme_mode_light
import mockstation.composeapp.generated.resources.settings_theme_mode_system
import org.jetbrains.compose.resources.stringResource

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Stable(
        val themeMode: ThemeMode,
        val navigationDisplayMode: NavigationDisplayMode,
        val testCaseDirectory: String?,
        val connections: List<Connection>,
        val selectedConnectionIndex: Int,
        val appVersion: String,
        val isTestingConnection: Boolean,
        val connectionTestSuccess: Boolean?,
        val connectionTestError: String?,
        val serverTestCaseDirectory: String?,
        val serverResFileFormat: String?,
        val isLoadingServerSettings: Boolean,
    ) : SettingsUiState {
        val repositoryUrl: String = REPOSITORY_URL
        val license: String = LICENSE
        val canAddConnection: Boolean
            get() = connections.size < MAX_CONNECTIONS

        companion object {
            const val MAX_CONNECTIONS = 10
        }
    }

    data class Error(
        val message: String,
    ) : SettingsUiState

    companion object {
        private const val REPOSITORY_URL = "https://github.com/syunpeii/mockstation"
        private const val LICENSE = "Apache License"
    }
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
    ;

    @Composable
    fun toDisplayString(): String = when (this) {
        LIGHT -> stringResource(Res.string.settings_theme_mode_light)
        DARK -> stringResource(Res.string.settings_theme_mode_dark)
        SYSTEM -> stringResource(Res.string.settings_theme_mode_system)
    }
}

enum class NavigationDisplayMode {
    LEFT,
    BOTTOM,
    AUTO,
    ;

    @Composable
    fun toDisplayString(): String = when (this) {
        LEFT -> stringResource(Res.string.settings_nav_mode_left)
        BOTTOM -> stringResource(Res.string.settings_nav_mode_bottom)
        AUTO -> stringResource(Res.string.settings_nav_mode_auto)
    }
}

data class Connection(
    val url: String,
    val description: String,
    val nameResource: ComposeStringResource,
)
