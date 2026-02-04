package com.messon.app.domain.repository

import co.touchlab.kermit.Logger
import com.messon.app.data.remote.network.Coupon
import com.messon.app.domain.dataModels.CouponDetail
import com.messon.app.presentation.mapper.toDomain
import com.messon.app.utils.NetworkError
import com.messon.app.utils.Result


import com.messon.app.data.local.dao.CouponDao
import com.messon.app.data.local.enitity.CouponEntity
import com.messon.app.presentation.mapper.toEntity
import com.messon.app.utils.Error
import com.messon.app.utils.LocalError
import com.messon.app.data.local.converter.DateTimeConverters
import kotlinx.datetime.LocalDate

class CouponRepository(
    private val couponApi: Coupon,
    private val couponDao: CouponDao,
) {
    val Log= Logger.withTag("CMP_TOAST")

    private val dateConverter = DateTimeConverters()
    suspend fun fetchCoupon(
        date: LocalDate,
        mealSlot: String,
        token: String
    ): Result<CouponDetail, Error> {


        val localCoupon: CouponEntity? = try {
            couponDao.getCouponByDateAndMeal(
                date = dateConverter.fromLocalDate(date),
                mealSlot = mealSlot
            )
        } catch (e: Exception) {
            null
        }

        if (localCoupon != null) {
            return Result.Success(localCoupon.toDomain())
        }


        val remoteResult: Result<CouponDetail, NetworkError> = try {
            val apiResult = couponApi.fetchCoupon(token, mealSlot)
            when (apiResult) {
                is Result.Success -> Result.Success(apiResult.data.toDomain())
                is Result.Error -> Result.Error(apiResult.error)
            }
        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN)
        }


        return when (remoteResult) {
            is Result.Success -> {
                try {
                    couponDao.insertCoupon(remoteResult.data.toEntity())
                } catch (e: Exception) {
                    return Result.Error(LocalError.WRITE_ERROR)
                }
                Result.Success(remoteResult.data)
            }
            is Result.Error -> Result.Error(remoteResult.error)
        }
    }
}
