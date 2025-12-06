package com.github.syunpeii.mockstation.app.navigation

enum class WindowSizeClass {
    Compact,
    Medium,
    Expanded,
    ;

    companion object {
        fun fromWidthByDp(width: Float): WindowSizeClass = when {
            width < 600 -> Compact
            width < 840 -> Medium
            else -> Expanded
        }
    }
}
