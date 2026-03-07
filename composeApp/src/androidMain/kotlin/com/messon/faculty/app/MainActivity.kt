package com.messon.faculty.app

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.messon.faculty.app.core.FilePickerManager
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.domain.dataModel.PlatformFile
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        FilePickerManager.currentActivity = this


        FilePickerManager.filePickerLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

                val activity = FilePickerManager.currentActivity ?: return@registerForActivityResult
                val continuation = FilePickerContinuationHolder.continuation ?: return@registerForActivityResult

                if (uri == null) {
                    continuation(null)
                    FilePickerContinuationHolder.continuation = null
                    return@registerForActivityResult
                }

                val inputStream = activity.contentResolver.openInputStream(uri)
                val bytes = inputStream?.readBytes() ?: ByteArray(0)
                inputStream?.close()

                val cursor = activity.contentResolver.query(uri, null, null, null, null)
                val name = cursor?.use {
                    val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index != -1) { it.moveToFirst(); it.getString(index) } else "file.csv"
                } ?: "file.csv"

                continuation(PlatformFile(name, bytes))
                FilePickerContinuationHolder.continuation = null
            }


        setContent {
            val activity = LocalActivity.current as Activity
            val googleAuth: GoogleAuth = koinInject { parametersOf(activity) }
            App(
                googleAuth=googleAuth,
            )
        }
    }
}



object FilePickerContinuationHolder {
    var continuation: ((PlatformFile?) -> Unit)? = null
}