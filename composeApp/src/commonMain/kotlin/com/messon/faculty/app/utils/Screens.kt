package com.messon.faculty.app.utils



sealed class Screen(val route: String) {

    object HomeScreen: Screen("home_screen")
    object LoginScreen: Screen("login_screen")

    object SendNotificationScreen: Screen(route ="send_notification_screen")
    object CsvPreviewScreen: Screen(route="csv_preview_screen")
    object StudentScreen: Screen(route ="student_screen")
    object AddStudentManuallyScreen: Screen(route ="add_student_manually_screen")

    object StudentDetailScreen: Screen(route ="student_detail_screen")

    object CsvPreviewResScreen: Screen(route ="csv_preview_res_screen")

    object DataValidityScreen: Screen(route = "data_validity_screen")

    object EditStudentScreen: Screen(route = "edit_student_screen")

    object DashboardScreen: Screen(route = "dashboard_screen")

    object MessListScreen: Screen(route = "mess_list_screen")

    object MessManagerListScreen: Screen(route = "mess_manager_list_screen")

    object AddMessScreen: Screen(route = "add_mess_screen")


    object AddManagerScreen: Screen(route = "add_manager_screen")

    object CollegeAdminScreen: Screen(route = "college_admin_screen")

    object CollegeListScreen: Screen(route = "college_list_screen")

    object AddCollegeAdminScreen: Screen(route = "add_college_admin_screen")
    object MessSettingScreen: Screen(route = "mess_setting_screen")

    object HolidaysListScreen: Screen(route = "holidays_list_screen")
    object AddHolidayScreen: Screen(route = "add_holiday_screen")

    object BillingMessListScreen: Screen("billing_mess_list_screen")

    object MealPriceScreen: Screen("meal_price_screen")

    object MessBillScreen:Screen("mess_bill_screen")
    object StudentBillScreen:Screen("student_bill_screen")

    object ViewComplaintScreen:Screen("view_complaint_screen")

}

