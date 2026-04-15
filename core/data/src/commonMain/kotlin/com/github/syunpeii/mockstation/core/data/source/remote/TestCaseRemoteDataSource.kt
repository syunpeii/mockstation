package com.github.syunpeii.mockstation.core.data.source.remote

import com.github.syunpeii.mockstation.core.data.source.TestCaseDataSource
import com.github.syunpeii.mockstation.core.model.TestCase
import com.github.syunpeii.mockstation.core.model.api.ActivateTestCaseRequest
import com.github.syunpeii.mockstation.core.network.api.TestCaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestCaseRemoteDataSource(
    private val api: TestCaseApi,
) : TestCaseDataSource {

    override suspend fun getTestCases(): List<TestCase> {
        return api.getTestCases()
    }

    override suspend fun getTestCase(
        id: String,
    ): TestCase? {
        return runCatching {
            api.getTestCase(id)
        }.fold(
            onSuccess = { it },
            onFailure = { null },
        )
    }

    override suspend fun saveTestCases(
        testCases: List<TestCase>,
    ) {
        testCases.forEach { case ->
            api.createTestCase(case)
        }
    }

    override suspend fun saveTestCase(
        testCase: TestCase,
    ): TestCase {
        return api.createTestCase(testCase)
    }

    override suspend fun deleteTestCase(
        id: String,
    ) {
        // TODO: Implement when API is ready
        throw NotImplementedError("Delete API not implemented yet")
    }

    override suspend fun activateTestCase(
        request: ActivateTestCaseRequest,
    ) {
        api.activateTestCase(request)
    }

    override fun observeTestCases(): Flow<List<TestCase>> {
        return flowOf(emptyList())
    }

    override suspend fun clearAll() {
        // TODO: Implement if needed
    }
}
