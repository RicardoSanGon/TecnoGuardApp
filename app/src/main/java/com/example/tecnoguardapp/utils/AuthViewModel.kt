package com.example.tecnoguardapp.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnoguardapp.data.network.TecnoGuardApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthClient
import com.example.tecnoguardapp.utils.Constants.CODE_VERIFIER
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager: DataStoreManager,
    @Named("auth") private val authApi: TecnoGuardAuthClient,
    @Named("api") private val apiClient: TecnoGuardApiClient
) : ViewModel() {

    private val _navigateToDashboard = MutableStateFlow(false)
    val navigateToDashboard: StateFlow<Boolean> = _navigateToDashboard

    fun getToken(code: String) {
        viewModelScope.launch {
            try {
                val tokenResponse = authApi.getToken(
                    clientId = "2",
                    redirectUri = "tecnoguard://callback",
                    code = code,
                    codeVerifier = CODE_VERIFIER!!
                )
                dataStoreManager.saveAccessToken(tokenResponse.accessToken)
                _navigateToDashboard.value = true
            } catch (e: Exception) {
                Log.e("TOKEN_ERROR", "Error al obtener el token: ${e.message}")
                _navigateToDashboard.value = false
            }
        }
    }

    suspend fun getData(): Boolean {
        return try {
            val token = dataStoreManager.getAccessToken()
            Log.e("Request ME, token", "$token")
            val request = apiClient.getMyData("Bearer $token")
            if (request.isSuccessful) {
                Log.e("Request ME", "Body recibido: ${request.body()}")
                request.body()?.status ?: false
            } else {
                Log.e("Request ME", "Error body: ${request.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("Request ME", "Error al obtener los datos del usuario: ${e.message}")
            false
        }
    }
}