package com.github.syunpeii.mockstation.core.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TestCase(
    val id: String,
    val title: String,
    val description: String,
    val status: TestCaseStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
)

@Serializable
enum class TestCaseStatus {
    PENDING,
    IN_PROGRESS,
    PASSED,
    FAILED,
}
