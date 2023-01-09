package com.lduboscq.introkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
