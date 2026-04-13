package com.github.syunpeii.mockstation.core.model.api

import com.github.syunpeii.mockstation.core.model.RequestInfo
import kotlinx.serialization.Serializable

@Serializable
data class RequestHistoryResponse(
    val items: List<RequestInfo>,
    val total: Int,
    val limit: Int,
    val offset: Int,
)
