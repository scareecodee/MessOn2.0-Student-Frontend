package com.messon.student.app.presentation.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.messon.student.app.domain.provider.DeviceInfoProvider
import com.messon.student.app.presentation.ui.screens.auth.SignInRoute
import com.messon.student.app.presentation.ui.screens.college.CollegeListScreen
import com.messon.student.app.presentation.ui.screens.mess.MealPreferenceScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.NotificationScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.MainScreen
import com.messon.student.app.presentation.ui.screens.mess.RatingScreen
import com.messon.student.app.presentation.ui.screens.mess.RegisterComplaintScreen
import com.messon.student.app.presentation.ui.screens.mess.ViewComplaintsScreen
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.utils.Screen
import com.messon.student.app.theme.enterLeft
import com.messon.student.app.theme.exitRight
import com.messon.student.app.presentation.ui.state.AuthState
import com.messon.student.app.presentation.viewmodel.CollegeScreenVM
import com.messon.student.app.presentation.viewmodel.ComplaintVM
import com.messon.student.app.presentation.viewmodel.FetchComplaintsVM
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import com.messon.student.app.presentation.viewmodel.NotificationViewModel
import com.messon.student.app.presentation.viewmodel.SubmitRatingVM
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNav(
    navController: NavHostController,
    googleAuth: GoogleAuth,
) {
    val collegeScreenVM: CollegeScreenVM= koinViewModel()
    val authSecureStorage: AuthSecureStorage = koinInject()
    val authViewModel: AuthViewModel = koinViewModel<AuthViewModel>()
    val couponViewModel: CouponViewModel =koinViewModel<CouponViewModel>()
    val menuFetchVM=koinViewModel<MenuFetchVM>()
    val notificationViewModel: NotificationViewModel=koinViewModel<NotificationViewModel>()
    val mealPlannerViewModel: MealPlannerViewModel=koinViewModel<MealPlannerViewModel>()
    val deviceInfoProvider: DeviceInfoProvider=koinInject()
    val complaintVM: ComplaintVM=koinViewModel<ComplaintVM>()

    val authState by authViewModel.uiState.collectAsStateWithLifecycle()

    val startDestination = when (authState) {
        is AuthState.Success -> Screen.MainScreen.route
        AuthState.Unauthenticated -> Screen.CollegeListScreen.route
        else -> Screen.CollegeListScreen.route
    }


    if (authState is AuthState.Idle || authState is AuthState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                strokeCap = StrokeCap.Round,
            )
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = Screen.LoginScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            SignInRoute(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.MainScreen.route) {
                        launchSingleTop = true
                    }
                },
                googleAuth = googleAuth,
                notificationViewModel = notificationViewModel,
                deviceInfoProvider = deviceInfoProvider
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
            val submitRatingVM: SubmitRatingVM=koinViewModel()
            RatingScreen(
                onBackBtnClick = { navController.popBackStack() },
                submitRatingVM = submitRatingVM
            )
        }

        composable(
            route = Screen.RegisterComplaintScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            RegisterComplaintScreen(
                onBackClick = { navController.popBackStack() },
                complaintVM = complaintVM
            )
        }

        composable(
            route = Screen.ViewComplaintsScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            val fetchComplaintVM: FetchComplaintsVM=koinViewModel()
            ViewComplaintsScreen(
                onRegisterCompClick = { navController.navigate(Screen.RegisterComplaintScreen.route) },
                onBackClick = { navController.popBackStack() },
                fetchComplaintsVM = fetchComplaintVM
            )
        }
    }
}
