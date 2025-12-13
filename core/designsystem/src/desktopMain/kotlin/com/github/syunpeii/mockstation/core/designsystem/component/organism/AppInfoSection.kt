package com.github.syunpeii.mockstation.core.designsystem.component.organism

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyLargeText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.ExternalLinkRow
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SettingSectionHeader
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun AppInfoSection(
    sectionTitle: String,
    versionTitle: String,
    licenseTitle: String,
    repositoryTitle: String,
    version: String,
    license: String,
    repositoryUrl: String,
    onRepositoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SettingSectionHeader(
            title = sectionTitle,
            showDivider = false,
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
                InfoRow(
                    label = versionTitle,
                    value = version,
                )
                InfoRow(
                    label = licenseTitle,
                    value = license,
                )
                ExternalLinkRow(
                    label = repositoryTitle,
                    value = repositoryUrl,
                    onLinkClick = onRepositoryClick,
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BodyLargeText(
            text = label,
        )
        BodyMediumText(
            text = value,
            color = MockStationTheme.colors.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
private fun PreviewAppInfoSection() {
    MockStationTheme {
        PreviewColumn {
            AppInfoSection(
                sectionTitle = "App Information",
                versionTitle = "Version",
                licenseTitle = "License",
                repositoryTitle = "Repository",
                version = "1.0.0",
                license = "MIT License",
                repositoryUrl = "https://github.com/syunpeii/mockstation",
                onRepositoryClick = {},
            )
        }
    }
}
