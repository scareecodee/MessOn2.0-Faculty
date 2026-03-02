package com.messon.faculty.app.data.remote.network


import co.touchlab.kermit.Logger
import com.messon.faculty.app.data.remote.dto.PreviewImportResponseDto
import com.messon.faculty.app.domain.dataModel.PlatformFile
import com.messon.faculty.app.utils.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.header
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.messon.faculty.app.utils.Result


private const val BASE_URL = "http://172.18.9.63:8000/"

class CsvUpload(
    private val httpClient: HttpClient
) {

    private val log = Logger.withTag("CSV_UPLOAD")

    suspend fun uploadCsv(
//        token: String,
        file: PlatformFile
    ): Result<PreviewImportResponseDto, NetworkError> {

//        val cleanToken = token.trim()

        return try {

            val response = httpClient.submitFormWithBinaryData(
                url = "${BASE_URL}admin/students/import/preview",
                formData = formData {


                    append("mode", "insert")


                    append(
                        key = "file",
                        value = file.bytes,
                        headers = Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "form-data; name=\"file\"; filename=\"${file.name}\""
                            )
                            append(HttpHeaders.ContentType, "text/csv")
                        }
                    )
                }
            )
//            {
//                header(HttpHeaders.Authorization, "Bearer $cleanToken")
//            }

            log.d { "STATUS CODE = ${response.status}" }

            when (response.status.value) {

                in 200..299 -> {
                    val body = response.body<PreviewImportResponseDto>()
                    Result.Success(body)
                }

                401 -> Result.Error(NetworkError.UNAUTHORIZED)
                403 -> Result.Error(NetworkError.FORBIDDEN)
                404 -> Result.Error(NetworkError.NOT_FOUND)
                409 -> Result.Error(NetworkError.CONFLICT)
                408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
                in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)

                else -> Result.Error(NetworkError.UNKNOWN)
            }

        } catch (e: UnresolvedAddressException) {
            Result.Error(NetworkError.NO_INTERNET)

        } catch (e: SerializationException) {
            Result.Error(NetworkError.SERIALIZATION)

        } catch (e: Exception) {
            log.e { "Error: ${e.message}" }
            Result.Error(NetworkError.UNKNOWN)
        }
    }
}