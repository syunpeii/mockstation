package com.github.syunpeii.mockstation.core.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class TestCase(
    val id: String,
    val title: String,
    val description: String,
    val status: TestCaseStatus,
    @Contextual
    val createdAt: Instant,
    @Contextual
    val updatedAt: Instant,
    val files: List<String>,
)

@Serializable
enum class TestCaseStatus {
    PENDING,
    IN_PROGRESS,
    PASSED,
    FAILED,
}
