package com.messon.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.messon.app.domain.auth.AuthSecureStorage
import com.messon.app.presentation.viewmodel.AuthViewModel
import com.messon.app.presentation.viewmodel.CouponViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val authSecureStorage: AuthSecureStorage= koinInject()
            val authViewModel: AuthViewModel = koinViewModel<AuthViewModel>()
            val couponViewModel: CouponViewModel=koinViewModel<CouponViewModel>()
            App(authViewModel = authViewModel, authSecureStorage =authSecureStorage, couponViewModel = couponViewModel )
        }
    }
}
