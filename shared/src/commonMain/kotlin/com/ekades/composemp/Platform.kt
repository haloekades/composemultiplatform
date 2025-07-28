package com.ekades.composemp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform