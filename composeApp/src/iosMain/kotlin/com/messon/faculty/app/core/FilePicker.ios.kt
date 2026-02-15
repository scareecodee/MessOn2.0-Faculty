package com.messon.faculty.app.core


import com.messon.faculty.app.domain.dataModel.PlatformFile
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UIKit.*
import kotlin.coroutines.resume
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile
import platform.darwin.NSObject
import platform.Foundation.NSURL

actual class FilePicker actual constructor() {

    private var delegate: DocumentPickerDelegate? = null

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun pickCsvFile(): PlatformFile? = suspendCancellableCoroutine { cont ->

        val vc = UIApplication.sharedApplication.keyWindow?.rootViewController
        if (vc == null) { cont.resume(null); return@suspendCancellableCoroutine }

        val picker = UIDocumentPickerViewController(
            documentTypes = listOf("public.comma-separated-values-text"),
            inMode = UIDocumentPickerMode.UIDocumentPickerModeImport
        )

        delegate = DocumentPickerDelegate { url ->
            if (url == null) { cont.resume(null); return@DocumentPickerDelegate }

            val path = url.path ?: ""
            val name = url.lastPathComponent ?: "file.csv"
            val content = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null) ?: ""
            cont.resume(PlatformFile(name = name, bytes = content.encodeToByteArray()))
        }

        picker.delegate = delegate
        picker.allowsMultipleSelection = false
        vc.presentViewController(picker, true, null)
    }
}

class DocumentPickerDelegate(private val callback: (NSURL?) -> Unit) :
    NSObject(), UIDocumentPickerDelegateProtocol {
    override fun documentPicker(controller: UIDocumentPickerViewController, didPickDocumentsAtURLs: List<*>) {
        val url = didPickDocumentsAtURLs.firstOrNull() as? NSURL
        callback(url)
    }
    override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
        callback(null)
    }
}
