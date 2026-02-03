package com.messon.app


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.messon.app.domain.auth.AuthSecureStorage
import com.messon.app.presentation.ui.navigation.AppNav
import com.messon.app.presentation.viewmodel.AuthViewModel
import com.messon.app.presentation.viewmodel.CouponViewModel


@Composable
@Preview
fun App(
    authSecureStorage: AuthSecureStorage,
    authViewModel: AuthViewModel,
    couponViewModel: CouponViewModel
) {
    MaterialTheme {
        val navController=rememberNavController()

        AppNav(
            navController=navController,
            viewModel = authViewModel,
            authSecureStorage=authSecureStorage,
            couponViewModel = couponViewModel
        )


    }
}