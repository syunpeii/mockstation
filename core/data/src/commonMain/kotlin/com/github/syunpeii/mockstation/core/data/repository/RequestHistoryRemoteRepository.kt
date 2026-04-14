package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange
import com.github.syunpeii.mockstation.core.network.api.RequestHistoryApi
import com.github.syunpeii.mockstation.data.repository.RequestHistoryRepository

class RequestHistoryRemoteRepository(
    private val requestHistoryApi: RequestHistoryApi,
) : RequestHistoryRepository {

    override suspend fun saveRequest(request: RequestInfo): Result<Unit> = Result.success(Unit)

    override suspend fun getRequestHistory(
        search: String?,
        methods: List<HttpMethod>?,
        statusCategories: List<StatusCategory>?,
        timeRange: TimeRange,
        sortOrder: SortOrder,
        deviceId: String?,
        limit: Int,
        offset: Int,
    ): Result<List<RequestInfo>> = runCatching {
        requestHistoryApi.getRequestHistory(
            search = search,
            methods = methods?.map { it.name },
            statusCategories = statusCategories?.map { it.name },
            timeRange = timeRange.name,
            sortOrder = sortOrder.name,
            deviceId = deviceId,
            limit = limit,
            offset = offset,
        ).items
    }

    override suspend fun deleteAllHistory(): Result<Unit> = runCatching {
        requestHistoryApi.deleteAllHistory()
    }

    override suspend fun getHistoryCount(): Result<Int> = Result.success(0)

    override suspend fun trimOldHistory(maxCount: Int): Result<Unit> = Result.success(Unit)
}
