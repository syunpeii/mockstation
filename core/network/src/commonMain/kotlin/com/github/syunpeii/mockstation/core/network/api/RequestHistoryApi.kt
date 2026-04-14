package com.github.syunpeii.mockstation.core.network.api

import com.github.syunpeii.mockstation.core.model.api.RequestHistoryResponse

interface RequestHistoryApi {
    suspend fun getRequestHistory(
        search: String?,
        methods: List<String>?,
        statusCategories: List<String>?,
        timeRange: String?,
        sortOrder: String?,
        deviceId: String?,
        limit: Int,
        offset: Int,
    ): RequestHistoryResponse

    suspend fun deleteAllHistory()
}
