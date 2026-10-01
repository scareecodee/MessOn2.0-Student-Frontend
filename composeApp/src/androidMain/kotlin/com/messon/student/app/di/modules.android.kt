package com.messon.student.app.di

import android.app.Activity
import android.content.Context
import com.messon.student.app.core.AppDispatchers
import com.messon.student.app.core.EncryptedTokenStorage
import com.messon.student.app.core.PushNotificationManager
import com.messon.student.app.core.PushTokenProvider
import com.messon.student.app.data.local.DataBase
import com.messon.student.app.data.local.getDatabase
import com.messon.student.app.data.remote.network.createHttpClient
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.domain.auth.GoogleAuthAndroid
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {


    single<DataBase> { getDatabase(androidContext()) }

    single { AppDispatchers() }

    single { createHttpClient(OkHttp.create()) }

    single { EncryptedTokenStorage(androidContext()) }
    single { PushNotificationManager() }
    single { PushTokenProvider() }

    factory<GoogleAuth> { (activity: Activity) ->
        GoogleAuthAndroid(
            serverClientId = "238348258649-0j2o0hqbhu0pcdrg5i0j1sajg1ppaeeu.apps.googleusercontent.com",
            context = activity
        )
    }


    single {
        val context: Context = get()
        try {
            AuthSecureStorage(context)
        } catch (e: Exception) {
            context.deleteSharedPreferences("auth_prefs")
            AuthSecureStorage(context)
        }
    }

}
