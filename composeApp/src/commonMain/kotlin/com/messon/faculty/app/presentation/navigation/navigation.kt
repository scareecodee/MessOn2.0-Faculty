package com.messon.faculty.app.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.faculty.app.domain.dataModel.CsvPreviewRes
import com.messon.faculty.app.domain.dataModel.ImportValidationError
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.presentation.ui.screens.AddStudentManuallyScreen
import com.messon.faculty.app.presentation.ui.screens.CsvPreviewResScreen
import com.messon.faculty.app.presentation.ui.screens.CsvPreviewScreen
import com.messon.faculty.app.presentation.ui.screens.DataValidityScreen
import com.messon.faculty.app.presentation.ui.screens.StudentDetailScreen
import com.messon.faculty.app.presentation.ui.screens.StudentScreen
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM
import com.messon.faculty.app.theme.enterLeft
import com.messon.faculty.app.theme.exitRight
import com.messon.faculty.app.utils.Screen


@Composable
fun AppNav(
    navController: NavHostController,
    filePickerViewModel: FilePickerViewModel,
    csvUploadViewModel: CsvUploadViewModel,
    addStudentManuallyVM: AddStudentManuallyVM,
    studentDetailScreenVM: StudentDetailScreenVM,
    studentScreenVM: StudentScreenVM
){
    NavHost(
        navController = navController,
        startDestination = Screen.StudentScreen.route,
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
                navController=navController,
                studentDetailScreenVM = studentDetailScreenVM,
                onStudentClick = {
                    navController.navigate(Screen.StudentDetailScreen.route)
                },
                studentScreenVM = studentScreenVM
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
                    onUploadSuccess = {
                        navController.navigate(Screen.CsvPreviewResScreen.route)
                    },
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
                },
                navController = navController
            )
        }

        composable (
            route=Screen.StudentDetailScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            StudentDetailScreen(
                onViewBillClick = {},
                studentDetailScreenVM =studentDetailScreenVM,
                onBackClick = {
                    navController.popBackStack()
                    studentDetailScreenVM.clearSelectedStudent()
                    studentDetailScreenVM.clearToggleStatusState()
                }
            )
        }

        composable (
            route=Screen.CsvPreviewResScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            CsvPreviewResScreen(
                onConfirm = {

                },
                onCancel = {
                    navController.popBackStack()
                },
                onBackBtnClick = {
                    navController.popBackStack()
                },
                csvUploadViewModel =csvUploadViewModel
            )
        }

        composable (
            route=Screen.DataValidityScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            DataValidityScreen(
                onCancel = {
                    navController.popBackStack()
                },
                onBackBtnClick = {
                    navController.popBackStack()
                },
                addStudentManuallyVM=addStudentManuallyVM
            )
        }
    }
}
