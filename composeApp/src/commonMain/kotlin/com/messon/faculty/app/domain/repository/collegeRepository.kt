package com.messon.faculty.app.domain.repository

import com.messon.faculty.app.data.remote.network.CollegeApi
import com.messon.faculty.app.domain.dataModel.College
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CollegeRepository(
    private val collegeApi: CollegeApi
) {
    private val _selectedCollege = MutableStateFlow<College?>(null)
    val selectedCollege = _selectedCollege.asStateFlow()

    fun selectCollege(college: College) {
        _selectedCollege.value = college
    }

    fun clearSelectedCollege(){
        _selectedCollege.value=null
    }
    suspend fun fetchColleges(
    ): Result<List<College>, NetworkError> {

        return when (val result = collegeApi.fetchColleges()) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map {
                        it.toDomain()
                    }
                )
            }

            is Result.Error -> Result.Error(result.error)
        }
    }



}
