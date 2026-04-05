package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenVM : ViewModel(){
    private val _isMessClosurePart= MutableStateFlow<Boolean>(false)
    val isMessClosurePart=_isMessClosurePart.asStateFlow()

    fun updateIsMessClosure(isMessClosurePart: Boolean){
        _isMessClosurePart.value=isMessClosurePart
    }


}