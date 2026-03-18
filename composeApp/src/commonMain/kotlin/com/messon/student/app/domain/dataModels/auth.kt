package com.messon.student.app.domain.dataModels

import com.messon.student.app.utils.UserRole


data class  LoginResponse(
    val accessToken: String,
    val tokenType: String = "bearer",
    val user: User,
    val expiresIn: String? = null,
)

data class LoginRequest(
    val idToken: String,
    val role: String= UserRole.STUDENT.value,
    val collegeId: String
)