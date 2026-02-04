package com.messon.student.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.messon.student.app.data.local.enitity.CouponEntity

@Dao
interface CouponDao{

    @Upsert
    fun insertCoupon(couponEntity: CouponEntity)

    @Query("SELECT * FROM CouponEntity WHERE date = :date AND mealSlot=:mealSlot ")
    suspend fun getCouponByDateAndMeal(date: String,mealSlot: String): CouponEntity

}