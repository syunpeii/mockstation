package com.github.syunpeii.mockstation.core.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class Device(
    val id: String,
    val name: String,
    val testCaseId: String,
    val isEnabled: Boolean,
    val delaySettings: DelaySettings,
    @Contextual
    val createdAt: Instant,
    @Contextual
    val updatedAt: Instant,
)
