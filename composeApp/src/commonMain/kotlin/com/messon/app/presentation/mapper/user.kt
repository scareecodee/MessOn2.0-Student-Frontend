package com.messon.app.presentation.mapper

import com.messon.app.data.remote.dto.UserDto
import com.messon.app.domain.dataModels.User


fun UserDto.toDomain(): User{
    return User(
        name = name,
        mailId = email,
        profileImg = picture?:"",
        id = id,
    )
}

fun User.toDto(id:String): UserDto{
    return UserDto(
        name = name,
        email = mailId,
        picture = profileImg,
        id = id
    )
}