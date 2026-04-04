package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.SelectionChip
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun <T : Enum<T>> SettingFilterSection(
    sectionTitle: String,
    label: String,
    selectedOption: T,
    options: Array<T>,
    onOptionChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    getDisplayName: @Composable (T) -> String = { it.name.lowercase().replaceFirstChar { char -> char.uppercase() } },
) {
    Column(
        modifier = modifier,
    ) {
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
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                Text(
                    text = label,
                    style = MockStationTheme.typography.bodyLarge,
                    color = MockStationTheme.colors.onSurface,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                ) {
                    options.forEach { option ->
                        SelectionChip(
                            label = getDisplayName(option),
                            selected = option == selectedOption,
                            onClick = { onOptionChange(option) },
                        )
                    }
                }
            }
        }
    }
}

private enum class PreviewOption {
    OPTION_A,
    OPTION_B,
    OPTION_C,
}

@Preview
@Composable
private fun PreviewSettingFilterSection() {
    MockStationTheme {
        PreviewColumn {
            SettingFilterSection(
                sectionTitle = "Sample Section",
                label = "Choose Option",
                selectedOption = PreviewOption.OPTION_A,
                options = PreviewOption.entries.toTypedArray(),
                onOptionChange = {},
            )

            SettingFilterSection(
                sectionTitle = "Another Section",
                label = "Select Mode",
                selectedOption = PreviewOption.OPTION_B,
                options = PreviewOption.entries.toTypedArray(),
                onOptionChange = {},
            )
        }
    }
}
