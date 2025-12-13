package com.github.syunpeii.mockstation.core.designsystem.resources

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

data class ComposeStringResource(
    val resourceId: StringResource,
    val formatArgs: List<Any>,
) {
    constructor(resourceId: StringResource, vararg formatArgs: Any) : this(resourceId, formatArgs.toList())

    @Composable
    fun getString(): String {
        val template = stringResource(resourceId)
        return template.format(*formatArgs.toTypedArray())
    }
}
