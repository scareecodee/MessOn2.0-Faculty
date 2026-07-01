package com.messon.faculty.app.domain.repository
import com.messon.faculty.app.data.remote.network.AdminApi
import com.messon.faculty.app.domain.dataModel.AdminCreateReq
import com.messon.faculty.app.domain.dataModel.AdminCreateRes
import com.messon.faculty.app.domain.dataModel.DeleteUsers
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.presentation.mapper.toDto
import com.messon.faculty.app.presentation.ui.uiState.DeleteUserState
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result

class AdminRepository(
    private val adminApi: AdminApi
) {
    suspend fun addAdmin(
        token: String,
        adminCreateReq: AdminCreateReq
    ): Result<AdminCreateRes, NetworkError> {

        return when (val result =adminApi.createAdmin(token,adminCreateReq.toDto())) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun fetchAllAdmins(
        token: String,
    ): Result<List<AdminCreateRes>, NetworkError> {

        return when (val result = adminApi.fetchAllAdmins(token)) {

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

    suspend fun deleteUsers(
        token: String,
        deleteUsers: DeleteUsers
    ): Result<String, NetworkError> {

        return when (val result =adminApi.deleteUsers(
            token = token,
            deleteUsersDto=deleteUsers.toDto()
        )
        )

        {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

}
