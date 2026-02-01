package com.messon.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.messon.app.data.remote.network.Auth
import com.messon.app.data.remote.network.createHttpClient
import com.messon.app.di.platformModule
import com.messon.app.di.sharedModule
import com.messon.app.domain.auth.AuthSecureStorage
import com.messon.app.presentation.viewmodel.AuthViewModel
import com.messon.app.domain.auth.GoogleAuthAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        startKoin {
            androidContext(this@MainActivity)
            modules(listOf(sharedModule, platformModule))
        }

        setContent {
            val authSecureStorage: AuthSecureStorage= koinInject()
            val authViewModel: AuthViewModel = koinViewModel<AuthViewModel>()
            App(authViewModel = authViewModel, authSecureStorage =authSecureStorage )
        }
    }
}
