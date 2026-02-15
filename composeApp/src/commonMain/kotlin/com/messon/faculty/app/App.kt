package com.messon.faculty.app


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.messon.faculty.app.presentation.navigation.AppNav
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel


@Composable
fun App() {

    MaterialTheme {
        val navController=rememberNavController()
        val filePickerViewModel: FilePickerViewModel= viewModel()
        AppNav(
            navController = navController,
            filePickerViewModel = filePickerViewModel
        )

        }
}
