package com.github.syunpeii.mockstation.app.ui.testcasesearch

import com.github.syunpeii.mockstation.app.ui.testcasesearch.model.TestCaseDisplay

sealed interface TestCaseSearchUiState {
    data object Loading : TestCaseSearchUiState

    data class Stable(
        val testCases: List<TestCaseDisplay>,
        val searchTags: List<String>,
        val selectedTestCaseId: String?,
        val currentInput: String,
    ) : TestCaseSearchUiState

    data class Error(
        val message: String,
    ) : TestCaseSearchUiState
}
