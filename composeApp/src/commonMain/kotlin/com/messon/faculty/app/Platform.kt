package com.messon.faculty.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform