package com.github.syunpeii.mockstation.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerSettings(
    val resFileFormat: ResFileFormat,
    val testCaseDirectory: String,
    val defaultDelayMs: Long,
    val port: Int,
)

@Serializable
enum class ResFileFormat(val value: Int) {
    // {apiPath}/{METHOD}.res pattern
    METHOD_SUFFIX(1),

    // {apiPath}.res pattern
    SIMPLE(2),
}
