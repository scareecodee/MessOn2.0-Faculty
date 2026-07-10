package com.messon.faculty.app.data.remote.network
import co.touchlab.kermit.Logger
import com.messon.faculty.app.data.remote.dto.FetchComplaintRespDto
import com.messon.faculty.app.data.remote.dto.UpdateComplaintStatusDto
import com.messon.faculty.app.utils.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import  com.messon.faculty.app.utils.Result
import io.ktor.client.request.patch
import io.ktor.client.request.setBody

private const val BASE_URL = "https://demo.messon.online/"

private val Log = Logger.withTag("CMP_COMPLAINTS")

class ComplaintApi(
    private val httpClient: HttpClient
){

    suspend fun fetchComplaints(
        token: String,
        cursor: String?,
        search: String?,
        messId: String
    ):Result<FetchComplaintRespDto, NetworkError> {
        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch-complaints"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                parameter(
                    "cursor",cursor
                )
                parameter(
                    "search",search
                )
                parameter(
                    "mess_id",messId
                )
            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val complaints=response.body<FetchComplaintRespDto>()
                Log.d(tag = "Ktor", message = {complaints.complaints.toString()})

                Result.Success(complaints)
            }
            401-> Result.Error(NetworkError.UNAUTHORIZED)
            403-> Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun updateComplaintStatus(
        token: String,
        updateComplaintStatusDto: UpdateComplaintStatusDto
    ):Result<UpdateComplaintStatusDto, NetworkError> {
        val response=try {
            httpClient.patch(
                urlString = "${BASE_URL}complaint/update-status"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(
                    updateComplaintStatusDto
                )

            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val complaints=response.body<UpdateComplaintStatusDto>()
                Result.Success(complaints)
            }
            401-> Result.Error(NetworkError.UNAUTHORIZED)
            403-> Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }

}