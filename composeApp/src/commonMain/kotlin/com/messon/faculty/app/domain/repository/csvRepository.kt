package com.messon.faculty.app.domain.repository
import com.messon.faculty.app.data.remote.network.CsvUpload
import com.messon.faculty.app.domain.dataModel.ConfirmImportRes
import com.messon.faculty.app.domain.dataModel.CsvPreviewRes
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result
import io.github.vinceglb.filekit.PlatformFile

class CsvRepository(
    private val csvUpload: CsvUpload
) {

    suspend fun uploadCsv(
        token: String,
        messId: String,
        mode: String,
        file: PlatformFile
    ): Result<CsvPreviewRes, NetworkError> {

        return when (val result = csvUpload.uploadCsv(
            file = file, mode = mode, messId = messId,
            token = token,
        )) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }

            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun saveChanges(
        token: String,
        messId: String,
        importToken: String
    ): Result<ConfirmImportRes, NetworkError> {

        return when (val result = csvUpload.saveChanges(
            importToken = importToken,
            token = token,
            messId = messId
        )) {

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
