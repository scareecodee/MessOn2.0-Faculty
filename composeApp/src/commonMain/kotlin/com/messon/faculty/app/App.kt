package com.messon.faculty.app
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.presentation.ui.navigation.AppNav


@Composable
fun App(
googleAuth: GoogleAuth
) {
    MaterialTheme {
        val navController=rememberNavController()
        AppNav(
            navController = navController,
            googleAuth = googleAuth,
        )

}
}
