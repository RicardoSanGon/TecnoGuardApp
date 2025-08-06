package com.example.tecnoguardapp.ui.screens.tokens

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tecnoguardapp.data.model.Tokens.CreateToken
import com.example.tecnoguardapp.data.network.BusinessApiClient
import com.example.tecnoguardapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TokensViewModel @Inject constructor(
    @Named("business") private val businessApiClient: BusinessApiClient,
    private val dataStoreManager: DataStoreManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _selectedOption = MutableLiveData<String>()
    val selectedOption: LiveData<String> = _selectedOption

    private val _tokenName = MutableLiveData<String>()
    val tokenName: LiveData<String> = _tokenName

    private val _tokenCode = MutableLiveData<String>()
    val tokenCode: LiveData<String> = _tokenCode

    private val _expirationDate = MutableLiveData<String>()
    val expirationDate: LiveData<String> = _expirationDate

    private val _showModal = MutableLiveData<Boolean>()
    val showModal: LiveData<Boolean> = _showModal
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onOptionChange(value: String) {
        _selectedOption.value = value
    }

    fun onChangeTokenName(value: String) {
        _tokenName.value = value
    }

    fun closeModal() {
        _showModal.value = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createToken() {
        _isLoading.value = true
        if (_tokenName.value != null && _selectedOption.value != null) {
            try {
                val acceso = CreateToken(_tokenName.value!!, _selectedOption.value!!.lowercase())
                val token = dataStoreManager.getAccessToken()
                val request = businessApiClient.crearAcceso("Bearer $token", acceso)
                if (request.isSuccessful) {
                    _tokenCode.value = request.body()?.data?.valor
                    val dateTime = ZonedDateTime.parse(request.body()?.data?.fecha_expiracion)
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    _expirationDate.value = dateTime.format(formatter).toString()
                    _showModal.value = true
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                    val errorMsg = request.errorBody()?.string() ?: "Error desconocido"
                    Log.e("CrearToken", "Error: $errorMsg")
                    Toast.makeText(context, "Ocurrió un error: $errorMsg", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (err: Exception) {
                _isLoading.value = false
                Log.d("CrearToken", err.message!!)
            }
        } else {
            _isLoading.value = false
            Toast.makeText(context, "Llene los datos porfavor", Toast.LENGTH_SHORT).show()
        }
    }
}