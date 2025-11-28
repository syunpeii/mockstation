package com.github.syunpeii.mockstation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform