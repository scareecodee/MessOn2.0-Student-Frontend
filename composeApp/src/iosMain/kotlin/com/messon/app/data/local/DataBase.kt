package com.messon.app.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun getDatabase(): DataBase{
    val dbFile= NSHomeDirectory()+"/messon.db"
    return Room.databaseBuilder<DataBase>(
        name = dbFile,
        factory = { DataBase::class.instantiateImp()}
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}