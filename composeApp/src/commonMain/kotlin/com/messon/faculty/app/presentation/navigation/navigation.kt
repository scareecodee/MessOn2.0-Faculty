package com.messon.faculty.app.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.presentation.ui.screens.CsvPreviewScreen
import com.messon.faculty.app.presentation.ui.screens.StudentScreen
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.theme.enterLeft
import com.messon.faculty.app.theme.exitRight
import com.messon.faculty.app.utils.Screen


@Composable
fun AppNav(
    navController: NavHostController,
    filePickerViewModel: FilePickerViewModel,
    csvUploadViewModel: CsvUploadViewModel
){
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
                studentsList =List(20){
                    Student(
                        name ="Sundram Kumar",
                        studentId = "B524066",
                        year = 2,
                        branch ="CE",
                        profileImg ="https://picsum.photos/seed/picsum/200/300\n" ,
                        isActive =true
                    )
                },
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
        }

    }