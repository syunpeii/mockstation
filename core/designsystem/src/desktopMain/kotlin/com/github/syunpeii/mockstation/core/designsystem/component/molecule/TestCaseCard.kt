package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.SelectionChip
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.TextButton
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun TestCaseCard(
    testCaseId: String,
    description: String,
    tags: List<String>,
    isSelected: Boolean,
    onClick: () -> Unit,
    onSwitchClick: () -> Unit,
    modifier: Modifier = Modifier,
    testCaseIdLabel: String = "ID",
    switchButtonLabel: String = "Switch",
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(MockStationTheme.shapes.medium)
            .clickable { onClick() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MockStationTheme.colors.primary else MockStationTheme.colors.outline,
                shape = MockStationTheme.shapes.medium,
            ),
        colors = CardDefaults.cardColors(MockStationTheme.colors.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MockStationTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$testCaseIdLabel: $testCaseId",
                    style = MockStationTheme.typography.labelMedium,
                    color = if (isSelected) MockStationTheme.colors.onPrimaryContainer else MockStationTheme.colors.onSurfaceVariant,
                )
            }

            Text(
                text = description,
                style = MockStationTheme.typography.bodyMedium,
                color = if (isSelected) MockStationTheme.colors.onPrimaryContainer else MockStationTheme.colors.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            if (tags.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                    verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.extraSmall),
                ) {
                    tags.take(3).forEach { tag ->
                        SelectionChip(
                            label = tag,
                            selected = false,
                            onClick = {},
                        )
                    }
                    if (tags.size > 3) {
                        SelectionChip(
                            label = "+${tags.size - 3}",
                            selected = false,
                            onClick = {},
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    text = switchButtonLabel,
                    onClick = onSwitchClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTestCaseCard() {
    MockStationTheme {
        PreviewColumn {
            TestCaseCard(
                testCaseId = "TC-001",
                description = "User login with valid credentials",
                tags = listOf("login", "authentication", "user"),
                isSelected = false,
                onClick = {},
                onSwitchClick = {},
                testCaseIdLabel = "Test Case ID",
                switchButtonLabel = "切替",
            )

            TestCaseCard(
                testCaseId = "TC-002",
                description = "Device registration flow with complex multi-step process",
                tags = listOf("device", "registration", "api", "backend", "integration"),
                isSelected = true,
                onClick = {},
                onSwitchClick = {},
                testCaseIdLabel = "ID",
                switchButtonLabel = "切替",
            )
        }
    }
}
