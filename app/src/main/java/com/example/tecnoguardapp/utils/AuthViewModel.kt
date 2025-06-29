package com.example.tecnoguardapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnoguardapp.data.network.TecnoGuardApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthClient
import com.example.tecnoguardapp.utils.Constants.CODE_VERIFIER
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager: DataStoreManager,
    @Named("auth") private val authApi: TecnoGuardAuthClient
): ViewModel() {

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
                Toast.makeText(context, "Token recibido ${tokenResponse.accessToken}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Log.e("TOKEN_ERROR", "Error al obtener el token: ${e.message}")
            }
        }
    }
}