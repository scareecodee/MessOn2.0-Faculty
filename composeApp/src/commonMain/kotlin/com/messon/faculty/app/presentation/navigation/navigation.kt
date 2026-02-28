package com.messon.faculty.app.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.presentation.ui.screens.AddStudentManuallyScreen
import com.messon.faculty.app.presentation.ui.screens.CsvPreviewScreen
import com.messon.faculty.app.presentation.ui.screens.StudentScreen
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.theme.enterLeft
import com.messon.faculty.app.theme.exitRight
import com.messon.faculty.app.utils.Screen


@Composable
fun AppNav(
    navController: NavHostController,
    filePickerViewModel: FilePickerViewModel,
    csvUploadViewModel: CsvUploadViewModel,
    addStudentManuallyVM: AddStudentManuallyVM
){
    val students = remember {
        List(20) {
            Student(
                name = "Sundram Kumar",
                studentId = "B524066$it",
                year = 2,
                branch = "CE",
                profileImg = "https://picsum.photos/seed/picsum/200/300",
                isActive = true
            )
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screen.StudentScreen.route
    ){
        composable(
            route = Screen.StudentScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            StudentScreen(
                modifier = Modifier,
                onBackBtnClick = {},
                filePickerViewModel=filePickerViewModel,
                studentsList =students,
                navController=navController
            )
        }

            composable(
                route=Screen.CsvPreviewScreen.route,
                enterTransition = enterLeft,
                exitTransition = exitRight
            ) {
                CsvPreviewScreen(
                    filePickerViewModel = filePickerViewModel,
                    onBackClick = {

                        navController.popBackStack()
                    },
                    csvUploadViewModel = csvUploadViewModel,
                    token = "",
                    onUploadSuccess = {},
                    modifier = Modifier,
                )
            }


        composable(
            route=Screen.AddStudentManuallyScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            AddStudentManuallyScreen(
                addStudentManuallyVM = addStudentManuallyVM,
                onBackBtnClick ={
                    addStudentManuallyVM.clearCurrentStudents()
                    navController.popBackStack()
                }
            )
        }
    }
}
