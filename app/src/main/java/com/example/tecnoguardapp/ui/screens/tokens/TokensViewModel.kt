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
import com.example.tecnoguardapp.data.responses.Tokens.get.TokensData
import com.example.tecnoguardapp.ui.screens.LoadingManager
import com.example.tecnoguardapp.utils.DataStoreManager
import com.example.tecnoguardapp.utils.formateDate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TokensViewModel @Inject constructor(
    @Named("business") private val businessApiClient: BusinessApiClient,
    private val dataStoreManager: DataStoreManager,
    @ApplicationContext private val context: Context,
    val loadingManager: LoadingManager
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

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    private val _showTable = MutableLiveData<Boolean>()
    val showTable: LiveData<Boolean> = _showTable

    private val _listTokens = MutableLiveData<List<TokensData>>()
    val listTokens: LiveData<List<TokensData>> = _listTokens


    fun closeTable() {
        _showTable.value = false
    }

    fun onOptionChange(value: String) {
        _selectedOption.value = value
    }

    fun onChangeTokenName(value: String) {
        _tokenName.value = value
        _tokenName.value?.length?.let {
            _isButtonEnabled.value = it > 5
        }
    }

    fun closeModal() {
        _showModal.value = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createToken() {
        loadingManager.showLoading()
        _isButtonEnabled.value = false
        if (_tokenName.value != null && _selectedOption.value != null && _tokenName.value != "" && _selectedOption.value != "") {
            try {
                val acceso = CreateToken(_tokenName.value!!, _selectedOption.value!!.lowercase())
                val token = dataStoreManager.getAccessToken()
                val request = businessApiClient.crearAcceso("Bearer $token", acceso)
                if (request.isSuccessful) {
                    _tokenCode.value = request.body()?.data?.valor
                    _expirationDate.value = formateDate(request.body()?.data!!.fecha_expiracion)
                    _tokenName.value = ""
                    _selectedOption.value = ""
                    _showModal.value = true
                    loadingManager.hideLoading()
                } else {
                    loadingManager.hideLoading()
                    val errorMsg = request.errorBody()?.string() ?: "Error desconocido"
                    Log.e("CrearToken", "Error: $errorMsg")
                    Toast.makeText(context, "Ocurrió un error: $errorMsg", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (err: Exception) {
                loadingManager.hideLoading()
                Toast.makeText(context, "Error al crear el token!", Toast.LENGTH_SHORT).show()
                Log.d("CrearToken", err.message!!)
            }
        } else {
            loadingManager.hideLoading()
            Toast.makeText(context, "Llene los datos porfavor", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun obtenerTokens() {
        loadingManager.showLoading()
        try {
            val token = dataStoreManager.getAccessToken()
            val request = businessApiClient.obtenerAccesos("Bearer $token")
            if (request.isSuccessful) {
                _listTokens.value = request.body()?.data
                loadingManager.hideLoading()
                if (_listTokens.value!!.isEmpty()) {
                    Toast.makeText(context, "No hay tokens!", Toast.LENGTH_SHORT).show()
                } else {
                    _showTable.value = true
                }
            }
        } catch (err: Exception) {
            loadingManager.hideLoading()
            _showTable.value = false
            Toast.makeText(context, "Error al ver los datos!", Toast.LENGTH_SHORT).show()
            Log.d("CrearToken", err.message!!)
        }
    }

}