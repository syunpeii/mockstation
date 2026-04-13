package com.github.syunpeii.mockstation.server.routes

enum class ErrorType(val message: String) {
    MISSING_ID("Missing id"),
    TEST_CASE_NOT_FOUND("Test case not found"),
    DEVICE_NOT_FOUND("Device not found"),
    REQUEST_HISTORY_FETCH_FAILED("Failed to fetch request history"),
    REQUEST_HISTORY_CLEAR_FAILED("Failed to clear request history"),
}
