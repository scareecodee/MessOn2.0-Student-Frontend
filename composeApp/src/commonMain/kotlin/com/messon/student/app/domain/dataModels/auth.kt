package com.messon.student.app.domain.dataModels

data class  LoginResponse(
    val accessToken: String,
    val tokenType: String = "bearer",
    val user: User,
    val expiresIn: String? = null,
    )
