package com.messon.student.app.presentation.mapper

import com.messon.student.app.data.remote.dto.UserDto
import com.messon.student.app.domain.dataModels.User


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