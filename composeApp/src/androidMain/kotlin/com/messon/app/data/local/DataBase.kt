package com.messon.app.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getDatabase(context: Context): DataBase{
    val dbFile=context.getDatabasePath("messon.db")
    return Room.databaseBuilder<DataBase>(
        context=context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}