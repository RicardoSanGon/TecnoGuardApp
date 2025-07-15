package com.example.tecnoguardapp.data.network

import com.example.tecnoguardapp.data.responses.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
interface TecnoGuardApiClient {
    @GET("user")
    suspend fun getMyData(@Header("Authorization") token: String): Response<UserResponse>
}