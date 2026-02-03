package com.messon.app.domain.repository

import com.messon.app.data.remote.network.Coupon
import com.messon.app.domain.dataModels.CouponDetail
import com.messon.app.presentation.mapper.toDomain
import com.messon.app.utils.NetworkError
import com.messon.app.utils.Result


class CouponRepository(
    private val coupon: Coupon,
) {

    suspend fun fetchCoupon(token: String, mealSlot: String):Result<CouponDetail, NetworkError> {
        val dtoResult = coupon.fetchCoupon(token,mealSlot)

        return when (dtoResult) {
            is Result.Success -> {
                val domain = dtoResult.data.toDomain()
                Result.Success(domain)
            }
            is Result.Error -> {
                Result.Error(dtoResult.error)
            }
        }
    }

}
