package com.messon.faculty.app.data.remote.network

import co.touchlab.kermit.Logger
import com.messon.faculty.app.data.remote.dto.AddStudentsManuallyResDto
import com.messon.faculty.app.data.remote.dto.BillReqDto
import com.messon.faculty.app.data.remote.dto.EditStudentResDto
import com.messon.faculty.app.data.remote.dto.FetchStudentsResDto
import com.messon.faculty.app.data.remote.dto.StudentDetailedBillHistoryDto
import com.messon.faculty.app.data.remote.dto.StudentDto
import com.messon.faculty.app.data.remote.dto.StudentInputDto
import com.messon.faculty.app.data.remote.dto.StudentMealDayDto
import com.messon.faculty.app.data.remote.dto.ToggleStatusResDto
import com.messon.faculty.app.utils.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.messon.faculty.app.utils.Result
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType


private const val BASE_URL ="https://messon2-0-backend-testingv1.onrender.com/"
class StudentApi(
    private val httpClient: HttpClient
) {
    private val log = Logger.withTag("CSV_UPLOAD")

    suspend fun addStudentsManually(
        token: String,
        students:List<StudentInputDto>,
        messId: String
    ): Result<AddStudentsManuallyResDto, NetworkError>
    {
        log.d(tag = "Ktor", message ={students.toString()})

        val response= try {
            httpClient.post(
                urlString = "${BASE_URL}messes/${messId}/students/manually"
            ) {
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(students)
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
            in 200..299 , 409->{
                val user=response.body<AddStudentsManuallyResDto>()
                Result.Success(user)
            }
            403->Result.Error(NetworkError.FORBIDDEN)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun toggleStudentStatus(
        token: String,
       uuid: String
    ): Result<ToggleStatusResDto, NetworkError>
    {
        log.d(tag = "Ktor", message ={uuid})

        val response= try {
            httpClient.patch(
                urlString = "${BASE_URL}admin/${uuid}/toggle-activation"
            ) {
                header(HttpHeaders.Authorization, "Bearer $token")
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
                val user=response.body<ToggleStatusResDto>()
                Result.Success(user)
            }
            403->Result.Error(NetworkError.FORBIDDEN)
            404->Result.Error(NetworkError.NOT_FOUND)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun fetchStudents(
        cursor: String?,
        search: String?,
        token: String,
        messId: String
    ): Result<FetchStudentsResDto, NetworkError>
    {
        log.d(tag = "Ktor", message ={"cursor is $cursor"})

        val response= try {
            httpClient.get(
                urlString = "${BASE_URL}admin/fetch-students/${messId}"
            ) {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
                parameter(
                    "cursor",cursor
                )
                parameter(
                    "search",search
                )
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
                val user=response.body<FetchStudentsResDto>()
                Result.Success(user)
            }
            403->Result.Error(NetworkError.FORBIDDEN)
            404->Result.Error(NetworkError.NOT_FOUND)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun editStudent(
        token: String,
        uuid: String,
        updatedStudent: StudentDto
    ): Result<EditStudentResDto, NetworkError>
    {
        log.d(tag = "Ktor", message ={"updating students"})
        log.d(tag = "Ktor", message ={updatedStudent.toString()})

        val response= try {
            httpClient.patch(
                urlString = "${BASE_URL}admin/students/update/${uuid}"
            ) {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(
                    updatedStudent
                )
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
                val user=response.body<EditStudentResDto>()
                Result.Success(user)
            }
            403->Result.Error(NetworkError.FORBIDDEN)
            404->Result.Error(NetworkError.NOT_FOUND)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun fetchMealConsumption(
        token: String,
        uuid: String,
        billRequest: BillReqDto
    ): Result<StudentDetailedBillHistoryDto, NetworkError>
    {
        log.d(tag = "Ktor", message ={"updating students"})
        log.d(tag = "Ktor", message = {uuid})
        val response= try {
            httpClient.post(
                urlString = "${BASE_URL}student/detailed-bill-history"
            ) {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(
                   billRequest
                )
                parameter("student_id",uuid)
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
        log.d(tag = "Ktor", message = {response.body()})

        return when(response.status.value){
            in 200..299->{
                val data=response.body<StudentDetailedBillHistoryDto>()
                Result.Success(data) }
            403->Result.Error(NetworkError.FORBIDDEN)
            404->Result.Error(NetworkError.NOT_FOUND)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}
