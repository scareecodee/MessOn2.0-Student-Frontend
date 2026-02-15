package com.messon.student.app.presentation.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.presentation.ui.screens.auth.SignInRoute
import com.messon.student.app.presentation.ui.screens.mainscreen.MainScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.MealPreferenceScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.NotificationScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.RegisterComplaintScreen
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.presentation.viewmodel.MessMenuViewModel
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.Screen
import com.messon.student.app.theme.enterLeft
import com.messon.student.app.theme.exitRight
import com.messon.student.app.utils.AuthState

@Composable
fun AppNav(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    googleAuth: GoogleAuth,
    authSecureStorage: AuthSecureStorage,
    couponViewModel: CouponViewModel,
    messMenuViewModel: MessMenuViewModel,
    mealPlannerViewModel: MealPlannerViewModel
) {
    val authState by authViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }

            AuthState.Unauthenticated -> {
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }

            else -> Unit
        }
    }


    if (authState is AuthState.Idle || authState is AuthState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = CustomColors.RichCerulean,
                strokeCap = StrokeCap.Round,
            )
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {

        composable(
            route = Screen.LoginScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            SignInRoute(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.MainScreen.route){
                        launchSingleTop=true
                    }
                },
                googleAuth = googleAuth
            )
        }

        composable(
            route = Screen.MainScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            MainScreen(
                navController = navController,
                authSecureStorage = authSecureStorage,
                couponViewModel = couponViewModel,
                messMenuViewModel = messMenuViewModel,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screen.RegisterComplainScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            RegisterComplaintScreen(
                modifier = Modifier,
                heading = "Mess Feedback",
                subHeading = "Report an issue or suggest a change to today's menu.\n\n",
                onSendClick = {},
                onBackBtnClick ={
                    navController.popBackStack()
                },
            )
        }

        composable(
            route = Screen.NotificationScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            NotificationScreen(
                modifier = Modifier,
                onMarkAsReadBtnClick = {},
                onBackBtnClick = {
                    navController.navigate(Screen.MainScreen.route)
                }
            )
        }

        composable(
            route = Screen.MealPreferenceScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            MealPreferenceScreen(
                mealPlannerViewModel = mealPlannerViewModel
            )
        }


    }
}
