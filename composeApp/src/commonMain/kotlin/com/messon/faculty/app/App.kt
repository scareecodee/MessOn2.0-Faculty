package com.messon.faculty.app
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.presentation.ui.navigation.AppNav
import com.messon.faculty.app.theme.MessonTheme


@Composable
fun App(
googleAuth: GoogleAuth
) {
    MaterialTheme {
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
}
