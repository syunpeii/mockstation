package com.github.syunpeii.mockstation.core.data.source

import com.github.syunpeii.mockstation.core.model.TestCase
import kotlinx.coroutines.flow.Flow

interface TestCaseDataSource {

    /**
     * Get all test cases
     *
     * @return List of TestCase
     */
    suspend fun getTestCases(): List<TestCase>

    /**
     * Get a specific test case by ID
     *
     * @param id The ID of the test case to retrieve
     * @return The retrieved TestCase, or null if not found
     */
    suspend fun getTestCase(id: String): TestCase?

    /**
     * Save multiple test cases
     *
     * @param testCases List of TestCase to save
     */
    suspend fun saveTestCases(testCases: List<TestCase>)

    /**
     * Save a single test case
     *
     * @param testCase The TestCase to save
     * @return The saved TestCase (may include server-generated fields like ID, timestamps)
     */
    suspend fun saveTestCase(testCase: TestCase): TestCase

    /**
     * Delete a test case
     *
     * @param id The ID of the test case to delete
     */
    suspend fun deleteTestCase(id: String)

    /**
     * Observe test cases as a Flow
     *
     * @return Flow emitting list of TestCases
     */
    fun observeTestCases(): Flow<List<TestCase>>

    /**
     * Clear all test cases
     */
    suspend fun clearAll()
}
