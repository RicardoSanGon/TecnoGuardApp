package com.example.tecnoguardapp.ui.screens.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    private val _selectedScreen = MutableLiveData<Int>()
    val selectedScreen: LiveData<Int> = _selectedScreen

    fun onScreenChange(screenInt: Int){
        _selectedScreen.value = screenInt
    }
}