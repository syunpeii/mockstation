package com.github.syunpeii.mockstation.core.model

import kotlinx.serialization.Serializable

@Serializable
data class TestCaseSummary(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
)

@Serializable
data class TestCaseDetail(
    val id: String,
    val title: String,
    val description: String,
    val files: List<String>,
    val tags: List<String>,
)
