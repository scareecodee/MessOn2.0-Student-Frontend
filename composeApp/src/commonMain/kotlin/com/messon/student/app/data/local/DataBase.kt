package com.messon.student.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.messon.student.app.data.local.converter.DateTimeConverters
import com.messon.student.app.data.local.dao.CouponDao
import com.messon.student.app.data.local.dao.MessMenuDao
import com.messon.student.app.data.local.enitity.CouponEntity
import com.messon.student.app.data.local.enitity.MessMenuEntity


@Database(

    entities = [CouponEntity::class, MessMenuEntity::class],
    version = 1,


)
@TypeConverters(DateTimeConverters::class)
abstract class DataBase : RoomDatabase(){

    abstract fun couponDao(): CouponDao
    abstract fun messMenuDao(): MessMenuDao
}