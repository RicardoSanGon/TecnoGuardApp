package com.example.tecnoguardapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tecnoguardapp.ui.screens.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.tecnoguardapp.core.NavigationWrapper
import com.example.tecnoguardapp.ui.theme.TecnoGuardAppTheme
import com.example.tecnoguardapp.utils.AuthViewModel
import com.example.tecnoguardapp.utils.Constants.REDIRECT_URI

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
        enableEdgeToEdge()
        setContent {
            TecnoGuardAppTheme {
                NavigationWrapper()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val uri = intent?.data
        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            val code = uri.getQueryParameter("code")
            Log.d("AUTH_CALLBACK", "URI: $uri")
            Log.d("AUTH_CALLBACK", "Code: $code")
            if (code != null) {
                authViewModel.getToken(code)
            } else {
                Log.e("AUTH_CALLBACK", "No se recibió el código. URI incompleta.")
            }
        }
    }
}





