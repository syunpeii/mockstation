package com.github.syunpeii.mockstation.core.data.source

import com.github.syunpeii.mockstation.core.model.TestCase
import com.github.syunpeii.mockstation.core.model.api.ActivateTestCaseRequest
import kotlinx.coroutines.flow.Flow

interface TestCaseDataSource {
    suspend fun getTestCases(): List<TestCase>
    suspend fun getTestCase(
        id: String,
    ): TestCase?

    suspend fun saveTestCases(
        testCases: List<TestCase>,
    )

    suspend fun saveTestCase(
        testCase: TestCase,
    ): TestCase

    suspend fun deleteTestCase(
        id: String,
    )

    suspend fun activateTestCase(
        request: ActivateTestCaseRequest,
    )

    fun observeTestCases(): Flow<List<TestCase>>
    suspend fun clearAll()
}
