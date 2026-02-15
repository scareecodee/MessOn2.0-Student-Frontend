package com.messon.student.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.messon.student.app.data.local.enitity.CouponEntity
import kotlinx.datetime.LocalDate

@Dao
interface CouponDao {

    @Upsert
    suspend fun insertCoupon(couponEntity: CouponEntity)

    @Query("""
        SELECT * FROM CouponEntity
        WHERE date = :date AND mealSlot = :mealSlot
    """)
    suspend fun getCouponByDateAndMeal(
        date: LocalDate,
        mealSlot: String
    ): CouponEntity?
}
