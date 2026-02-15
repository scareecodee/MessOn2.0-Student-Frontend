package com.messon.student.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.presentation.ui.navigation.AppNav
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.presentation.viewmodel.MessMenuViewModel


@Composable
@Preview
fun App(
    authSecureStorage: AuthSecureStorage,
    authViewModel: AuthViewModel,
    couponViewModel: CouponViewModel,
    googleAuth: GoogleAuth,
    messMenuViewModel: MessMenuViewModel,
    mealPlannerViewModel: MealPlannerViewModel
) {
    MaterialTheme {
        val navController=rememberNavController()

        AppNav(
            navController=navController,
            authViewModel = authViewModel,
            authSecureStorage=authSecureStorage,
            couponViewModel = couponViewModel,
            googleAuth = googleAuth,
            messMenuViewModel = messMenuViewModel,
            mealPlannerViewModel = mealPlannerViewModel
        )
    }
}