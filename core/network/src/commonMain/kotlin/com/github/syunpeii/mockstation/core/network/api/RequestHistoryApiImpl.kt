package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.api.RequestHistoryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RequestHistoryApiImpl(
    private val client: HttpClient,
    private val baseUrl: String,
) : RequestHistoryApi {

    override suspend fun getRequestHistory(
        search: String?,
        methods: List<String>?,
        statusCategories: List<String>?,
        timeRange: String?,
        sortOrder: String?,
        deviceId: String?,
        limit: Int,
        offset: Int,
    ): RequestHistoryResponse {
        return client.get("$baseUrl/api/request-history") {
            search?.let { parameter("search", it) }
            methods?.forEach { parameter("methods", it) }
            statusCategories?.forEach { parameter("statusCategories", it) }
            timeRange?.let { parameter("timeRange", it) }
            sortOrder?.let { parameter("sortOrder", it) }
            deviceId?.let { parameter("deviceId", it) }
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
    }

    override suspend fun deleteAllHistory() {
        client.delete("$baseUrl/api/request-history")
    }
}
