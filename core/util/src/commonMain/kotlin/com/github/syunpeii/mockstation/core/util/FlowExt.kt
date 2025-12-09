package com.github.syunpeii.mockstation.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

/**
 * Catches exceptions in the Flow and logs them with the specified tag.
 *
 * @param tag The tag to use for logging. Default is "Flow".
 * @param onError When an error occurs, this lambda will be invoked with the exception.
 * @return A Flow that catches exceptions and logs them.
 */
fun <T> Flow<T>.catchAndLog(
    tag: String = "Flow",
    onError: (Throwable) -> Unit = {},
): Flow<T> = catch { exception ->
    println("[$tag] Error: ${exception.message}")
    onError(exception)
}
