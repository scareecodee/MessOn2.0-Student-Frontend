package com.messon.app.utils

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object LoginScreen: Screen("login_screen")

    object RegisterComplainScreen: Screen(route = "register_complaint_screen")
}