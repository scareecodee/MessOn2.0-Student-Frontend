package com.messon.student.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.messon.student.app.data.local.converter.DateTimeConverters
import com.messon.student.app.data.local.dao.CouponDao
import com.messon.student.app.data.local.enitity.CouponEntity


@Database(
    entities = [CouponEntity::class],
    version = 1
)
@TypeConverters(DateTimeConverters::class)
abstract class DataBase : RoomDatabase(){

    abstract fun couponDao(): CouponDao
}