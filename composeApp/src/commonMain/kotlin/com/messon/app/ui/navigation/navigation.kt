package com.messon.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.app.ui.screens.auth.SignInScreen
import com.messon.app.ui.screens.mainscreen.MainScreen
import com.messon.app.ui.screens.mainscreen.RegisterComplaintScreen
import com.messon.app.utils.Screen
import com.messon.app.theme.enterLeft
import com.messon.app.theme.exitRight

@Composable
fun AppNav(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ){
        composable(
            route = Screen.LoginScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            SignInScreen(
                modifier = Modifier,
                onBtnClick = {
                    navController.navigate(Screen.MainScreen.route)
                }
            )
        }
        composable(
            route=Screen.MainScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            MainScreen(
                navController = navController
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


    }
}