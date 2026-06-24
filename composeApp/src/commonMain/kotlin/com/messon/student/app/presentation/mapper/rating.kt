package com.messon.student.app.presentation.mapper

import com.messon.student.app.data.remote.dto.SubmitRatingReqDto
import com.messon.student.app.domain.dataModels.SubmitRatingReq

fun SubmitRatingReq.toDto(): SubmitRatingReqDto{
    return SubmitRatingReqDto(
        mealSlot = mealSlot,
        rating = rating
    )
}