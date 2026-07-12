package com.messon.faculty.app.presentation.ui.navigation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.ViewModel
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
import com.messon.faculty.app.theme.MessonColors
import com.messon.faculty.app.theme.MessonTheme
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
    val addStudentManuallyVM: AddStudentManuallyVM=koinViewModel()
    val homeScreenVM: HomeScreenVM=koinViewModel()
    val messClosureVM: MessClosureVM=koinViewModel()
    val messBillVM: MessBillVM=koinViewModel()
    val studentBillVM: StudentBillVM=koinViewModel()
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
                color = MaterialTheme.colorScheme.primary,
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
                },
                messListVM = messListVM
            )
        }


        composable(
            route = Screen.LoginScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight,

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
                navController = navController,
                homeScreenVM = homeScreenVM,
            )
        }


        composable(
            route = Screen.MessManagerListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            val managerListVM: ManagerListVM=koinViewModel()
            MessManagerListScreen(
                onBackClick = { navController.popBackStack() },
                onFabClick = {
                    navController.navigate(Screen.AddManagerScreen.route) {
                        launchSingleTop = true
                    }
                },
                onCardClick = {},
                managerListVM = managerListVM,
                messListVM = messListVM,
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

            val addManagerVM: AddManagerVM=koinViewModel()
            AddManagerScreen(
                onBackBtnClick = {
                    navController.popBackStack()
                },
                messListVM =messListVM,
                addManagerVM = addManagerVM
            )
        }


        composable(
            route = Screen.CollegeAdminScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            val adminVM: AdminVM=koinViewModel()
            CollegeAdminScreen(
                onBackClick = { navController.popBackStack() },
                onFabClick = {
                    navController.navigate(Screen.AddCollegeAdminScreen.route) {
                        launchSingleTop = true
                    }
                },
                adminVM =adminVM
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
                    onBackClick = { navController.popBackStack() },
                    onUploadSuccess = { navController.navigate(Screen.CsvPreviewResScreen.route) },
                    messListVM = messListVM,
                    messonColors = MessonTheme.colors,
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
                csvUploadViewModel = csvUploadViewModel,
                messListVM = messListVM,
                messonColors = MessonTheme.colors
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
                editStudentVM = editStudentVM,
                onConflict = { navController.navigate(Screen.DataValidityScreen.route) },
                messonColors = MessonTheme.colors
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
            AddStudentManuallyScreen(
                onBackBtnClick = {
                    navController.popBackStack()
                },
                addStudentManuallyVM = addStudentManuallyVM,
                navController = navController,
                messListVM = messListVM
            )
        }

        composable (
            route=Screen.HomeScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            HomeScreen(
                onBillCardClick = {
                    navController.navigate(Screen.BillingMessListScreen.route)
                },
                onNoticeCardClick = {
                    homeScreenVM.updateScreenRoute(Screen.SendNotificationScreen.route)
                    navController.navigate(Screen.MessListScreen.route)
                },
                onMessCardClick = {
                    homeScreenVM.updateScreenRoute(screenRoute = Screen.DashboardScreen.route)
                    navController.navigate(Screen.MessListScreen.route)
                },
                onAdminCardClick = {
                    navController.navigate(Screen.CollegeAdminScreen.route)
                },
                onClosureCardClick = {
                    homeScreenVM.updateScreenRoute(Screen.HolidaysListScreen.route)
                    navController.navigate(Screen.MessListScreen.route)
                },
                onFeedbackCardClick = {
                    homeScreenVM.updateScreenRoute(Screen.ViewComplaintScreen.route)
                    navController.navigate(Screen.MessListScreen.route)
                },
                messonColors = MessonTheme.colors
            )
        }

        composable (
            route=Screen.StudentDetailScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            StudentDetailScreen(
                onBackClick = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate(Screen.EditStudentScreen.route)
                },
                studentDetailScreenVM = studentDetailVM,
                studentBillVM = studentBillVM,
                navController = navController
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
               addStudentManuallyVM = addStudentManuallyVM,
           )
        }

        composable (
            route=Screen.MessSettingScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            MessSettingScreen(
                onBackClick = {navController.popBackStack()}
            )
        }

        composable (
            route=Screen.HolidaysListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
           MessHolidaysScreen(
               onBackBtnClick = { navController.popBackStack() },
               onAddIconClick = {
                   navController.navigate(Screen.AddHolidayScreen.route)
               },
               messClosureVM = messClosureVM,
               messListVM = messListVM
           )
        }

        composable (
            route=Screen.AddHolidayScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
           CreateHolidayScreen(
               onBackBtnClick = {navController.popBackStack()},
               messClosureVM=messClosureVM,
               messListVM = messListVM
           )
        }

        composable (
            route=Screen.BillingMessListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
           BillingMessListScreen(
               navController = navController,
               onBackBtnClick = { navController.popBackStack() },
               messListVM = messListVM,
               onPriceClick = { navController.navigate(Screen.MealPriceScreen.route) },
               messBillVM = messBillVM,
           )
        }

        composable (
            route=Screen.MealPriceScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            MealPriceScreen(
                onBackBtnClick = {navController.popBackStack()}
            )
        }

        composable (
            route=Screen.MessBillScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            MessBillScreen(
                onBackBtnClick ={navController.popBackStack()},
                messBillVM =messBillVM
            )
        }

        composable (
            route=Screen.StudentBillScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            StudentBillScreen(
                onBackClick = {navController.popBackStack()},
                studentBillVM=studentBillVM
            )
        }

        composable (
            route=Screen.SendNotificationScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
           BroadcastNotificationScreen(
               onViewHistoryClick = {},
               messonColors = MessonTheme.colors
           )
        }
        composable (
            route=Screen.ViewComplaintScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            val fetchComplaintsVM: FetchComplaintsVM=koinViewModel()
            ViewComplaintsScreen(
                onBackClick = { navController.popBackStack() },
                fetchComplaintsVM = fetchComplaintsVM,
                messListVM = messListVM
            )
        }
        
    }
}