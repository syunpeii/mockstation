package com.github.syunpeii.mockstation.core.designsystem.component.organism

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.SelectionChip
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.HeadlineSmallText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.LabelText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun TestCaseDetailPanel(
    testCaseId: String,
    description: String,
    tags: List<String>,
    content: String,
    modifier: Modifier = Modifier,
    onSwitchClick: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    testCaseIdLabel: String = "Test Case ID",
    descriptionLabel: String = "Description",
    tagsLabel: String = "Tags",
    contentLabel: String = "Content",
    switchButtonLabel: String = "Switch",
    closeLabel: String = "Close",
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MockStationTheme.colors.outline,
                shape = MockStationTheme.shapes.medium,
            ),
        colors = CardDefaults.cardColors(
            containerColor = MockStationTheme.colors.surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MockStationTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HeadlineSmallText(
                    text = testCaseId,
                    color = MockStationTheme.colors.onSurface,
                )

                if (onClose != null) {
                    AppIconButton(
                        imageVector = Icons.Filled.Close,
                        contentDescription = closeLabel,
                        onClick = onClose,
                    )
                }
            }

            HorizontalDivider()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
            ) {
                DetailSection(
                    label = testCaseIdLabel,
                    content = {
                        SelectionContainer {
                            BodyMediumText(
                                text = testCaseId,
                                color = MockStationTheme.colors.onSurface,
                            )
                        }
                    },
                )

                DetailSection(
                    label = descriptionLabel,
                    content = {
                        SelectionContainer {
                            BodyMediumText(
                                text = description,
                                color = MockStationTheme.colors.onSurface,
                            )
                        }
                    },
                )

                if (tags.isNotEmpty()) {
                    DetailSection(
                        label = tagsLabel,
                        content = {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
                            ) {
                                tags.forEach { tag ->
                                    SelectionChip(
                                        label = tag,
                                        selected = false,
                                        onClick = {},
                                    )
                                }
                            }
                        },
                    )
                }

                DetailSection(
                    label = contentLabel,
                    content = {
                        SelectionContainer {
                            BodyMediumText(
                                text = content,
                                color = MockStationTheme.colors.onSurface,
                            )
                        }
                    },
                )
            }

            if (onSwitchClick != null) {
                PrimaryButton(
                    text = switchButtonLabel,
                    onClick = onSwitchClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun DetailSection(
    label: String,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
    ) {
        LabelText(
            text = label,
            color = MockStationTheme.colors.onSurfaceVariant,
        )
        content()
    }
}

@Preview
@Composable
private fun PreviewTestCaseDetailPanel() {
    MockStationTheme {
        PreviewBox {
            TestCaseDetailPanel(
                testCaseId = "TC-001",
                description = "User login with valid credentials",
                tags = listOf("login", "authentication", "user"),
                content = """
                    # Test Case: User Login

                    ## Objective
                    Verify that users can successfully log in with valid credentials.

                    ## Test Steps
                    1. Navigate to the login page
                    2. Enter valid username
                    3. Enter valid password
                    4. Click the login button

                    ## Expected Result
                    - User should be authenticated
                    - User should be redirected to dashboard
                    - Welcome message should be displayed
                """.trimIndent(),
                onSwitchClick = {},
                onClose = {},
                testCaseIdLabel = "Test Case ID",
                descriptionLabel = "Description",
                tagsLabel = "Tags",
                contentLabel = "Content",
                switchButtonLabel = "Switch to this test case",
                closeLabel = "Close",
            )
        }
    }
}
