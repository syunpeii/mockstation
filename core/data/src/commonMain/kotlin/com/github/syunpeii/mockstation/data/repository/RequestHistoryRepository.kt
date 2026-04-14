package com.github.syunpeii.mockstation.data.repository

import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange

interface RequestHistoryRepository {
    suspend fun saveRequest(request: RequestInfo): Result<Unit>

    suspend fun getRequestHistory(
        search: String? = null,
        methods: List<HttpMethod>? = null,
        statusCategories: List<StatusCategory>? = null,
        timeRange: TimeRange = TimeRange.ALL,
        sortOrder: SortOrder = SortOrder.NEWEST_FIRST,
        deviceId: String? = null,
        limit: Int = DEFAULT_LIMIT,
        offset: Int = 0,
    ): Result<List<RequestInfo>>

    suspend fun deleteAllHistory(): Result<Unit>

    suspend fun getHistoryCount(): Result<Int>

    suspend fun trimOldHistory(maxCount: Int): Result<Unit>

    companion object {
        private const val DEFAULT_LIMIT = 100
    }
}
