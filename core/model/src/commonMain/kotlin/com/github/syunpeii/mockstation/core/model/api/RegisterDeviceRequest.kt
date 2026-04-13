package com.github.syunpeii.mockstation.core.model.api

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDeviceRequest(
    val name: String,
)
