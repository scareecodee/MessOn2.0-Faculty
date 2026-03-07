package com.messon.faculty.app.domain.repository
import com.messon.faculty.app.data.remote.network.MessApi
import com.messon.faculty.app.domain.dataModel.MessCreateReq
import com.messon.faculty.app.domain.dataModel.MessCreateRes
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.presentation.mapper.toDto
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result

class MessRepository(
    private val messApi: MessApi
) {
    suspend fun addMess(
        token: String,
        messCreateReq: MessCreateReq
    ): Result<MessCreateRes, NetworkError> {

        return when (val result = messApi.createMess(token=token,messCreateReq.toDto())) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun fetchAllMess(
        token: String,
    ): Result<List<MessCreateRes>, NetworkError> {

        return when (val result = messApi.fetchAllMess(token=token)) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map{
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

}
