package com.example.tecnoguardapp.utils

import android.net.Uri
import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import androidx.core.net.toUri

object Constants {
    val CLIENT_ID = "2"
    val REDIRECT_URI = "tecnoguard://callback"
    val URL = "http://192.168.100.47:8000/"
    val API = "${URL}v1/api/"
    var CODE_VERIFIER: String? = null

    // Paso 1: Genera el code_verifier
    fun generateCodeVerifier(): String {
        val randomBytes = ByteArray(32)
        SecureRandom().nextBytes(randomBytes)
        CODE_VERIFIER = Base64.encodeToString(randomBytes, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
        return CODE_VERIFIER!!
    }

    // Paso 2: Genera el code_challenge (SHA256 del verifier)
    fun generateCodeChallenge(verifier: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(verifier.toByteArray())
        return Base64.encodeToString(digest, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }

    // Paso 3: Genera un state aleatorio
    fun generateRandomState(): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..32).map { charset.random() }.joinToString("")
    }

    fun buildAuthorizationUrl(): Uri {
        val codeVerifier = generateCodeVerifier()
        val codeChallenge = generateCodeChallenge(codeVerifier)
        val state = generateRandomState()

        // Guarda el code_verifier y state para usarlos más adelante (SharedPreferences, ViewModel, etc.)

        return (URL + "oauth/authorize").toUri().buildUpon()
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", "*")
            .appendQueryParameter("state", state)
            .appendQueryParameter("code_challenge", codeChallenge)
            .appendQueryParameter("code_challenge_method", "S256")
            .build()
    }

}