package com.example.tecnoguardapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnoguardapp.data.network.BusinessApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthClient
import com.example.tecnoguardapp.utils.Constants.CLIENT_ID
import com.example.tecnoguardapp.utils.Constants.CODE_VERIFIER
import com.example.tecnoguardapp.utils.Constants.REDIRECT_URI
import com.example.tecnoguardapp.utils.Constants.resetCodeVerifier
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @Named("auth") private val authApi: TecnoGuardAuthClient,
    @Named("api") private val authApiClient: TecnoGuardAuthApiClient,
    @Named("business") private val businessApiClient: BusinessApiClient,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _navigateToDashboard = MutableStateFlow(false)
    val navigateToDashboard: StateFlow<Boolean> = _navigateToDashboard

    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin: StateFlow<Boolean> = _navigateToLogin


    suspend fun getUserData() {
        val userData = dataStoreManager.getUserData()
        if (userData == null) {
            try {
                val token = dataStoreManager.getAccessToken()
                val request = businessApiClient.getMyData("Bearer $token")
                if (request.isSuccessful) {
                    dataStoreManager.saveUserData(request.body()?.data)
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Hubo un error al obtener los datos!", Toast.LENGTH_LONG)
                    .show()
                Log.e("GETUSERDATA", e.message!!)
            }
        }
    }


    fun getToken(code: String) {
        viewModelScope.launch {
            try {
                val tokenResponse = authApi.getToken(
                    clientId = CLIENT_ID,
                    redirectUri = REDIRECT_URI,
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
            val request = businessApiClient.getMyData("Bearer $token")
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

    suspend fun logout() {
        try {
            val accessToken = dataStoreManager.getAccessToken()
            authApiClient.logout("Bearer $accessToken")
        } catch (e: Exception) {
            Toast.makeText(context, "Hubo un error inesperado al cerrar sesión", Toast.LENGTH_LONG).show()
            Log.e("LOGOUT", e.message!!)
        } finally {
            resetCodeVerifier()
            dataStoreManager.clear()
            _navigateToLogin.value = true
        }
    }

    fun resetNavigateToLogin() {
        _navigateToLogin.value = false
    }

    fun resetNavigateToDashboard() {
        _navigateToDashboard.value = false
    }
}