package com.messon.faculty.app
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.presentation.ui.navigation.AppNav
import com.messon.faculty.app.presentation.ui.screens.college.CollegeAdminScreen
import com.messon.faculty.app.presentation.ui.screens.college.CollegeListScreen
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.AuthViewModel
import com.messon.faculty.app.presentation.viewmodel.CollegeScreenVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.EditStudentVM
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.MessVM
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM

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
