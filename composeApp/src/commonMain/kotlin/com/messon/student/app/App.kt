package com.messon.student.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.presentation.ui.navigation.AppNav
import com.messon.student.app.presentation.ui.screens.mess.RatingScreen
import com.messon.student.app.presentation.ui.screens.mess.ViewComplaintsScreen
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.ComplaintVM
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import com.messon.student.app.theme.MessonTheme


@Composable
fun App(
    googleAuth: GoogleAuth,
) {
    val navController=rememberNavController()
    MessonTheme(
        darkTheme = isSystemInDarkTheme(),
        content = {
            AppNav(
                navController = navController,
                googleAuth = googleAuth,
            )
        }
    )
}