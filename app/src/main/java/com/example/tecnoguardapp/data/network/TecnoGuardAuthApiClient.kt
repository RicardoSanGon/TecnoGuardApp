package com.example.tecnoguardapp.data.network

import retrofit2.http.POST

interface TecnoGuardAuthApiClient {
    @POST("logout")
    suspend fun logout()
}