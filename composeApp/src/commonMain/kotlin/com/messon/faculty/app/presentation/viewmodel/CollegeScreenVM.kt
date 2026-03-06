package com.messon.faculty.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.domain.dataModel.College
import com.messon.faculty.app.domain.repository.CollegeRepository
import com.messon.faculty.app.presentation.ui.uiState.FetchCollegesState
import com.messon.faculty.app.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CollegeScreenVM(
    val collegeRepository: CollegeRepository
)
    : ViewModel() {


    private val _uiState = MutableStateFlow<FetchCollegesState>(FetchCollegesState.Idle)

    var uiState: StateFlow<FetchCollegesState> = _uiState.asStateFlow()

    fun selectCollege(college: College) {
        collegeRepository.selectCollege(college)
    }

    fun clearSelectedMess(){
        clearSelectedMess()
    }

    fun fetchColleges(
    ){
        if(_uiState.value is FetchCollegesState.Loading) return
        viewModelScope.launch {

            _uiState.value = FetchCollegesState.Loading
            when (val result =collegeRepository.fetchColleges()) {

                is com.messon.faculty.app.utils.Result.Success -> {


                    _uiState.value = FetchCollegesState.Success(
                       colleges = result.data
                    )

                }

                is Result.Error -> {

                    _uiState.value = FetchCollegesState.Error(
                        message = result.error.name
                    )
                }
            }
        }
    }

    fun clearCollegeFetchState(){
        _uiState.value= FetchCollegesState.Idle
    }

}