package com.github.syunpeii.mockstation.app.ui.settings

import kotlinx.serialization.Serializable

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Stable(
        val themeMode: ThemeMode,
        val navigationDisplayMode: NavigationDisplayMode,
        val testCaseDirectory: String?,
        val connections: List<Connection>,
        val selectedConnectionIndex: Int,
        val appVersion: String,
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

enum class ThemeMode(
    val displayName: String,
) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM("System"),
}

enum class NavigationDisplayMode(
    val displayName: String,
) {
    LEFT("Left"),
    BOTTOM("Bottom"),
    AUTO("Auto"),
}

@Serializable
data class Connection(
    val id: String,
    val name: String,
    val url: String,
    val description: String,
)
