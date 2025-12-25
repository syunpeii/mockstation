package com.github.syunpeii.mockstation.core.model

import kotlinx.serialization.Serializable

@Serializable
data class DelaySettings(
    val type: DelayType,
    val delayMs: Int?,
    val isEnabled: Boolean,
    val targetFiles: List<String>,
)

@Serializable
enum class DelayType {
    OFF,
    PRESET,
    CUSTOM,
}
