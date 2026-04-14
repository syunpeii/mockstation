package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.TestCase
import com.github.syunpeii.mockstation.core.model.api.ActivateTestCaseRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class TestCaseApiImpl(
    private val client: HttpClient,
    private val baseUrl: String,
) : TestCaseApi {

    override suspend fun getTestCases(): List<TestCase> {
        return client.get("$baseUrl/api/testcases").body()
    }

    override suspend fun getTestCase(
        id: String
    ): TestCase {
        return client.get("$baseUrl/api/testcases/$id").body()
    }

    override suspend fun createTestCase(
        testCase: TestCase
    ): TestCase {
        return client.post("$baseUrl/api/testcases") {
            setBody(testCase)
        }.body()
    }

    override suspend fun deleteTestCase(
        id: String
    ) {
        client.delete("$baseUrl/api/testcases/$id")
    }

    override suspend fun activateTestCase(
        request: ActivateTestCaseRequest
    ) {
        client.post("$baseUrl/api/testcases/activate") {
            setBody(request)
        }
    }
}
