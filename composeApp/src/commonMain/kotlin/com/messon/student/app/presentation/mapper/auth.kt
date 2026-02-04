package com.messon.student.app.presentation.mapper

import com.messon.student.app.data.remote.dto.LoginResponseDto
import com.messon.student.app.domain.dataModels.LoginResponse


fun LoginResponseDto.toDomain(): LoginResponse{
    return LoginResponse(
        user = user.toDomain(),
        accessToken =accessToken,
        expiresIn = expiresIn,
        tokenType = tokenType
    )
}