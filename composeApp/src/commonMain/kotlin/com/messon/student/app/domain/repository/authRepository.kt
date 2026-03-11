package com.messon.student.app.domain.repository

import com.messon.student.app.data.remote.dto.LoginRequestDto
import com.messon.student.app.data.remote.network.Auth
import com.messon.student.app.domain.dataModels.LoginRequest
import com.messon.student.app.domain.dataModels.LoginResponse

import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result
import com.messon.student.app.presentation.mapper.toDomain
import com.messon.student.app.presentation.mapper.toDto

class AuthRepository (
    private val auth: Auth
){
    suspend fun login(
        loginRequest: LoginRequest
    ): Result<LoginResponse, NetworkError>{
        val dtoResult=auth.login(loginRequest.toDto())

        return when (dtoResult) {
            is Result.Success -> {
                val domain= dtoResult.data.toDomain()
                Result.Success(domain)
            }
            is Result.Error -> {
                Result.Error(dtoResult.error)
            }
        }
    }
}