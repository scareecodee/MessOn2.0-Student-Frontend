package com.messon.student.app.theme


import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val animationDuration = 350


val enterLeft: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
    slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(400)
    )
}


val exitRight:(AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)?= {
    slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(animationDuration)
    )
}

val popEnterRight: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)?= {
    slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth }, // enters from left
        animationSpec = tween(animationDuration)
    )
}

val popExitLeft:  (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)?  = {
    slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth }, // exits to right
        animationSpec = tween(animationDuration)
    )
}