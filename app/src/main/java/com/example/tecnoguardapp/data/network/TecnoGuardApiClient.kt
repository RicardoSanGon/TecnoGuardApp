package com.example.tecnoguardapp.data.network

import com.example.tecnoguardapp.data.model.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TecnoGuardApiClient {
    @GET("users")
    suspend fun getAllUsers()


}