package com.github.syunpeii.mockstation.server.service

import io.ktor.http.HttpMethod
import io.ktor.http.Parameters

interface MockResponseResolver {
    suspend fun resolve(
        testCaseId: String,
        apiPath: String,
        method: HttpMethod,
        queryParameters: Parameters,
    ): StubResponse?
}
