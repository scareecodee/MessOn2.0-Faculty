package com.messon.faculty.app.presentation.ui.navigation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.presentation.ui.screens.mess.AddManagerScreen
import com.messon.faculty.app.presentation.ui.screens.mess.AddMessScreen
import com.messon.faculty.app.presentation.ui.screens.mess.DashboardScreen
import com.messon.faculty.app.presentation.ui.screens.mess.MessListScreen
import com.messon.faculty.app.presentation.ui.screens.mess.MessManagerListScreen
import com.messon.faculty.app.presentation.ui.screens.students.AddStudentManuallyScreen
import com.messon.faculty.app.presentation.ui.screens.students.CsvPreviewResScreen
import com.messon.faculty.app.presentation.ui.screens.students.CsvPreviewScreen
import com.messon.faculty.app.presentation.ui.screens.students.DataValidityScreen
import com.messon.faculty.app.presentation.ui.screens.students.EditStudentScreen
import com.messon.faculty.app.presentation.ui.screens.students.StudentDetailScreen
import com.messon.faculty.app.presentation.ui.screens.students.StudentScreen
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.AuthViewModel
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.EditStudentVM
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.MessVM
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM
import com.messon.faculty.app.theme.enterLeft
import com.messon.faculty.app.theme.exitRight
import com.messon.faculty.app.utils.Screen
import kotlin.math.sin


@Composable
fun AppNav(
    navController: NavHostController,
    filePickerViewModel: FilePickerViewModel,
    csvUploadViewModel: CsvUploadViewModel,
    addStudentManuallyVM: AddStudentManuallyVM,
    studentDetailScreenVM: StudentDetailScreenVM,
    studentScreenVM: StudentScreenVM,
    editStudentVM: EditStudentVM,
    authViewModel: AuthViewModel,
    googleAuth: GoogleAuth,
    messVM: MessVM
){

    val authState by authViewModel.uiState.collectAsStateWithLifecycle()

//    LaunchedEffect(authState) {
//        when (authState) {
//            is AuthState.Success -> {
//                navController.navigate(Screen.MainScreen.route) {
//                    popUpTo(0) { inclusive = true }
//                    launchSingleTop = true
//                }
//            }
//
//            AuthState.Unauthenticated -> {
//                navController.navigate(Screen.LoginScreen.route) {
//                    popUpTo(0) { inclusive = true }
//                    launchSingleTop = true
//                }
//            }
//
//            else -> Unit
//        }
//    }


//    if (authState is AuthState.Idle || authState is AuthState.Loading) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator(
//                color = CustomColors.RichCerulean,
//                strokeCap = StrokeCap.Round,
//            )
//        }
//        return
//    }

    NavHost(
        navController = navController,
        startDestination = Screen.MessListScreen.route,
        modifier = Modifier.fillMaxSize()
    ){

        composable(
            route = Screen.StudentScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            StudentScreen(
                modifier = Modifier,
                onBackBtnClick = {
                    navController.popBackStack()
                },
                filePickerViewModel=filePickerViewModel,
                navController=navController,
                studentDetailScreenVM = studentDetailScreenVM,
                onStudentClick = {
                    navController.navigate(Screen.StudentDetailScreen.route)
                },
                studentScreenVM = studentScreenVM
            )
        }
//
//        composable(
//            route = Screen.LoginScreen.route,
//            enterTransition = enterLeft,
//            exitTransition = exitRight
//        ) {
//            SignInRoute(
//                viewModel = authViewModel,
//                onLoginSuccess = {
//                    navController.navigate(Screen.MainScreen.route){
//                        launchSingleTop=true
//                    }
//                },
//                googleAuth = googleAuth
//            )
//        }

            composable(
                route=Screen.CsvPreviewScreen.route,
                enterTransition = enterLeft,
                exitTransition = exitRight,
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
                },
                onEditClick = {
                    navController.navigate(
                        Screen.EditStudentScreen.route
                    ){
                      launchSingleTop=true
                    }
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

        composable (
            route=Screen.EditStudentScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            EditStudentScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                studentDetailScreenVM = studentDetailScreenVM,
                editStudentVM =editStudentVM
            )
        }

        composable (
            route=Screen.DashboardScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            DashboardScreen(
                onBackClick = { navController.popBackStack() },
                onStudentsClick = {
                    navController.navigate(Screen.StudentScreen.route)
                },
                onManagersClick = {
                    navController.navigate(Screen.MessManagerListScreen.route)
                },
                onAdminsClick = {},
                messVM = messVM
            )
        }

        composable (
            route=Screen.MessListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            MessListScreen(
                onBackBtnClick = {},
                onFabClick = {
                    navController.navigate(Screen.AddMessScreen.route){
                        launchSingleTop=true
                    }
                },
                messVM = messVM,
                onCardClick = {
                    navController.navigate(Screen.DashboardScreen.route){
                        launchSingleTop=true
                    }
                }
            )
        }

        composable (
            route=Screen.MessManagerListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            MessManagerListScreen(
                onDetailClick = {

                },
                onBackClick = {
                    navController.popBackStack()
                },
                onFabClick = {
                    navController.navigate(Screen.AddManagerScreen.route){
                        launchSingleTop=true
                    }
                },
                messVM = messVM
            )
        }

        composable (
            route=Screen.AddMessScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            AddMessScreen(
                onBackBtnClick = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            route=Screen.AddManagerScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            AddManagerScreen(
                onBackBtnClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
