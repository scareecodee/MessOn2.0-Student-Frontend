package com.messon.app.di

import android.content.Context
import com.messon.app.core.AppDispatchers
import com.messon.app.data.local.DataBase
import com.messon.app.data.local.getDatabase
import com.messon.app.data.remote.network.createHttpClient
import com.messon.app.domain.auth.AuthSecureStorage
import com.messon.app.domain.auth.GoogleAuth
import com.messon.app.domain.auth.GoogleAuthAndroid
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {


    single<DataBase> { getDatabase(androidContext()) }

    single { AppDispatchers() }

    single { createHttpClient(OkHttp.create()) }

    single<GoogleAuth> {
        GoogleAuthAndroid(
            serverClientId = "499767546987-i0cdk28trnaq3rd6gd0mmd4ro0g8hsnb.apps.googleusercontent.com",
            context = get()
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
