package com.messon.faculty.app.theme



import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val animationDuration = 400


// Updated with sliding logic
val enterLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(400)
    ) + fadeIn(animationSpec = tween(400))
}

val exitRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(350)
    ) + fadeOut(animationSpec = tween(350))
}

val popEnterRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    fadeIn(
        animationSpec = tween(animationDuration)
    )
}

val popExitLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    fadeOut(
        animationSpec = tween(animationDuration)
    )
}