package com.github.syunpeii.mockstation.app.ui.testcasesearch.model

data class TestCaseDisplay(
    val id: String,
    val description: String,
    val tags: List<String>,
    val content: String,
)
