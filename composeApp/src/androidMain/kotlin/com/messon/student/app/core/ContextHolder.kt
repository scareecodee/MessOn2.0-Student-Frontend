package com.messon.student.app.core
import android.content.Context

internal object AndroidContextHolder {
    lateinit var appContext: Context
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
    }
}