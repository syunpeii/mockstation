@file:OptIn(kotlin.time.ExperimentalTime::class)

package com.github.syunpeii.mockstation.data.repository

import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange
import com.github.syunpeii.mockstation.core.model.getStatusCategory
import java.util.concurrent.ConcurrentLinkedDeque
import kotlin.time.Instant

class RequestHistoryRepositoryImpl : RequestHistoryRepository {

    private val history = ConcurrentLinkedDeque<RequestInfo>()

    override suspend fun saveRequest(request: RequestInfo): Result<Unit> = runCatching {
        history.addFirst(request)
        if (history.size > MAX_HISTORY_SIZE) {
            trimToSize(MAX_HISTORY_SIZE)
        }
    }

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
        var filtered = history.toList()

        if (search != null) {
            filtered = filtered.filter { it.path.contains(search, ignoreCase = true) }
        }

        if (!methods.isNullOrEmpty()) {
            filtered = filtered.filter { it.method in methods }
        }

        if (!statusCategories.isNullOrEmpty()) {
            filtered = filtered.filter { it.statusCode.getStatusCategory() in statusCategories }
        }

        filtered = when (timeRange) {
            TimeRange.LAST_HOUR -> {
                val oneHourAgo = Instant.fromEpochMilliseconds(System.currentTimeMillis() - 60 * 60 * 1000)
                filtered.filter { it.timestamp >= oneHourAgo }
            }

            TimeRange.LAST_24_HOURS -> {
                val oneDayAgo = Instant.fromEpochMilliseconds(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
                filtered.filter { it.timestamp >= oneDayAgo }
            }

            TimeRange.LAST_7_DAYS -> {
                val sevenDaysAgo = Instant.fromEpochMilliseconds(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000)
                filtered.filter { it.timestamp >= sevenDaysAgo }
            }

            TimeRange.ALL -> filtered
        }

        if (deviceId != null) {
            filtered = filtered.filter { it.deviceId == deviceId }
        }

        filtered = when (sortOrder) {
            SortOrder.NEWEST_FIRST -> filtered.sortedByDescending { it.timestamp }
            SortOrder.OLDEST_FIRST -> filtered.sortedBy { it.timestamp }
        }

        filtered.drop(offset).take(limit)
    }

    override suspend fun deleteAllHistory(): Result<Unit> = runCatching {
        history.clear()
    }

    override suspend fun getHistoryCount(): Result<Int> = runCatching {
        history.size
    }

    override suspend fun trimOldHistory(maxCount: Int): Result<Unit> = runCatching {
        if (history.size > maxCount) {
            trimToSize(maxCount)
        }
    }

    private fun trimToSize(size: Int) {
        while (history.size > size) {
            history.removeLast()
        }
    }

    companion object {
        private const val MAX_HISTORY_SIZE = 1000
    }
}
