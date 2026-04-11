package com.github.syunpeii.mockstation.core.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ServerSettingsResponse(
    val resFileFormat: Int,
    val testCaseDirectory: String,
    val defaultDelayMs: Long,
    val port: Int,
)

@Serializable
data class UpdateServerSettingsRequest(
    val resFileFormat: Int?,
    val testCaseDirectory: String?,
    val defaultDelayMs: Long?,
    val port: Int?,
)
