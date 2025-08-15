package com.example.tecnoguardapp.utils

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorManager @Inject constructor() {
    private val _isAnError = MutableStateFlow(false)
    val isAnError: StateFlow<Boolean> = _isAnError

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    fun showModal() {
        _isAnError.value = true
    }

    fun closeModal() {
        _isAnError.value = false
        _errorMessage.value = ""
    }

    suspend fun emitError(message: String) {
        Log.d("EMITERROR", message)
        _errorFlow.emit(message)
        _errorMessage.value = message
        showModal()
    }


}