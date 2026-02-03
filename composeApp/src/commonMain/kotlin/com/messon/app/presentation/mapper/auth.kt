package com.messon.app.presentation.mapper

import com.messon.app.data.remote.dto.LoginResponseDto
import com.messon.app.domain.dataModels.LoginResponse


fun LoginResponseDto.toDomain(): LoginResponse{
    return LoginResponse(
        user = user.toDomain(),
        accessToken =accessToken,
        expiresIn = expiresIn,
        tokenType = tokenType
    )
}