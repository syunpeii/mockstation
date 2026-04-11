package com.github.syunpeii.mockstation.core.model.api

import kotlinx.serialization.Serializable

@Serializable
data class DeviceResponse(
    val id: String,
    val name: String,
    val testCaseId: String?,
    val isEnabled: Boolean,
    val lastAccessedAt: String,
    val createdAt: String,
)

@Serializable
data class UpdateDeviceRequest(
    val name: String?,
    val testCaseId: String?,
    val isEnabled: Boolean?,
)
