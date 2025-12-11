package com.github.syunpeii.mockstation.core.designsystem.component.molecule

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
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumMonospaceText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SettingButtonSection(
    sectionTitle: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        SettingSectionHeader(title = sectionTitle)

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
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
            ) {
                BodyMediumText(
                    text = description,
                    color = MockStationTheme.colors.onSurfaceVariant,
                )

                content()

                PrimaryButton(
                    text = buttonText,
                    onClick = onButtonClick,
                    enabled = buttonEnabled,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingButtonSectionWithValue() {
    MockStationTheme {
        PreviewColumn {
            SettingButtonSection(
                sectionTitle = "File Path",
                description = "Select a file path for configuration",
                buttonText = "Choose File",
                onButtonClick = {},
            ) {
                BodyMediumMonospaceText(
                    text = "/Users/username/projects/mockstation/config.json",
                    modifier = Modifier.padding(
                        vertical = MockStationTheme.spacing.small,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingButtonSectionWithoutValue() {
    MockStationTheme {
        PreviewColumn {
            SettingButtonSection(
                sectionTitle = "Directory Path",
                description = "Select the directory for data storage",
                buttonText = "Choose Directory",
                onButtonClick = {},
            ) {
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
}

@Preview
@Composable
private fun PreviewSettingButtonSectionDisabled() {
    MockStationTheme {
        PreviewColumn {
            SettingButtonSection(
                sectionTitle = "Export Path",
                description = "Configure export destination",
                buttonText = "Select Path",
                onButtonClick = {},
                buttonEnabled = true,
            ) {
                BodyMediumText(
                    text = "Available",
                    color = MockStationTheme.colors.onSurfaceVariant,
                    modifier = Modifier.padding(
                        vertical = MockStationTheme.spacing.small,
                    ),
                )
            }
            SettingButtonSection(
                sectionTitle = "Export Path",
                description = "Configure export destination",
                buttonText = "Select Path",
                onButtonClick = {},
                buttonEnabled = false,
            ) {
                BodyMediumText(
                    text = "Not available",
                    color = MockStationTheme.colors.onSurfaceVariant,
                    modifier = Modifier.padding(
                        vertical = MockStationTheme.spacing.small,
                    ),
                )
            }
        }
    }
}
