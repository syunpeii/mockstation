package com.github.syunpeii.mockstation.core.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ActivateTestCaseRequest(
    val testCaseId: String,
    val deviceId: String?,
)
