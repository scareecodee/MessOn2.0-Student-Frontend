package com.messon.app.presentation.mapper

import com.messon.app.data.remote.dto.CouponDetailDto
import com.messon.app.domain.dataModels.CouponDetail

fun CouponDetailDto.toDomain(): CouponDetail{
    return CouponDetail(
        date = date,
        mealSlot=mealSlot,
        expiredAt = expireAt,
        expired = expired
    )
}