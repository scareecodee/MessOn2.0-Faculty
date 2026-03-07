package com.messon.faculty.app.domain.repository

import com.messon.faculty.app.data.remote.network.ManagerApi
import com.messon.faculty.app.domain.dataModel.ManagerCreateReq
import com.messon.faculty.app.domain.dataModel.ManagerCreateRes
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.presentation.mapper.toDto
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result

class ManagerRepository(
    private val managerApi: ManagerApi
) {
    suspend fun addManager(
        token: String,
        managerCreateReq: ManagerCreateReq
    ): Result<ManagerCreateRes, NetworkError> {

        return when (val result =managerApi.createManager(token=token, managerCreateReqDto = managerCreateReq.toDto())) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun fetchAllManagers(
        token: String,
    ): Result<List<ManagerCreateRes>, NetworkError> {

        return when (val result = managerApi.fetchAllManagers(token=token)) {

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
