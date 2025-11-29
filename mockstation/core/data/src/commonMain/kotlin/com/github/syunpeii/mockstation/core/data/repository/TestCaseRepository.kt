package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.model.Result
import com.github.syunpeii.mockstation.core.model.TestCase
import kotlinx.coroutines.flow.Flow

interface TestCaseRepository {
    /**
     * Get all test cases
     */
    suspend fun getTestCases(): Result<List<TestCase>>

    /**
     * Get a test case by ID
     */
    suspend fun getTestCase(id: String): Result<TestCase>

    /**
     * Create a test case
     */
    suspend fun createTestCase(testCase: TestCase): Result<TestCase>

    /**
     * Delete a test case
     */
    suspend fun deleteTestCase(id: String): Result<Unit>

    /**
     * Observe test case changes
     */
    fun observeTestCases(): Flow<List<TestCase>>
}
