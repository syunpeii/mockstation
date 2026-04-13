package com.github.syunpeii.mockstation.server.routes

import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.SortOrder
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.TimeRange
import com.github.syunpeii.mockstation.core.model.api.ErrorResponse
import com.github.syunpeii.mockstation.core.model.api.RequestHistoryResponse
import com.github.syunpeii.mockstation.server.service.RequestHistoryService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.configureRequestHistoryApi(requestHistoryService: RequestHistoryService) {
    route("/request-history") {
        get("") {
            val query = call.toRequestHistoryQuery()
            val result = requestHistoryService.getHistory(
                search = query.search,
                methods = query.methods,
                statusCategories = query.statusCategories,
                timeRange = query.timeRange,
                sortOrder = query.sortOrder,
                deviceId = query.deviceId,
                limit = query.limit,
                offset = query.offset,
            )

            result.onSuccess { items ->
                val total = requestHistoryService.getHistoryCount().getOrDefault(0)
                call.respond(
                    RequestHistoryResponse(
                        items = items,
                        total = total,
                        limit = query.limit,
                        offset = query.offset,
                    ),
                )
            }.onFailure { _ ->
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(ErrorType.REQUEST_HISTORY_FETCH_FAILED.message),
                )
            }
        }

        delete("") {
            requestHistoryService.clearHistory().onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }.onFailure { _ ->
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(ErrorType.REQUEST_HISTORY_CLEAR_FAILED.message),
                )
            }
        }
    }
}

private data class RequestHistoryQueryParams(
    val search: String?,
    val methods: List<HttpMethod>?,
    val statusCategories: List<StatusCategory>?,
    val timeRange: TimeRange,
    val sortOrder: SortOrder,
    val deviceId: String?,
    val limit: Int,
    val offset: Int,
)

private fun ApplicationCall.toRequestHistoryQuery(): RequestHistoryQueryParams {
    val search = request.queryParameters["search"]
    val methodsParam = request.queryParameters["methods"]
    val statusCategoriesParam = request.queryParameters["statusCategories"]
    val timeRangeParam = request.queryParameters["timeRange"]
    val sortOrderParam = request.queryParameters["sortOrder"]
    val deviceId = request.queryParameters["deviceId"]
    val limit = request.queryParameters["limit"]?.toIntOrNull() ?: 100
    val offset = request.queryParameters["offset"]?.toIntOrNull() ?: 0

    val methods = methodsParam?.split(",")?.mapNotNull { methodName ->
        try {
            HttpMethod.valueOf(methodName.trim().uppercase())
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    val statusCategories = statusCategoriesParam?.split(",")?.mapNotNull { categoryName ->
        try {
            StatusCategory.valueOf(categoryName.trim().uppercase())
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    val timeRange = runCatching {
        timeRangeParam?.let { param ->
            TimeRange.valueOf(param.uppercase())
        }
    }.getOrNull() ?: TimeRange.ALL

    val sortOrder = runCatching {
        sortOrderParam?.let { param ->
            SortOrder.valueOf(param.uppercase())
        }
    }.getOrNull() ?: SortOrder.NEWEST_FIRST

    return RequestHistoryQueryParams(
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
