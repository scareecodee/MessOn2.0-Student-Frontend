package com.messon.student.app.presentation.mapper

import com.messon.student.app.data.local.enitity.CouponEntity
import com.messon.student.app.data.remote.dto.CouponDetailDto
import com.messon.student.app.domain.dataModels.CouponDetail

fun CouponDetailDto.toDomain(): CouponDetail{
    return CouponDetail(
        date = date,
        mealSlot = mealSlot,
        expiredAt = expireAt,
        usedAt = usedAt,
        couponId = couponId,
        uid = uid,
        signature = signature,
    )
}

fun CouponEntity.toDomain(): CouponDetail = CouponDetail(
    couponId = couponId,
    date = date,
    mealSlot = mealSlot,
    usedAt = usedAt,
    expiredAt = expireAt,
    uid = uid,
    signature = signature
)


fun CouponDetail.toEntity(): CouponEntity = CouponEntity(
    couponId = couponId,
    uid = uid,
    date = date,
    mealSlot =mealSlot,
    expireAt = expiredAt,
    signature = signature,
    usedAt = usedAt
)
