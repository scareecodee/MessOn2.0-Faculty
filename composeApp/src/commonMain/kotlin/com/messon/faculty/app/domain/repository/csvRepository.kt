package com.messon.faculty.app.domain.repository


import com.messon.faculty.app.data.remote.network.CsvUpload
import com.messon.faculty.app.domain.dataModel.CsvPreviewRes
import com.messon.faculty.app.domain.dataModel.PlatformFile
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result

class CsvRepository(
    private val csvUpload: CsvUpload
) {

    suspend fun uploadCsv(
//        token: String,
        file: PlatformFile
    ): Result<CsvPreviewRes, NetworkError> {

        return when (val result = csvUpload.uploadCsv(file)) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }

            is Result.Error -> Result.Error(result.error)
        }
    }
}
