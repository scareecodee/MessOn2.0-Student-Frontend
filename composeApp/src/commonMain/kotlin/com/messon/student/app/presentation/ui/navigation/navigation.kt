package com.messon.student.app.presentation.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.messon.student.app.presentation.ui.screens.college.CollegeListScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.MainScreen
import com.messon.student.app.presentation.ui.screens.mess.MealPreferenceScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.NotificationScreen
import com.messon.student.app.presentation.ui.screens.mess.RatingScreen
import com.messon.student.app.presentation.ui.screens.mess.RegisterComplaintScreen
import com.messon.student.app.presentation.ui.screens.mess.ViewComplaintsScreen
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.Screen
import com.messon.student.app.theme.enterLeft
import com.messon.student.app.theme.exitRight
import com.messon.student.app.presentation.ui.state.AuthState
import com.messon.student.app.presentation.viewmodel.CollegeScreenVM
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNav(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    googleAuth: GoogleAuth,
    authSecureStorage: AuthSecureStorage,
    couponViewModel: CouponViewModel,
    menuFetchVM: MenuFetchVM,
    mealPlannerViewModel: MealPlannerViewModel
) {
    val authState by authViewModel.uiState.collectAsStateWithLifecycle()

    val collegeScreenVM: CollegeScreenVM= koinViewModel()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }

            AuthState.Unauthenticated -> {
                navController.navigate(Screen.CollegeListScreen.route) {
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
        startDestination = Screen.CollegeListScreen.route
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
                authViewModel = authViewModel,
                menuFetchVM=menuFetchVM
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
            val mealPlannerViewModel: MealPlannerViewModel=koinViewModel()
            MealPreferenceScreen(
                mealPlannerViewModel = mealPlannerViewModel
            )
        }

        composable(
            route = Screen.CollegeListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            CollegeListScreen(
                onCardClick = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                onBackClick = {
                    navController.popBackStack()
                },
                collegeScreenVM =collegeScreenVM
            )
        }

        composable(
            route = Screen.RatingScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            RatingScreen(
                onBackBtnClick = {navController.popBackStack()}
            )
        }

        composable(
            route = Screen.RegisterComplaintScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            RegisterComplaintScreen(
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(
            route = Screen.ViewComplaintsScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            ViewComplaintsScreen(
                onRegisterCompClick = {navController.navigate(Screen.RegisterComplaintScreen.route)},
                onBackClick = {navController.popBackStack()}
            )
        }
    }
}
