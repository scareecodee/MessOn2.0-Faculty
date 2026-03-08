package com.messon.faculty.app.data.remote.network


import co.touchlab.kermit.Logger
import com.messon.faculty.app.data.remote.dto.ConfirmImportResponseDto
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
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


private const val BASE_URL = "http://172.18.9.63:8000/"

class CsvUpload(
    private val httpClient: HttpClient
) {

    private val log = Logger.withTag("CSV_UPLOAD")

    suspend fun uploadCsv(
        token: String,
        mode: String,
        messId: String,
        file: PlatformFile
    ): Result<PreviewImportResponseDto, NetworkError> {

        val cleanToken = token.trim()

        log.d(tag="mode", message ={mode} )

        return try {

            val response = httpClient.submitFormWithBinaryData(
                url = "${BASE_URL}admin/import/students/preview/${messId}",
                formData = formData {

                    append("mode", mode)
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
            {
                header(HttpHeaders.Authorization, "Bearer $cleanToken")
            }

            log.d (tag = "ktor", message = {"STATUS CODE = ${response.status}"} )

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
            log.d (tag = "ktor", message = {e.message.toString()} )
            Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun saveChanges(
        token: String,
        importToken: String,
        messId: String
    ): Result<ConfirmImportResponseDto, NetworkError>
    {
        log.d(tag = "Ktor", message = {"in save changes block"})

       val response= try {
            httpClient.post(
                urlString = "${BASE_URL}admin/import/students/confirm/${messId}"
            ) {
                header(HttpHeaders.Authorization, "Bearer $token")

                parameter("import_token", importToken)

                contentType(ContentType.Application.Json)
            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val user=response.body<ConfirmImportResponseDto>()
                Result.Success(user)
            }
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}
