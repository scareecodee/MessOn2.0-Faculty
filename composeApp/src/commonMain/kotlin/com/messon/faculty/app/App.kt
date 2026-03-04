package com.messon.faculty.app
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.messon.faculty.app.presentation.navigation.AppNav
import com.messon.faculty.app.presentation.ui.screens.mess.CatererDirectoryScreen
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.EditStudentVM
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM

@Composable
fun App(
filePickerViewModel: FilePickerViewModel,
csvUploadViewModel: CsvUploadViewModel,
addStudentManuallyVM: AddStudentManuallyVM,
studentDetailScreenVM: StudentDetailScreenVM,
studentScreenVM: StudentScreenVM,
editStudentVM: EditStudentVM
) {

    MaterialTheme {
        val navController=rememberNavController()
//        AppNav(
//            navController = navController,
//            filePickerViewModel = filePickerViewModel,
//            csvUploadViewModel = csvUploadViewModel,
//            addStudentManuallyVM = addStudentManuallyVM,
//            studentDetailScreenVM = studentDetailScreenVM,
//            studentScreenVM=studentScreenVM,
//            editStudentVM = editStudentVM
//        )

        CatererDirectoryScreen(
            onDetailClick ={},
            onBackClick = {},
            onFabClick = {}
        )



}
    }
