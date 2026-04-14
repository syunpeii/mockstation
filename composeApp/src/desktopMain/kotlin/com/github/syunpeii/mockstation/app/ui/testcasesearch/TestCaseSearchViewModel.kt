package com.github.syunpeii.mockstation.app.ui.testcasesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.syunpeii.mockstation.app.ui.testcasesearch.model.TestCaseDisplay
import com.github.syunpeii.mockstation.core.data.repository.TestCaseRepository
import com.github.syunpeii.mockstation.core.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestCaseSearchViewModel(
    private val testCaseRepository: TestCaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<TestCaseSearchUiState>(TestCaseSearchUiState.Loading)
    val uiState: StateFlow<TestCaseSearchUiState> = _uiState.asStateFlow()
    private var allTestCases: List<TestCaseDisplay> = emptyList()

    init {
        loadInitialData()
    }

    fun onSearchInputChange(text: String) {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val filteredTestCases = filterTestCases(currentState.searchTags, text)
            _uiState.value = currentState.copy(
                currentInput = text,
                testCases = filteredTestCases,
            )
        }
    }

    fun onAddTag() {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val newTag = currentState.currentInput.trim()
            if (newTag.isNotEmpty()) {
                onAddTag(newTag)
            }
        }
    }

    fun onRemoveTag(tag: String) {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val newTags = currentState.searchTags - tag
            val filteredTestCases = filterTestCases(newTags, currentState.currentInput)
            _uiState.value = currentState.copy(
                searchTags = newTags,
                testCases = filteredTestCases,
            )
        }
    }

    fun onClearAllTags() {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val filteredTestCases = filterTestCases(emptyList(), currentState.currentInput)
            _uiState.value = currentState.copy(
                searchTags = emptyList(),
                testCases = filteredTestCases,
            )
        }
    }

    fun onSelectTestCase(id: String) {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            if (currentState.selectedTestCaseId == id) {
                _uiState.value = currentState.copy(selectedTestCaseId = null)
            } else {
                _uiState.value = currentState.copy(selectedTestCaseId = id)
            }
        }
    }

    fun onDeselectTestCase() {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            _uiState.value = currentState.copy(selectedTestCaseId = null)
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _uiState.value = TestCaseSearchUiState.Loading
            loadTestCases()
        }
    }

    fun onSwitchTestCase(id: String) {
        println("Switching to test case: $id")
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            loadTestCases()
        }
    }

    private suspend fun loadTestCases() {
        val result = testCaseRepository.getTestCases()
        if (result.isSuccess) {
            val testCases = (result as Result.Success).data
            allTestCases = testCases.map { testCase ->
                TestCaseDisplay(
                    id = testCase.id,
                    description = testCase.id,
                    tags = emptyList(),
                    content = "",
                )
            }
            _uiState.value = TestCaseSearchUiState.Stable(
                testCases = allTestCases,
                searchTags = emptyList(),
                selectedTestCaseId = null,
                currentInput = "",
            )
        } else {
            _uiState.value = TestCaseSearchUiState.Error("Failed to load test cases")
        }
    }

    private fun filterTestCases(
        searchTags: List<String>,
        currentInput: String,
    ): List<TestCaseDisplay> {
        val filterTerms = buildList {
            addAll(searchTags)
            val trimmedInput = currentInput.trim()
            if (trimmedInput.isNotEmpty()) {
                add(trimmedInput)
            }
        }
        if (filterTerms.isEmpty()) {
            return allTestCases
        }

        return allTestCases.filter { testCase ->
            filterTerms.all { term ->
                testCase.id.contains(term, ignoreCase = true) ||
                    testCase.description.contains(term, ignoreCase = true) ||
                    testCase.tags.any { it.contains(term, ignoreCase = true) }
            }
        }
    }

    private fun onAddTag(tag: String) {
        val currentState = _uiState.value
        if (currentState is TestCaseSearchUiState.Stable) {
            val newTags = (currentState.searchTags + tag).distinct()
            val filteredTestCases = filterTestCases(newTags, "")
            _uiState.value = currentState.copy(
                currentInput = "",
                searchTags = newTags,
                testCases = filteredTestCases,
            )
        }
    }
}
