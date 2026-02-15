package com.messon.faculty.app.core
import com.messon.faculty.app.domain.dataModel.PlatformFile

expect class FilePicker() {
    suspend fun pickCsvFile(): PlatformFile?
}


