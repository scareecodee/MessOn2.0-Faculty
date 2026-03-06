package com.messon.faculty.app
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.presentation.ui.navigation.AppNav
import com.messon.faculty.app.presentation.ui.screens.mess.AddMessScreen
import com.messon.faculty.app.presentation.ui.screens.mess.DashboardScreen
import com.messon.faculty.app.presentation.ui.screens.mess.MessListScreen
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.AuthViewModel
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.EditStudentVM
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.MessVM
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM
import com.messon.faculty.app.utils.Screen

@Composable
fun App(
filePickerViewModel: FilePickerViewModel,
csvUploadViewModel: CsvUploadViewModel,
addStudentManuallyVM: AddStudentManuallyVM,
studentDetailScreenVM: StudentDetailScreenVM,
studentScreenVM: StudentScreenVM,
editStudentVM: EditStudentVM,
authViewModel: AuthViewModel,
googleAuth: GoogleAuth,
messVM: MessVM
) {
    MaterialTheme {
        val navController=rememberNavController()
        AppNav(
            navController = navController,
            filePickerViewModel = filePickerViewModel,
            csvUploadViewModel = csvUploadViewModel,
            addStudentManuallyVM = addStudentManuallyVM,
            studentDetailScreenVM = studentDetailScreenVM,
            studentScreenVM = studentScreenVM,
            editStudentVM = editStudentVM,
            authViewModel =authViewModel,
            googleAuth = googleAuth,
            messVM = messVM
        )







}
    }
