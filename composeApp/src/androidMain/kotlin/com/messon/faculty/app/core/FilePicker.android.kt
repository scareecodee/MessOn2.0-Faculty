package com.messon.faculty.app.core


import com.messon.faculty.app.domain.dataModel.PlatformFile
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import com.messon.faculty.app.FilePickerContinuationHolder


actual class FilePicker actual constructor() {

    actual suspend fun pickCsvFile(): PlatformFile? =
        suspendCancellableCoroutine { cont ->

            FilePickerContinuationHolder.continuation = { file ->
                cont.resume(file)
            }

            FilePickerManager.filePickerLauncher?.launch("text/csv")
                ?: cont.resume(null)
        }
}
