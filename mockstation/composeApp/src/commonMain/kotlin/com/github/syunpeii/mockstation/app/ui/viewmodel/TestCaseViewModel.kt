package com.github.syunpeii.mockstation.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.syunpeii.mockstation.core.data.repository.TestCaseRepository
import com.github.syunpeii.mockstation.core.model.TestCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestCaseViewModel(
    private val testCaseRepository: TestCaseRepository,
) : ViewModel() {

    private val _testCases = MutableStateFlow<List<TestCase>>(emptyList())
    val testCases: StateFlow<List<TestCase>> = _testCases.asStateFlow()

    init {
        loadTestCases()
    }

    private fun loadTestCases() {
        viewModelScope.launch {
            // TODO: Implement test case loading
        }
    }
}
