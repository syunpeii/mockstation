package com.github.syunpeii.mockstation.core.model

sealed class Result<out T> {
    val isSuccess: Boolean
        get() = this is Success<T>

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    data class Success<T>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val exception: Throwable,
    ) : Result<Nothing>()

    data object Loading : Result<Nothing>()
}
