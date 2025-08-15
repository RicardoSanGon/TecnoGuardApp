package com.example.tecnoguardapp.ui.screens

import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tecnoguardapp.utils.ErrorManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ErrorViewModel @Inject constructor(
    private val errorManager: ErrorManager
): ViewModel(){
    val isAnError: StateFlow<Boolean> = errorManager.isAnError
    val errorMessage: StateFlow<String> = errorManager.errorMessage

    fun closeModal() = errorManager.closeModal()
}