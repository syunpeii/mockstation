package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.model.Result
import com.github.syunpeii.mockstation.core.model.TestCase
import kotlinx.coroutines.flow.Flow

interface TestCaseRepository {
    suspend fun getTestCases(): Result<List<TestCase>>
    suspend fun getTestCase(id: String): Result<TestCase>
    suspend fun createTestCase(testCase: TestCase): Result<TestCase>
    suspend fun deleteTestCase(id: String): Result<Unit>
    fun observeTestCases(): Flow<List<TestCase>>
}
