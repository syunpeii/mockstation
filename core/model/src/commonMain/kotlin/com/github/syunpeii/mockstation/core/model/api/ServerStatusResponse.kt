package com.github.syunpeii.mockstation.core.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ServerStatusResponse(
    val status: String,
    val version: String,
    val uptime: Long,
)
