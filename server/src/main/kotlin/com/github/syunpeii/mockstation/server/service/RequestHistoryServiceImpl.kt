package com.github.syunpeii.mockstation.server.service

import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange
import com.github.syunpeii.mockstation.data.repository.RequestHistoryRepository
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class RequestHistoryServiceImpl(
    private val requestHistoryRepository: RequestHistoryRepository,
) : RequestHistoryService {

    @OptIn(ExperimentalTime::class)
    override suspend fun recordRequest(
        method: HttpMethod,
        path: String,
        statusCode: Int,
        durationMs: Long,
        deviceId: String,
        requestHeaders: Map<String, String>?,
        responseBody: String?,
        requestBody: String?,
    ) {
        val requestInfo = RequestInfo(
            id = UUID.randomUUID().toString(),
            method = method,
            path = path,
            statusCode = statusCode,
            statusText = statusCode.toString(),
            timestamp = Instant.fromEpochMilliseconds(System.currentTimeMillis()),
            deviceId = deviceId,
            requestBody = requestBody,
            responseBody = responseBody,
            headers = requestHeaders ?: emptyMap(),
            responseHeaders = emptyMap(),
            durationMs = durationMs,
        )
        requestHistoryRepository.saveRequest(requestInfo)
    }

    override suspend fun getHistory(
        search: String?,
        methods: List<HttpMethod>?,
        statusCategories: List<StatusCategory>?,
        timeRange: TimeRange,
        sortOrder: SortOrder,
        deviceId: String?,
        limit: Int,
        offset: Int,
    ): Result<List<RequestInfo>> {
        return requestHistoryRepository.getRequestHistory(
            search = search,
            methods = methods,
            statusCategories = statusCategories,
            timeRange = timeRange,
            sortOrder = sortOrder,
            deviceId = deviceId,
            limit = limit,
            offset = offset,
        )
    }

    override suspend fun clearHistory(): Result<Unit> {
        return requestHistoryRepository.deleteAllHistory()
    }

    override suspend fun getHistoryCount(): Result<Int> {
        return requestHistoryRepository.getHistoryCount()
    }
}
