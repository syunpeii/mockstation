package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.TestCase
import com.github.syunpeii.mockstation.core.model.api.ActivateTestCaseRequest

interface TestCaseApi {
    suspend fun getTestCases(): List<TestCase>
    suspend fun getTestCase(
        id: String,
    ): TestCase

    suspend fun createTestCase(
        testCase: TestCase,
    ): TestCase

    suspend fun deleteTestCase(
        id: String,
    )

    suspend fun activateTestCase(
        request: ActivateTestCaseRequest,
    )
}
