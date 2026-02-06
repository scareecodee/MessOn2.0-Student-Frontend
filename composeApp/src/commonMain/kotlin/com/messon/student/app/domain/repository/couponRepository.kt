package com.messon.student.app.domain.repository

import co.touchlab.kermit.Logger
import com.messon.student.app.data.remote.network.Coupon
import com.messon.student.app.domain.dataModels.CouponDetail
import com.messon.student.app.presentation.mapper.toDomain
import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result


import com.messon.student.app.data.local.dao.CouponDao
import com.messon.student.app.data.local.enitity.CouponEntity
import com.messon.student.app.presentation.mapper.toEntity
import com.messon.student.app.utils.Error
import com.messon.student.app.utils.LocalError
import com.messon.student.app.data.local.converter.DateTimeConverters
import kotlinx.datetime.LocalDate


class CouponRepository(
    private val couponApi: Coupon,
    private val couponDao: CouponDao,
) {

    private val log = Logger.withTag("CMP_COUPON")
    private val dateConverter = DateTimeConverters()

    suspend fun fetchCoupon(
        date: LocalDate,
        mealSlot: String,
        token: String
    ): Result<CouponDetail, Error> {


        val remoteResult: Result<CouponDetail, NetworkError> = try {
            val apiResult = couponApi.fetchCoupon(token, mealSlot)
            when (apiResult) {
                is Result.Success -> Result.Success(apiResult.data.toDomain())
                is Result.Error -> Result.Error(apiResult.error)
            }
        } catch (e: Exception) {
            log.e { "Network error: ${e.message}" }
            Result.Error(NetworkError.UNKNOWN)
        }

        // 🔹 If network succeeds → update Room
        if (remoteResult is Result.Success) {
            try {
                couponDao.insertCoupon(remoteResult.data.toEntity())
            } catch (e: Exception) {
                log.e { "Room insert failed: ${e.message}" }
                return Result.Error(LocalError.WRITE_ERROR)
            }
        }

        // 🔹 ALWAYS read from Room (single source of truth)
        val localCoupon: CouponEntity? = try {
            couponDao.getCouponByDateAndMeal(
                date = dateConverter.fromLocalDate(date),
                mealSlot = mealSlot
            )
        } catch (e: Exception) {
            null
        }

        return if (localCoupon != null) {
            Result.Success(localCoupon.toDomain())
        } else {
            Result.Error(LocalError.NOT_FOUND)
        }
    }
}

