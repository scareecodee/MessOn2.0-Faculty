package com.messon.faculty.app.core

import androidx.activity.ComponentActivity


import androidx.activity.result.ActivityResultLauncher

object FilePickerManager {
    var currentActivity: ComponentActivity? = null
    var filePickerLauncher: ActivityResultLauncher<String>? = null
}
