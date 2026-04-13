package com.github.syunpeii.mockstation.core.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ServerSummaryResponse(
    val status: String,
    val deviceCount: Int,
    val testCaseCount: Int,
    val historyCount: Int,
    val uptime: Long,
)
