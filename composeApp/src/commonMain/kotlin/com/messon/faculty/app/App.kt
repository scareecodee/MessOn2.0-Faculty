package com.messon.faculty.app
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.messon.faculty.app.domain.dataModel.CsvPreviewRes
import com.messon.faculty.app.domain.dataModel.ImportValidationError
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.domain.repository.CsvRepository
import com.messon.faculty.app.presentation.navigation.AppNav
import com.messon.faculty.app.presentation.ui.screens.CsvPreviewResScreen
import com.messon.faculty.app.presentation.ui.screens.EditStudentScreen
import com.messon.faculty.app.presentation.ui.screens.StudentDetailScreen
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM

@Composable
fun App(
filePickerViewModel: FilePickerViewModel,
csvUploadViewModel: CsvUploadViewModel,
addStudentManuallyVM: AddStudentManuallyVM,
studentDetailScreenVM: StudentDetailScreenVM,
studentScreenVM: StudentScreenVM
) {

    MaterialTheme {
        val navController=rememberNavController()
        AppNav(
            navController = navController,
            filePickerViewModel = filePickerViewModel,
            csvUploadViewModel = csvUploadViewModel,
            addStudentManuallyVM = addStudentManuallyVM,
            studentDetailScreenVM = studentDetailScreenVM,
            studentScreenVM=studentScreenVM
        )

//        EditStudentScreen(
//            onBackClick = {},
//            onSaveClick = {
//
//            }
//        )


}
    }
