package com.messon.faculty.app.presentation.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.presentation.ui.screens.HomeScreen
import com.messon.faculty.app.presentation.ui.screens.auth.SignInRoute
import com.messon.faculty.app.presentation.ui.screens.college.*
import com.messon.faculty.app.presentation.ui.screens.mess.*
import com.messon.faculty.app.presentation.ui.screens.students.*
import com.messon.faculty.app.presentation.ui.uiState.AuthState
import com.messon.faculty.app.presentation.viewmodel.*
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.theme.enterLeft
import com.messon.faculty.app.theme.exitRight
import com.messon.faculty.app.utils.Screen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNav(
    navController: NavHostController,
    googleAuth: GoogleAuth
) {
    val filePickerVM: FilePickerViewModel = koinViewModel()
    val authViewModel: AuthViewModel = koinViewModel()
    val createMessVM: CreateMessVM = koinViewModel()
    val messListVM: MessListVM=koinViewModel()
    val csvUploadViewModel: CsvUploadViewModel=koinViewModel()
    val studentDetailVM: StudentDetailScreenVM = koinViewModel()
    val editStudentVM: EditStudentVM=koinViewModel()


    val authState by authViewModel.uiState.collectAsStateWithLifecycle()

    var hasNavigated by rememberSaveable{ mutableStateOf(false) }

    LaunchedEffect(authState) {

        if (hasNavigated) return@LaunchedEffect

        when (authState) {

            is AuthState.Success -> {
                hasNavigated = true
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }

            AuthState.Unauthenticated -> {
                hasNavigated = true
                navController.navigate(Screen.CollegeListScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }

            else -> Unit
        }
    }

    if (authState is AuthState.Idle || authState is AuthState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = CustomColors.RichCerulean,
                strokeCap = StrokeCap.Round
            )
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = Screen.CollegeListScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {


        composable(
            route = Screen.StudentScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            val studentScreenVM: StudentScreenVM = koinViewModel()

            StudentScreen(
                modifier = Modifier,
                onBackBtnClick = { navController.popBackStack() },
                filePickerViewModel = filePickerVM,
                navController = navController,
                studentDetailScreenVM = studentDetailVM,
                studentScreenVM = studentScreenVM,
                onStudentClick = {
                    navController.navigate(Screen.StudentDetailScreen.route)
                }
            )
        }


        composable(
            route = Screen.LoginScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            SignInRoute(
                viewModel = authViewModel,
                googleAuth = googleAuth,
                onLoginSuccess = {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }


        composable(
            route = Screen.DashboardScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            DashboardScreen(
                messVM = messListVM,
                onBackClick = { navController.popBackStack() },
                onStudentsClick = {
                    navController.navigate(Screen.StudentScreen.route)
                },
                onManagersClick = {
                    navController.navigate(Screen.MessManagerListScreen.route)
                },
                onAdminsClick = {
                    navController.navigate(Screen.CollegeAdminScreen.route)
                }
            )
        }


        composable(
            route = Screen.MessListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            MessListScreen(
                messListVM = messListVM,
                onBackBtnClick = {
                    navController.popBackStack()
                },
                onFabClick = {
                    navController.navigate(Screen.AddMessScreen.route) {
                        launchSingleTop = true
                    }
                },
                onCardClick = {
                    navController.navigate(Screen.DashboardScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }


        composable(
            route = Screen.MessManagerListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            MessManagerListScreen(
//                messListVM=messListVM,
                onBackClick = { navController.popBackStack() },
                onFabClick = {
                    navController.navigate(Screen.AddManagerScreen.route) {
                        launchSingleTop = true
                    }
                },
                onCardClick = {}
            )
        }


        composable(
            route = Screen.AddMessScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            AddMessScreen(
                onBackBtnClick = {
                    navController.popBackStack()
                },
                createMessVM = createMessVM
            )
        }


        composable(
            route = Screen.AddManagerScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            AddManagerScreen(
                onBackBtnClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = Screen.CollegeAdminScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            CollegeAdminScreen(
                messListVM=messListVM,
                onBackClick = { navController.popBackStack() },
                onFabClick = {
                    navController.navigate(Screen.AddCollegeAdminScreen.route){
                        launchSingleTop=true
                    }
                }
            )
        }


        composable(
            route = Screen.CollegeListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            val collegeVM: CollegeScreenVM = koinViewModel()

            CollegeListScreen(
                collegeScreenVM = collegeVM,
                onBackClick = {},
                onCardClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                    }
                }
            )

        }

        composable(
            route = Screen.CsvPreviewScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

                CsvPreviewScreen(
                    filePickerViewModel = filePickerVM,
                    csvUploadViewModel = csvUploadViewModel,
                    onBackClick = {  navController.popBackStack()},
                    onUploadSuccess = { navController.navigate(Screen.CsvPreviewResScreen.route) },
                )

        }

        composable (
            route=Screen.CsvPreviewResScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            CsvPreviewResScreen(
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
            route=Screen.EditStudentScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            EditStudentScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                studentDetailScreenVM = studentDetailVM,
                editStudentVM =editStudentVM
            )
        }

        composable (
            route=Screen.AddCollegeAdminScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
           AddCollegeAdminScreen(
               onBackBtnClick ={navController.popBackStack()}
           )
        }


        composable (
            route=Screen.AddStudentManuallyScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            val addStudentManuallyVM: AddStudentManuallyVM=koinViewModel()
            AddStudentManuallyScreen(
                onBackBtnClick ={
                    navController.popBackStack()
                },
                addStudentManuallyVM = addStudentManuallyVM,
                navController = navController
            )
        }

        composable (
            route=Screen.HomeScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            HomeScreen(
                onRatingCardClick = {},
                onBillCardClick = {},
                onNoticeCardClick = {},
                onMessCardClick = {
                    navController.navigate(Screen.MessListScreen.route)
                }
            )
        }
    }
}