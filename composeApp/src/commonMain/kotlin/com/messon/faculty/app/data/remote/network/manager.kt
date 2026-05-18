package com.messon.faculty.app.data.remote.network


import co.touchlab.kermit.Logger
import com.messon.faculty.app.data.remote.dto.ManagerCreateReqDto
import com.messon.faculty.app.data.remote.dto.ManagerCreateResDto
import com.messon.faculty.app.utils.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.messon.faculty.app.utils.Result
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody

private const val BASE_URL ="https://messon2-0-backend-testingv1.onrender.com/"

class ManagerApi(
    private val httpClient: HttpClient
){
    val Log = Logger.withTag("CMP_LOG")

    suspend fun createManager(
        token:String,
        messId: String,
        managerCreateReqDto: ManagerCreateReqDto
    ): Result<ManagerCreateResDto, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"in login block"})

        val response=try {
            httpClient.post(
                urlString = "${BASE_URL}mess/${messId}/admins"
            ) {

                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(managerCreateReqDto)
            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<ManagerCreateResDto>()
                Result.Success(mess)
            }
            401->Result.Error(NetworkError.UNAUTHORIZED)
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun fetchAllManagers(
        messId: String,
        token:String,
    ): Result<List<ManagerCreateResDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"in login block"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/mess-admins/${messId}"
            ) {

                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<List<ManagerCreateResDto>>()
                Result.Success(mess)
            }
            401->Result.Error(NetworkError.UNAUTHORIZED)
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}