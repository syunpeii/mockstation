package com.github.syunpeii.mockstation.core.data.source.local

import com.github.syunpeii.mockstation.core.data.source.TestCaseDataSource
import com.github.syunpeii.mockstation.core.model.TestCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestCaseLocalDataSource : TestCaseDataSource {

    override suspend fun getTestCases(): List<TestCase> {
        return emptyList()
    }

    override suspend fun getTestCase(id: String): TestCase? {
        return null
    }

    override suspend fun saveTestCases(testCases: List<TestCase>) {
        // TODO: Implement if needed
    }

    override suspend fun saveTestCase(testCase: TestCase): TestCase {
        // TODO: Implement with SQLDelight database
        return testCase
    }

    override suspend fun deleteTestCase(id: String) {
        // TODO: Implement if needed
    }

    override fun observeTestCases(): Flow<List<TestCase>> {
        return flowOf(emptyList())
    }

    override suspend fun clearAll() {
        // TODO: Implement if needed
    }
}
