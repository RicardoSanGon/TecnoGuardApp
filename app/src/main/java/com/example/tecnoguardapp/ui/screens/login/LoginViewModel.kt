package com.example.tecnoguardapp.ui.screens.login

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.tecnoguardapp.utils.Constants.URL
import com.example.tecnoguardapp.utils.Constants.buildAuthorizationUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel(){

    fun login(context: Context){
        val authUri = buildAuthorizationUrl()
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, authUri)
    }

    fun register(context: Context){
        val uri = (URL+"register").toUri()
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, uri)
    }
}

