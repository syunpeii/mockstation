package com.github.syunpeii.mockstation.core.model

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    data object Loading : Result<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isLoading: Boolean get() = this is Loading

    /**
     * Returns the data if this is a Success, or null otherwise.
     *
     * @return The data if Success, or null if Error or Loading.
     */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
}
