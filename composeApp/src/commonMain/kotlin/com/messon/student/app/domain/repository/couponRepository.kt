package com.messon.student.app.domain.repository

import co.touchlab.kermit.Logger
import com.messon.student.app.data.remote.network.Coupon
import com.messon.student.app.domain.dataModels.CouponDetail
import com.messon.student.app.presentation.mapper.toDomain
import com.messon.student.app.utils.Result
import com.messon.student.app.data.local.dao.CouponDao
import com.messon.student.app.utils.Error
import com.messon.student.app.data.local.converter.DateTimeConverters
import kotlinx.datetime.LocalDate


class CouponRepository(
    private val couponApi: Coupon,
    private val couponDao: CouponDao,
) {

    val Log= Logger.withTag("CMP_LOG")
    private val log = Logger.withTag("CMP_COUPON")
    private val dateConverter = DateTimeConverters()

    suspend fun fetchCoupon(
        date: LocalDate,
        mealSlot: String,
        token: String
    ): Result<CouponDetail, Error> {

        return when (
            val result =couponApi.fetchCoupon(
                token = token,
                mealSlot = mealSlot
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }
}

