package com.messon.app.domain.repository

import com.messon.app.data.remote.dto.LoginRequestDto
import com.messon.app.data.remote.network.Auth
import com.messon.app.domain.dataModels.User
import com.messon.app.presentation.mapper.toDomain
import com.messon.app.utils.NetworkError
import com.messon.app.utils.Result

class AuthRepository (
   private val auth: Auth
){
    suspend fun login(
        loginRequestDto: LoginRequestDto
    ): Result<User, NetworkError>{
        val dtoResult=auth.login(loginRequestDto)

        return when (dtoResult) {
            is Result.Success -> {
                val domain= dtoResult.data.user.toDomain()
                Result.Success(domain)
            }
            is Result.Error -> {
                Result.Error(dtoResult.error)
            }
        }
    }
}