package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.SecondaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.SelectionChip
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.TextButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.input.NumberField
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun DelaySettingsDialog(
    initialSettings: DelaySettings,
    availableFiles: List<String>,
    onDismiss: () -> Unit,
    onSave: (DelaySettings) -> Unit,
    title: String,
    delayTypeLabel: String,
    delayTypeOffLabel: String,
    delayTypePresetLabel: String,
    delayTypeCustomLabel: String,
    presetDelayLabel: String,
    customDelayLabel: String,
    targetFilesLabel: String,
    selectAllLabel: String,
    clearAllLabel: String,
    enableLabel: String,
    noFilesLabel: String,
    placeholderLabel: String,
    saveLabel: String,
    cancelLabel: String,
    modifier: Modifier = Modifier,
    presetValues: List<Int> = listOf(1000, 5000, 10000, 30000, 60000),
) {
    var delayType by remember { mutableStateOf(initialSettings.type) }
    var delayMs by remember { mutableStateOf(initialSettings.delayMs) }
    var isEnabled by remember { mutableStateOf(initialSettings.isEnabled) }
    var targetFiles by remember { mutableStateOf(initialSettings.targetFiles) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MockStationTheme.typography.headlineSmall,
                color = MockStationTheme.colors.onBackground,
            )
        },
        text = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                ) {
                    Text(
                        text = delayTypeLabel,
                        style = MockStationTheme.typography.labelMedium,
                        color = MockStationTheme.colors.onSurfaceVariant,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                    ) {
                        DelayType.entries.forEach { type ->
                            SelectionChip(
                                label = when (type) {
                                    DelayType.OFF -> delayTypeOffLabel
                                    DelayType.PRESET -> delayTypePresetLabel
                                    DelayType.CUSTOM -> delayTypeCustomLabel
                                },
                                selected = delayType == type,
                                onClick = { delayType = type },
                            )
                        }
                    }
                }

                if (delayType != DelayType.OFF) {
                    HorizontalDivider()

                    if (delayType == DelayType.PRESET) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                        ) {
                            Text(
                                text = presetDelayLabel,
                                style = MockStationTheme.typography.labelMedium,
                                color = MockStationTheme.colors.onSurfaceVariant,
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                presetValues.chunked(3).forEach { rowValues ->
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                                    ) {
                                        rowValues.forEach { value ->
                                            SecondaryButton(
                                                text = value.toString(),
                                                onClick = { delayMs = value.toString() },
                                            )
                                        }
                                    }
                                }
                            }
                            if (delayMs.isNotEmpty()) {
                                Text(
                                    style = MockStationTheme.typography.bodyMedium,
                                    text = delayMs.toInt().toString(),
                                    color = MockStationTheme.colors.primary,
                                )
                            }
                        }
                    } else if (delayType == DelayType.CUSTOM) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                        ) {
                            Text(
                                text = customDelayLabel,
                                style = MockStationTheme.typography.labelMedium,
                                color = MockStationTheme.colors.onSurfaceVariant,
                            )
                            NumberField(
                                value = delayMs,
                                onValueChange = { delayMs = it },
                                placeholder = placeholderLabel,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }

                    HorizontalDivider()

                    Column(
                        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = targetFilesLabel,
                                style = MockStationTheme.typography.labelMedium,
                                color = MockStationTheme.colors.onSurfaceVariant,
                            )
                            TextButton(
                                text = if (targetFiles.isEmpty()) {
                                    selectAllLabel
                                } else {
                                    clearAllLabel
                                },
                                onClick = {
                                    targetFiles = if (targetFiles.isEmpty()) {
                                        availableFiles.toSet()
                                    } else {
                                        emptySet()
                                    }
                                },
                            )
                        }

                        if (availableFiles.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 150.dp),
                                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                            ) {
                                items(availableFiles) { file ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Checkbox(
                                            checked = targetFiles.contains(file),
                                            onCheckedChange = { checked ->
                                                targetFiles = if (checked) {
                                                    targetFiles + file
                                                } else {
                                                    targetFiles - file
                                                }
                                            },
                                        )
                                        Text(
                                            text = file,
                                            style = MockStationTheme.typography.bodyMedium,
                                            color = MockStationTheme.colors.onSurface,
                                        )
                                    }
                                }
                            }
                        } else {
                            Text(
                                style = MockStationTheme.typography.bodyMedium,
                                text = noFilesLabel,
                                color = MockStationTheme.colors.onSurface,
                            )
                        }
                    }

                    HorizontalDivider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = enableLabel,
                            style = MockStationTheme.typography.labelMedium,
                            color = MockStationTheme.colors.onSurfaceVariant,
                        )
                        Switch(
                            checked = isEnabled,
                            onCheckedChange = { isEnabled = it },
                        )
                    }
                }
            }
        },
        confirmButton = {
            PrimaryButton(
                text = saveLabel,
                onClick = {
                    onSave(
                        DelaySettings(
                            type = delayType,
                            delayMs = delayMs,
                            isEnabled = isEnabled,
                            targetFiles = targetFiles,
                        ),
                    )
                },
                enabled = when (delayType) {
                    DelayType.OFF -> true
                    DelayType.PRESET, DelayType.CUSTOM -> delayMs.isNotEmpty()
                },
            )
        },
        dismissButton = {
            TextButton(
                text = cancelLabel,
                onClick = onDismiss,
            )
        },
        modifier = modifier,
        containerColor = MockStationTheme.colors.surface,
    )
}

data class DelaySettings(
    val type: DelayType,
    val delayMs: String,
    val isEnabled: Boolean,
    val targetFiles: Set<String>,
)

enum class DelayType {
    OFF,
    PRESET,
    CUSTOM,
}

@Preview
@Composable
private fun PreviewDelaySettingsDialogOff() {
    MockStationTheme {
        PreviewBox {
            DelaySettingsDialog(
                initialSettings = DelaySettings(
                    type = DelayType.OFF,
                    delayMs = "",
                    isEnabled = false,
                    targetFiles = emptySet(),
                ),
                availableFiles = listOf("api/user.json", "api/device.json", "api/test.json"),
                onDismiss = {},
                onSave = {},
                title = "Delay Settings",
                delayTypeLabel = "Delay Type",
                delayTypeOffLabel = "Off",
                delayTypePresetLabel = "Preset",
                delayTypeCustomLabel = "Custom",
                presetDelayLabel = "Preset Delay",
                customDelayLabel = "Custom Delay",
                targetFilesLabel = "Target Files",
                selectAllLabel = "Select All",
                clearAllLabel = "Clear All",
                enableLabel = "Enable",
                noFilesLabel = "No files available",
                placeholderLabel = "Enter custom delay",
                saveLabel = "Save",
                cancelLabel = "Cancel",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDelaySettingsDialogPreset() {
    MockStationTheme {
        PreviewBox {
            DelaySettingsDialog(
                initialSettings = DelaySettings(
                    type = DelayType.PRESET,
                    delayMs = "5000",
                    isEnabled = true,
                    targetFiles = emptySet(),
                ),
                availableFiles = listOf("api/user.json", "api/device.json", "api/test.json"),
                onDismiss = {},
                onSave = {},
                title = "Delay Settings",
                delayTypeLabel = "Delay Type",
                delayTypeOffLabel = "Off",
                delayTypePresetLabel = "Preset",
                delayTypeCustomLabel = "Custom",
                presetDelayLabel = "Preset Delay",
                customDelayLabel = "Custom Delay",
                targetFilesLabel = "Target Files",
                selectAllLabel = "Select All",
                clearAllLabel = "Clear All",
                enableLabel = "Enable",
                noFilesLabel = "No files available",
                placeholderLabel = "Enter custom delay",
                saveLabel = "Save",
                cancelLabel = "Cancel",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDelaySettingsDialogCustom() {
    MockStationTheme {
        PreviewBox {
            DelaySettingsDialog(
                initialSettings = DelaySettings(
                    type = DelayType.CUSTOM,
                    delayMs = "30000",
                    isEnabled = true,
                    targetFiles = setOf("api/user.json", "api/device.json"),
                ),
                availableFiles = listOf(
                    "api/user.json",
                    "api/device.json",
                    "api/test.json",
                    "api/settings.json",
                    "api/config.json",
                ),
                onDismiss = {},
                onSave = {},
                title = "Delay Settings",
                delayTypeLabel = "Delay Type",
                delayTypeOffLabel = "Off",
                delayTypePresetLabel = "Preset",
                delayTypeCustomLabel = "Custom",
                presetDelayLabel = "Preset Delay",
                customDelayLabel = "Custom Delay",
                targetFilesLabel = "Target Files",
                selectAllLabel = "Select All",
                clearAllLabel = "Clear All",
                enableLabel = "Enable",
                noFilesLabel = "No files available",
                placeholderLabel = "Enter custom delay",
                saveLabel = "Save",
                cancelLabel = "Cancel",
            )
        }
    }
}
