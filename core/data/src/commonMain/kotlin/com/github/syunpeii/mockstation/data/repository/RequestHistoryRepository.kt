package com.github.syunpeii.mockstation.data.repository

import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange

interface RequestHistoryRepository {
    suspend fun saveRequest(request: RequestInfo): Result<Unit>

    suspend fun getRequestHistory(
        search: String?,
        methods: List<HttpMethod>?,
        statusCategories: List<StatusCategory>?,
        timeRange: TimeRange,
        sortOrder: SortOrder,
        deviceId: String?,
        limit: Int,
        offset: Int,
    ): Result<List<RequestInfo>>

    suspend fun deleteAllHistory(): Result<Unit>

    suspend fun getHistoryCount(): Result<Int>

    suspend fun trimOldHistory(maxCount: Int): Result<Unit>
}
