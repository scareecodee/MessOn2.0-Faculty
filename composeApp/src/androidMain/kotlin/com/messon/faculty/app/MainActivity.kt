package com.messon.faculty.app

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.messon.faculty.app.core.FilePickerManager
import com.messon.faculty.app.domain.dataModel.PlatformFile

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
            App()
        }
    }
}



object FilePickerContinuationHolder {
    var continuation: ((PlatformFile?) -> Unit)? = null
}