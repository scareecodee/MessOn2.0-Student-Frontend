package com.messon.app.di
import com.messon.app.domain.auth.AuthSecureStorage
import org.koin.dsl.module
import com.messon.app.core.AppDispatchers
import com.messon.app.data.local.DataBase
import com.messon.app.data.local.getDatabase

actual val platformModule= module{
    single<AuthSecureStorage> {
        val storage = try {
            AuthSecureStorage()
        } catch (e: Exception) {
            AuthSecureStorage().also { it.clear() }
        }
        storage
    }
    single { AppDispatchers() }
    single<DataBase> { getDatabase() }


}