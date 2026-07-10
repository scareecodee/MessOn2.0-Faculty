package com.messon.faculty.app.domain.repository
import com.messon.faculty.app.data.remote.network.ComplaintApi
import com.messon.faculty.app.data.remote.network.MessApi
import com.messon.faculty.app.domain.dataModel.BillReq
import com.messon.faculty.app.domain.dataModel.FetchComplaintsRes
import com.messon.faculty.app.domain.dataModel.Holiday
import com.messon.faculty.app.domain.dataModel.HolidayRes
import com.messon.faculty.app.domain.dataModel.MessCreateReq
import com.messon.faculty.app.domain.dataModel.MessCreateRes
import com.messon.faculty.app.domain.dataModel.StudentBillingData
import com.messon.faculty.app.domain.dataModel.UpdateComplaintStatus
import com.messon.faculty.app.presentation.mapper.toDTo
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.presentation.mapper.toDto
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result

class MessRepository(
    private val messApi: MessApi,
    private val complaintApi: ComplaintApi
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

    suspend fun createHoliday(
        token: String,
        holiday: Holiday,
        messId: String
    ): Result<HolidayRes, NetworkError> {

        return when (val result = messApi.createHoliday(
            token = token,
            holiday = holiday.toDTo(),
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

    suspend fun fetchHolidays(
        token: String,
        messId: String
    ): Result<List<HolidayRes>, NetworkError> {

        return when (val result = messApi.fetchHolidays(
            token = token,
            messId = messId
        )) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map { it.toDomain() }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun fetchMessBill(
        token: String,
        messId: String,
        billingReq: BillReq
    ): Result<List<StudentBillingData>, NetworkError> {

        return when (val result = messApi.fetchMessBill(
            token = token,
            messId = messId,
            billReqDto =billingReq.toDto()
        )) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map { it.toDomain() }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun downloadMessBillCsv(
        token: String,
        messId: String,
        billingReq: BillReq
    ): Result<ByteArray, NetworkError> {

        return when (val result = messApi.downloadMessBillCsv(
            token = token,
            messId = messId,
            billReqDto =billingReq.toDto()
        )) {

            is Result.Success -> {

                val data = result.data


                Result.Success(
                    data =data
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun fetchComplaints(
        token: String,
        cursor: String?,
        search: String?,
        messId: String
    ): Result<FetchComplaintsRes, NetworkError> {

        return when (
            val result =complaintApi.fetchComplaints(
                token = token,
                cursor = cursor,
                search = search,
                messId = messId,
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }


    suspend fun updateComplaintStatus(
        updateComplaintStatus: UpdateComplaintStatus,
        token: String
    ): Result<UpdateComplaintStatus, NetworkError> {

        return when (
            val result =complaintApi.updateComplaintStatus(
                token = token,
                updateComplaintStatusDto =updateComplaintStatus.toDto()
            )
        ) {
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



