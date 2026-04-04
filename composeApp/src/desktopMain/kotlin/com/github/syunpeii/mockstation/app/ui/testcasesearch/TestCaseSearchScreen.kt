package com.github.syunpeii.mockstation.app.ui.testcasesearch

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.app.navigation.WindowSizeClass
import com.github.syunpeii.mockstation.app.ui.testcasesearch.model.TestCaseDisplay
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyLargeText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.HeadlineMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.SearchTagInput
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.TagChipGroup
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.TestCaseCard
import com.github.syunpeii.mockstation.core.designsystem.component.molecule.TestCaseDetailBottomSheet
import com.github.syunpeii.mockstation.core.designsystem.component.organism.TestCaseDetailPanel
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.testcase_search_adjust_tags
import mockstation.composeapp.generated.resources.testcase_search_clear_all_tags
import mockstation.composeapp.generated.resources.testcase_search_close
import mockstation.composeapp.generated.resources.testcase_search_content
import mockstation.composeapp.generated.resources.testcase_search_description
import mockstation.composeapp.generated.resources.testcase_search_error
import mockstation.composeapp.generated.resources.testcase_search_no_results
import mockstation.composeapp.generated.resources.testcase_search_refresh
import mockstation.composeapp.generated.resources.testcase_search_search_hint
import mockstation.composeapp.generated.resources.testcase_search_switch_testcase
import mockstation.composeapp.generated.resources.testcase_search_tags
import mockstation.composeapp.generated.resources.testcase_search_tags_label
import mockstation.composeapp.generated.resources.testcase_search_testcase_id
import mockstation.composeapp.generated.resources.testcase_search_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TestCaseSearchScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = WindowSizeClass.Expanded,
    viewModel: TestCaseSearchViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    TestCaseSearchBaseScreen(
        uiState = uiState,
        windowSizeClass = windowSizeClass,
        onSearchInputChange = viewModel::onSearchInputChange,
        onAddTag = viewModel::onAddTag,
        onRemoveTag = viewModel::onRemoveTag,
        onClearAllTags = viewModel::onClearAllTags,
        onSelectTestCase = viewModel::onSelectTestCase,
        onDeselectTestCase = viewModel::onDeselectTestCase,
        onRefresh = viewModel::onRefresh,
        onSwitchTestCase = viewModel::onSwitchTestCase,
        modifier = modifier,
    )
}

@Composable
private fun TestCaseSearchBaseScreen(
    uiState: TestCaseSearchUiState,
    windowSizeClass: WindowSizeClass,
    onSearchInputChange: (String) -> Unit,
    onAddTag: () -> Unit,
    onRemoveTag: (String) -> Unit,
    onClearAllTags: () -> Unit,
    onSelectTestCase: (String) -> Unit,
    onDeselectTestCase: () -> Unit,
    onRefresh: () -> Unit,
    onSwitchTestCase: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MockStationTheme.colors.background),
    ) {
        when (uiState) {
            is TestCaseSearchUiState.Loading -> TestCaseSearchScreenLoading()
            is TestCaseSearchUiState.Stable -> TestCaseSearchContent(
                uiState = uiState,
                windowSizeClass = windowSizeClass,
                onSearchInputChange = onSearchInputChange,
                onAddTag = onAddTag,
                onRemoveTag = onRemoveTag,
                onClearAllTags = onClearAllTags,
                onSelectTestCase = onSelectTestCase,
                onDeselectTestCase = onDeselectTestCase,
                onRefresh = onRefresh,
                onSwitchTestCase = onSwitchTestCase,
            )

            is TestCaseSearchUiState.Error -> TestCaseSearchScreenError(
                message = uiState.message,
            )
        }
    }
}

@Composable
private fun TestCaseSearchContent(
    uiState: TestCaseSearchUiState.Stable,
    windowSizeClass: WindowSizeClass,
    onSearchInputChange: (String) -> Unit,
    onAddTag: () -> Unit,
    onRemoveTag: (String) -> Unit,
    onClearAllTags: () -> Unit,
    onSelectTestCase: (String) -> Unit,
    onDeselectTestCase: () -> Unit,
    onRefresh: () -> Unit,
    onSwitchTestCase: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        HeadlineMediumText(
            text = stringResource(Res.string.testcase_search_title),
            modifier = Modifier.padding(MockStationTheme.spacing.medium),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MockStationTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SearchTagInput(
                currentInput = uiState.currentInput,
                onInputChange = onSearchInputChange,
                onAddTag = onAddTag,
                placeholder = stringResource(Res.string.testcase_search_search_hint),
                modifier = Modifier.weight(1f),
            )

            AppIconButton(
                imageVector = Icons.Filled.Refresh,
                contentDescription = stringResource(Res.string.testcase_search_refresh),
                onClick = onRefresh,
            )
        }

        if (uiState.searchTags.isNotEmpty()) {
            TagChipGroup(
                tags = uiState.searchTags,
                onTagRemove = onRemoveTag,
                label = stringResource(Res.string.testcase_search_tags_label),
                onClearAll = onClearAllTags,
                clearAllLabel = stringResource(Res.string.testcase_search_clear_all_tags),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MockStationTheme.spacing.medium),
            )
        }

        AdaptiveLayout(
            uiState = uiState,
            windowSizeClass = windowSizeClass,
            onSelectTestCase = onSelectTestCase,
            onDeselectTestCase = onDeselectTestCase,
            onSwitchTestCase = onSwitchTestCase,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun AdaptiveLayout(
    uiState: TestCaseSearchUiState.Stable,
    windowSizeClass: WindowSizeClass,
    onSelectTestCase: (String) -> Unit,
    onDeselectTestCase: () -> Unit,
    onSwitchTestCase: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isCompact = windowSizeClass == WindowSizeClass.Compact

    when {
        uiState.selectedTestCaseId == null -> {
            TestCaseList(
                testCases = uiState.testCases,
                selectedTestCaseId = null,
                onSelectTestCase = onSelectTestCase,
                onSwitchTestCase = onSwitchTestCase,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MockStationTheme.spacing.medium),
            )
        }

        isCompact -> {
            TestCaseList(
                testCases = uiState.testCases,
                selectedTestCaseId = uiState.selectedTestCaseId,
                onSelectTestCase = onSelectTestCase,
                onSwitchTestCase = onSwitchTestCase,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MockStationTheme.spacing.medium),
            )

            val selectedTestCase = uiState.testCases.find { it.id == uiState.selectedTestCaseId }
            selectedTestCase?.let {
                TestCaseDetailBottomSheet(
                    testCaseId = it.id,
                    description = it.description,
                    tags = it.tags,
                    content = it.content,
                    onDismiss = onDeselectTestCase,
                    onSwitchClick = { onSwitchTestCase(it.id) },
                    testCaseIdLabel = stringResource(Res.string.testcase_search_testcase_id),
                    descriptionLabel = stringResource(Res.string.testcase_search_description),
                    tagsLabel = stringResource(Res.string.testcase_search_tags),
                    contentLabel = stringResource(Res.string.testcase_search_content),
                    switchButtonLabel = stringResource(Res.string.testcase_search_switch_testcase),
                    closeLabel = stringResource(Res.string.testcase_search_close),
                )
            }
        }

        else -> {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = MockStationTheme.spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.medium),
            ) {
                Column(
                    modifier = Modifier.weight(0.4f),
                ) {
                    TestCaseList(
                        testCases = uiState.testCases,
                        selectedTestCaseId = uiState.selectedTestCaseId,
                        onSelectTestCase = onSelectTestCase,
                        onSwitchTestCase = onSwitchTestCase,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(vertical = MockStationTheme.spacing.medium),
                ) {
                    val selectedTestCase = uiState.testCases.find { it.id == uiState.selectedTestCaseId }
                    selectedTestCase?.let {
                        TestCaseDetailPanel(
                            testCaseId = it.id,
                            description = it.description,
                            tags = it.tags,
                            content = it.content,
                            onClose = onDeselectTestCase,
                            testCaseIdLabel = stringResource(Res.string.testcase_search_testcase_id),
                            descriptionLabel = stringResource(Res.string.testcase_search_description),
                            tagsLabel = stringResource(Res.string.testcase_search_tags),
                            contentLabel = stringResource(Res.string.testcase_search_content),
                            closeLabel = stringResource(Res.string.testcase_search_close),
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TestCaseList(
    testCases: List<TestCaseDisplay>,
    selectedTestCaseId: String?,
    onSelectTestCase: (String) -> Unit,
    onSwitchTestCase: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (testCases.isEmpty()) {
        EmptyState(
            message = stringResource(Res.string.testcase_search_no_results),
            hint = stringResource(Res.string.testcase_search_adjust_tags),
            modifier = modifier,
        )
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(vertical = MockStationTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
        ) {
            items(
                items = testCases,
                key = { it.id },
            ) { testCase ->
                TestCaseCard(
                    testCaseId = testCase.id,
                    description = testCase.description,
                    tags = testCase.tags,
                    isSelected = testCase.id == selectedTestCaseId,
                    onClick = { onSelectTestCase(testCase.id) },
                    onSwitchClick = { onSwitchTestCase(testCase.id) },
                    testCaseIdLabel = stringResource(Res.string.testcase_search_testcase_id),
                    switchButtonLabel = stringResource(Res.string.testcase_search_switch_testcase),
                )
            }
        }
    }
}

@Composable
private fun EmptyState(
    message: String,
    hint: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MockStationTheme.spacing.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BodyLargeText(
            text = message,
            color = MockStationTheme.colors.onBackground,
        )
        BodyMediumText(
            text = hint,
            color = MockStationTheme.colors.onBackground,
            modifier = Modifier.padding(top = MockStationTheme.spacing.small),
        )
    }
}

@Composable
private fun TestCaseSearchScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = MockStationTheme.colors.primary)
    }
}

@Composable
private fun TestCaseSearchScreenError(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(MockStationTheme.spacing.extraLarge),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BodyLargeText(
                text = stringResource(Res.string.testcase_search_error),
                color = MockStationTheme.colors.error,
            )
            BodyMediumText(
                text = message,
                modifier = Modifier.padding(top = MockStationTheme.spacing.small),
                color = MockStationTheme.colors.onBackground,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTestCaseSearchScreenStableExpanded() {
    MockStationTheme {
        TestCaseSearchBaseScreen(
            uiState = TestCaseSearchUiState.Stable(
                testCases = listOf(
                    TestCaseDisplay(
                        id = "TC-001",
                        description = "User login with valid credentials",
                        tags = listOf("login", "authentication", "user"),
                        content = "# Test Case\n\nTest content here...",
                    ),
                    TestCaseDisplay(
                        id = "TC-002",
                        description = "Device registration flow",
                        tags = listOf("device", "registration"),
                        content = "# Device Registration\n\nSteps...",
                    ),
                ),
                searchTags = listOf("login"),
                selectedTestCaseId = "TC-001",
                currentInput = "",
            ),
            windowSizeClass = WindowSizeClass.Expanded,
            onSearchInputChange = {},
            onAddTag = {},
            onRemoveTag = {},
            onClearAllTags = {},
            onSelectTestCase = {},
            onDeselectTestCase = {},
            onRefresh = {},
            onSwitchTestCase = {},
        )
    }
}

@Preview
@Composable
private fun PreviewTestCaseSearchScreenStableCompact() {
    MockStationTheme {
        TestCaseSearchBaseScreen(
            uiState = TestCaseSearchUiState.Stable(
                testCases = listOf(
                    TestCaseDisplay(
                        id = "TC-001",
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
                    ),
                    TestCaseDisplay(
                        id = "TC-002",
                        description = "Device registration flow",
                        tags = listOf("device", "registration"),
                        content = "# Device Registration\n\nSteps...",
                    ),
                ),
                searchTags = listOf("login"),
                selectedTestCaseId = "TC-001",
                currentInput = "",
            ),
            windowSizeClass = WindowSizeClass.Compact,
            onSearchInputChange = {},
            onAddTag = {},
            onRemoveTag = {},
            onClearAllTags = {},
            onSelectTestCase = {},
            onDeselectTestCase = {},
            onRefresh = {},
            onSwitchTestCase = {},
        )
    }
}
