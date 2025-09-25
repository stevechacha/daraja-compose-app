package com.chacha.darajacmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform