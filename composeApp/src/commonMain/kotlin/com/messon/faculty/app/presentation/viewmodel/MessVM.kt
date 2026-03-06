package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.messon.faculty.app.domain.dataModel.Mess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MessVM(
)
    : ViewModel() {

    private val _selectedMess = MutableStateFlow<Mess?>(null)

    var selectedMess: StateFlow<Mess?> = _selectedMess.asStateFlow()



    fun selectMess(mess: Mess){
        _selectedMess.value=mess
    }

    fun clearSelectedMess(){
        _selectedMess.value=null
    }

}