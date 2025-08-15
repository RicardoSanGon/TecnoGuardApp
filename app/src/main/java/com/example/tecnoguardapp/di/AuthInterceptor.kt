package com.example.tecnoguardapp.di

import com.example.tecnoguardapp.utils.DataStoreManager
import com.example.tecnoguardapp.utils.ErrorManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val errorManager: ErrorManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { dataStoreManager.getAccessToken() }
        val requestBuilder = originalRequest.newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        val response = chain.proceed(requestBuilder.build())
        if (response.code() in listOf(400, 401, 403, 422)) {
            val errorBody = response.peekBody(Long.MAX_VALUE).string()
            val errorMessage = try {
                JSONObject(errorBody).optString(
                    "message",
                    "Error ${response.code()}: ${response.message()}"
                )
            } catch (e: Exception) {
                "Error ${response.code()}: ${response.message()}"
            }
            runBlocking {
                errorManager.emitError(errorMessage)
            }
        }
        return response
    }


}