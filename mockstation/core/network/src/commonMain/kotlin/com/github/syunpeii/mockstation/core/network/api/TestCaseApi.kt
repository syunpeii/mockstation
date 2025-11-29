package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.TestCase

interface TestCaseApi {

    /**
     * Fetches a list of all test cases.
     *
     * @return A list of [TestCase] objects.
     */
    suspend fun getTestCases(): List<TestCase>

    /**
     * Fetches a specific test case by its ID.
     *
     * @param id The ID of the test case to fetch.
     * @return The [TestCase] object corresponding to the provided ID.
     */
    suspend fun getTestCase(id: String): TestCase

    /**
     * Creates a new test case.
     *
     * @param testCase The [TestCase] object to create.
     * @return The created [TestCase] object.
     */
    suspend fun createTestCase(testCase: TestCase): TestCase

    /**
     * Deletes a test case by its ID.
     *
     * @param id The ID of the test case to delete.
     */
    suspend fun deleteTestCase(id: String)
}
