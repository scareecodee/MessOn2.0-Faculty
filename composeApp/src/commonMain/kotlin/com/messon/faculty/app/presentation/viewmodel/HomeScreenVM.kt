package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenVM : ViewModel(){
    private val _screenRoute= MutableStateFlow<String?>(null)
    val screenRoute=_screenRoute.asStateFlow()

    fun updateScreenRoute(screenRoute: String){
        _screenRoute.value=screenRoute
    }

}