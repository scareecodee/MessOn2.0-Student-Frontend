package com.messon.student.app.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class UserInteraction {
    @SerialName("like")
    LIKE,

    @SerialName("dislike")
    DISLIKE
}