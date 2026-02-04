package com.messon.student.app
interface Platform {
    val name: String
}

expect fun getPlatform(): Platform