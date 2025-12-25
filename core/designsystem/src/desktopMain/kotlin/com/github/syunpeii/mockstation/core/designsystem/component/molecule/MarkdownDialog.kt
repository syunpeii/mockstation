package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.TextButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodySmallMonospaceText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.HeadlineSmallText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun MarkdownDialog(
    title: String,
    markdownContent: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    closeLabel: String,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            HeadlineSmallText(text = title)
        },
        text = {
            val scrollState = rememberScrollState()
            SelectionContainer {
                BodySmallMonospaceText(
                    text = markdownContent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .verticalScroll(scrollState)
                        .padding(vertical = MockStationTheme.spacing.small),
                    color = MockStationTheme.colors.onSurface,
                )
            }
        },
        confirmButton = {
            TextButton(
                text = closeLabel,
                onClick = onDismiss,
            )
        },
        modifier = modifier,
        containerColor = MockStationTheme.colors.surface,
    )
}

@Preview
@Composable
private fun PreviewMarkdownDialog() {
    MockStationTheme {
        PreviewBox {
            MarkdownDialog(
                title = "Test Case: test-case-abc",
                markdownContent = """
                    # Test Case ABC

                    ## Overview
                    This is a test case for device management.

                    ## Steps
                    1. First step
                    2. Second step
                    3. Third step

                    ## Expected Results
                    - Result 1
                    - Result 2

                    ## Code Example
                    ```kotlin
                    fun example() {
                        println("Hello, World!")
                    }
                    ```
                """.trimIndent(),
                onDismiss = {},
                closeLabel = "Close",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMarkdownDialogSimple() {
    MockStationTheme {
        PreviewBox {
            MarkdownDialog(
                title = "Simple README",
                markdownContent = """
                    This is a simple test case.

                    Just testing the basic functionality.
                """.trimIndent(),
                onDismiss = {},
                closeLabel = "Close",
            )
        }
    }
}
