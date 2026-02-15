package com.messon.faculty.app.core


import com.messon.faculty.app.domain.dataModel.PlatformFile
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UIKit.*
import kotlin.coroutines.resume
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.darwin.NSObject
import platform.Foundation.NSURL
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfURL
import platform.posix.memcpy


actual class FilePicker actual constructor() {

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun pickCsvFile(): PlatformFile? =
        suspendCancellableCoroutine { cont ->

            val picker = UIDocumentPickerViewController(
                documentTypes = listOf("public.comma-separated-values-text"),
                inMode = UIDocumentPickerMode.UIDocumentPickerModeImport
            )

            val delegate = object : NSObject(), UIDocumentPickerDelegateProtocol {

                override fun documentPicker(
                    controller: UIDocumentPickerViewController,
                    didPickDocumentsAtURLs: List<*>
                ) {
                    val url = didPickDocumentsAtURLs.firstOrNull() as? NSURL
                    if (url == null) {
                        cont.resume(null)
                        return
                    }

                    val data = NSData.dataWithContentsOfURL(url)
                    val bytes = data?.toByteArray()

                    cont.resume(
                        PlatformFile(
                            name = url.lastPathComponent ?: "file.csv",
                            bytes = bytes ?: ByteArray(0)
                        )
                    )
                }
            }

            picker.delegate = delegate

            val root =
                UIApplication.sharedApplication.keyWindow?.rootViewController

            root?.presentViewController(picker, true, null)
        }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val bytes = ByteArray(length.toInt())
    memcpy(bytes.refTo(0), this.bytes, length)
    return bytes
}
