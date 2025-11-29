package com.github.syunpeii.mockstation.core.util

/**
 * Checks if the string is neither null nor empty.
 *
 * @return `true` if the string is not null and not empty, `false` otherwise.
 */
fun String?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()


/**
 * Checks if the string is neither null nor blank.
 *
 * @return `true` if the string is not null and not blank, `false` otherwise.
 */
fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()
