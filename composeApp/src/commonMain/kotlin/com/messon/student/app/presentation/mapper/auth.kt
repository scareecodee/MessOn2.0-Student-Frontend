package com.messon.student.app.presentation.mapper

import com.messon.student.app.data.remote.dto.LoginRequestDto
import com.messon.student.app.data.remote.dto.LoginResponseDto
import com.messon.student.app.domain.dataModels.LoginRequest
import com.messon.student.app.domain.dataModels.LoginResponse


fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        user = user.toDomain(),
        accessToken =accessToken,
        expiresIn = expiresIn,
        tokenType = tokenType
    )
}

fun LoginRequest.toDto(): LoginRequestDto {
    return LoginRequestDto(
        idToken = idToken,
        role = role,
        collegeId = collegeId
    )
}