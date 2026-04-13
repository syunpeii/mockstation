package com.github.syunpeii.mockstation.server.service

import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange

interface RequestHistoryService {
    suspend fun recordRequest(
        method: HttpMethod,
        path: String,
        statusCode: Int,
        durationMs: Long,
        deviceId: String,
        requestHeaders: Map<String, String>?,
        responseBody: String?,
        requestBody: String?,
    )

    suspend fun getHistory(
        search: String?,
        methods: List<HttpMethod>?,
        statusCategories: List<StatusCategory>?,
        timeRange: TimeRange,
        sortOrder: SortOrder,
        deviceId: String?,
        limit: Int,
        offset: Int,
    ): Result<List<RequestInfo>>

    suspend fun clearHistory(): Result<Unit>

    suspend fun getHistoryCount(): Result<Int>
}
