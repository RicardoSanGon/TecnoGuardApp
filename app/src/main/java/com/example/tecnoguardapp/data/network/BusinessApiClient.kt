package com.example.tecnoguardapp.data.network

import com.example.tecnoguardapp.data.model.OpenDoor
import com.example.tecnoguardapp.data.model.Tokens.CreateToken
import com.example.tecnoguardapp.data.responses.DoorResponse
import com.example.tecnoguardapp.data.responses.Tokens.CreateTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BusinessApiClient {
    @POST("puerta")
    suspend fun abrirPuerta(@Header("Authorization") token: String, @Body() data: OpenDoor): Response<DoorResponse>

    @POST("jefe-familia/token")
    suspend fun crearAcceso(@Header("Authorization") token: String, @Body() data: CreateToken): Response<CreateTokenResponse>
}