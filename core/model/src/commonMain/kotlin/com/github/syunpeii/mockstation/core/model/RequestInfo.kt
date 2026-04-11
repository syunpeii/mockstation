package com.github.syunpeii.mockstation.core.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class RequestInfo(
    val id: String,
    val method: HttpMethod,
    val path: String,
    val statusCode: Int,
    val statusText: String,

    @Contextual
    val timestamp: Instant,

    val deviceId: String,
    val requestBody: String?,
    val responseBody: String?,
    val headers: Map<String, String>,
    val responseHeaders: Map<String, String>,
    val durationMs: Long?,
)

enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
    OPTIONS,
    ;

    fun toDisplayString(): String = name
}

enum class StatusCategory {
    SUCCESS_2XX,
    REDIRECT_3XX,
    CLIENT_ERROR_4XX,
    SERVER_ERROR_5XX,
    OTHER,
}

enum class TimeRange {
    LAST_HOUR,
    LAST_24_HOURS,
    LAST_7_DAYS,
    ALL,
}

enum class SortOrder {
    NEWEST_FIRST,
    OLDEST_FIRST,
}

fun Int.getStatusCategory(): StatusCategory = when (this) {
    in 200..299 -> StatusCategory.SUCCESS_2XX
    in 300..399 -> StatusCategory.REDIRECT_3XX
    in 400..499 -> StatusCategory.CLIENT_ERROR_4XX
    in 500..599 -> StatusCategory.SERVER_ERROR_5XX
    else -> StatusCategory.OTHER
}
