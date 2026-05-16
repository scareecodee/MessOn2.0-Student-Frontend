package com.messon.student.app.utils

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object LoginScreen: Screen("login_screen")

    object RegisterComplaintScreen: Screen(route = "register_complaint_screen")

    object ViewComplaintsScreen: Screen(route="view_complaints_screen")

    object NotificationScreen: Screen(route ="notification_screen")

    object MealPreferenceScreen: Screen(route="meal_pref-screen")

    object CollegeListScreen: Screen(route="college_list_screen")
    object RatingScreen: Screen(route="rating_screen")


}