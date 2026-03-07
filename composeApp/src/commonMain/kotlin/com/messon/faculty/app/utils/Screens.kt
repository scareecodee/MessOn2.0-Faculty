package com.messon.faculty.app.utils



sealed class Screen(val route: String) {


    object HomeScreen: Screen("home_screen")
    object LoginScreen: Screen("login_screen")

    object NotificationScreen: Screen(route ="notification_screen")
    object CsvPreviewScreen: Screen(route="csv_preview_screen")
    object StudentScreen: Screen(route ="student_screen")
    object AddStudentManuallyScreen: Screen(route ="add_student_manually_screen")

    object StudentDetailScreen: Screen(route ="student_detail_screen")  // ---> register in AppNav

    object CsvPreviewResScreen: Screen(route ="csv_preview_res_screen")

    object DataValidityScreen: Screen(route = "data_validity_screen") // ---> register in AppNav

    object EditStudentScreen: Screen(route = "edit_student_screen")

    object DashboardScreen: Screen(route = "dashboard_screen")

    object MessListScreen: Screen(route = "mess_list_screen")

    object MessManagerListScreen: Screen(route = "mess_manager_list_screen")

    object AddMessScreen: Screen(route = "add_mess_screen")


    object AddManagerScreen: Screen(route = "add_manager_screen")

    object CollegeAdminScreen: Screen(route = "college_admin_screen")

    object CollegeListScreen: Screen(route = "college_list_screen")

    object AddCollegeAdminScreen: Screen(route = "add_college_admin_screen")


}

