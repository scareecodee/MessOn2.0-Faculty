package com.messon.faculty.app.utils


sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object LoginScreen: Screen("login_screen")

    object RegisterComplainScreen: Screen(route = "register_complaint_screen")

    object NotificationScreen: Screen(route ="notification_screen")
    object CsvPreviewScreen: Screen(route="csv_preview_screen")
    object StudentScreen: Screen(route ="student_screen")
    object AddStudentManuallyScreen: Screen(route ="add_student_manually_screen")

    object StudentDetailScreen: Screen(route ="student_detail_screen")

    object CsvPreviewResScreen: Screen(route ="csv_preview_res_screen")

    object DataValidityScreen: Screen(route = "data_validity_screen")

}