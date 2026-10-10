package com.messon.student.app.di
import com.messon.student.app.domain.auth.AuthSecureStorage
import org.koin.dsl.module
import com.messon.student.app.core.AppDispatchers
import com.messon.student.app.core.IosPushTokenProvider
import com.messon.student.app.core.PushNotificationManager
import com.messon.student.app.core.PushTokenProvider
import com.messon.student.app.data.local.DataBase
import com.messon.student.app.data.local.getDatabase
import com.messon.student.app.domain.auth.provider.IosDeviceInfoProvider
import com.messon.student.app.domain.provider.DeviceInfoProvider

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
    single { PushNotificationManager() }
    single { IosPushTokenProvider(get()) }
    single<DeviceInfoProvider> { IosDeviceInfoProvider() }
}