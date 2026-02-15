package com.messon.faculty.app
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.messon.faculty.app.domain.repository.CsvRepository
import com.messon.faculty.app.presentation.navigation.AppNav
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel

@Composable
fun App(
filePickerViewModel: FilePickerViewModel,
csvUploadViewModel: CsvUploadViewModel
) {

    MaterialTheme {
        val navController=rememberNavController()
        AppNav(
            navController = navController,
            filePickerViewModel = filePickerViewModel,
            csvUploadViewModel = csvUploadViewModel
        )
        AppNav(
            navController =navController,
            filePickerViewModel =filePickerViewModel,
            csvUploadViewModel = csvUploadViewModel
        )
}
    }
