package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.organism.TestCaseDetailPanel
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestCaseDetailBottomSheet(
    testCaseId: String,
    description: String,
    tags: List<String>,
    content: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onSwitchClick: (() -> Unit)? = null,
    testCaseIdLabel: String = "Test Case ID",
    descriptionLabel: String = "Description",
    tagsLabel: String = "Tags",
    contentLabel: String = "Content",
    switchButtonLabel: String = "Switch",
    closeLabel: String = "Close",
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()

    val handleDismiss: () -> Unit = {
        scope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = handleDismiss,
        sheetState = sheetState,
        modifier = modifier,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MockStationTheme.spacing.small)
                    .pointerHoverIcon(PointerIcon.Default),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .background(
                            color = MockStationTheme.colors.onSurfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(2.dp),
                        ),
                )
            }
        },
    ) {
        TestCaseDetailPanel(
            testCaseId = testCaseId,
            description = description,
            tags = tags,
            content = content,
            onClose = handleDismiss,
            onSwitchClick = onSwitchClick,
            testCaseIdLabel = testCaseIdLabel,
            descriptionLabel = descriptionLabel,
            tagsLabel = tagsLabel,
            contentLabel = contentLabel,
            switchButtonLabel = switchButtonLabel,
            closeLabel = closeLabel,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(bottom = MockStationTheme.spacing.medium),
        )
    }
}

@Preview
@Composable
private fun PreviewTestCaseDetailBottomSheet() {
    MockStationTheme {
        PreviewBox {
            TestCaseDetailBottomSheet(
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
                onDismiss = {},
                onSwitchClick = {},
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

@Preview
@Composable
private fun PreviewTestCaseDetailBottomSheetWithoutSwitch() {
    MockStationTheme {
        PreviewBox {
            TestCaseDetailBottomSheet(
                testCaseId = "TC-002",
                description = "Device registration flow",
                tags = listOf("device", "registration"),
                content = """
                    # Device Registration

                    ## Steps
                    1. Start registration process
                    2. Enter device information
                    3. Confirm registration
                """.trimIndent(),
                onDismiss = {},
                onSwitchClick = null,
                testCaseIdLabel = "Test Case ID",
                descriptionLabel = "Description",
                tagsLabel = "Tags",
                contentLabel = "Content",
                closeLabel = "Close",
            )
        }
    }
}
