package com.messon.student.app.core


import kotlinx.coroutines.CoroutineDispatcher

expect class AppDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}
