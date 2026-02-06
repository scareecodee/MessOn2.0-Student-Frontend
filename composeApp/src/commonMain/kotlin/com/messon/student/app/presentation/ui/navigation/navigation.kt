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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.presentation.ui.screens.auth.SignInRoute
import com.messon.student.app.presentation.ui.screens.mainscreen.MainScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.NotificationScreen
import com.messon.student.app.presentation.ui.screens.mainscreen.RegisterComplaintScreen
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MessMenuViewModel
import com.messon.student.app.utils.Screen
import com.messon.student.app.theme.enterLeft
import com.messon.student.app.theme.exitRight

@Composable
fun AppNav(
    navController: NavHostController,
    viewModel: AuthViewModel,
    googleAuth: GoogleAuth,
    authSecureStorage: AuthSecureStorage,
    couponViewModel: CouponViewModel,
    messMenuViewModel: MessMenuViewModel
){
    var sessionChecked by remember { mutableStateOf(false) }
    var isSessionValid by remember{ mutableStateOf(false) }

    LaunchedEffect(Unit){
        isSessionValid=authSecureStorage.isSessionValid()
        sessionChecked=true
    }

    if(!sessionChecked)
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
        return
    }
    NavHost(
        navController = navController,
        startDestination = if(isSessionValid) Screen.MainScreen.route else Screen.LoginScreen.route
    ){
        composable(
            route = Screen.LoginScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            SignInRoute(
                viewModel = viewModel,
                onLoginSuccess = { navController.navigate(Screen.MainScreen.route) },
                googleAuth = googleAuth
            )

        }
        composable(
            route=Screen.MainScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            MainScreen(
                navController = navController,
                authSecureStorage = authSecureStorage,
                couponViewModel = couponViewModel,
                messMenuViewModel = messMenuViewModel
            )
        }

        composable(
            route=Screen.RegisterComplainScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            RegisterComplaintScreen(
                modifier = Modifier,
                heading = "Mess Feedback",
                subHeading = "Report an issue or suggest a change to today's menu.\n" +
                        "\n",
                onSendClick = {}
            )
        }

        composable(
            route=Screen.NotificationScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            NotificationScreen(modifier = Modifier,
                onMarkAsReadBtnClick = {},
                onBackBtnClick = {navController.navigate(Screen.MainScreen.route)}
            )
        }
    }
}