package com.example.tecnoguardapp.data.network

import com.example.tecnoguardapp.data.model.Family_Members.AddMember
import com.example.tecnoguardapp.data.model.OpenDoor
import com.example.tecnoguardapp.data.model.Tokens.CreateToken
import com.example.tecnoguardapp.data.responses.DoorResponse
import com.example.tecnoguardapp.data.responses.Family_Members.delete.DeletedMember
import com.example.tecnoguardapp.data.responses.Family_Members.get.Members
import com.example.tecnoguardapp.data.responses.Family_Members.store.StoreMember
import com.example.tecnoguardapp.data.responses.Tokens.get.GetTokens
import com.example.tecnoguardapp.data.responses.Tokens.store.CreateTokenResponse
import com.example.tecnoguardapp.data.responses.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BusinessApiClient {
    @GET("me")
    suspend fun getMyData(): Response<UserResponse>

    @POST("puerta")
    suspend fun abrirPuerta(
        @Body() data: OpenDoor
    ): Response<DoorResponse>

    @POST("jefe-familia/token")
    suspend fun crearAcceso(
        @Body() data: CreateToken
    ): Response<CreateTokenResponse>

    @GET("jefe-familia/tokens")
    suspend fun obtenerAccesos(): Response<GetTokens>

    @GET("jefe-familia/family-members")
    suspend fun obtenerMiembros(): Response<Members>

    @POST("jefe-familia/family-members")
    suspend fun agregarMiembro(
        @Body() data: AddMember
    ): Response<StoreMember>

    @DELETE("jefe-familia/family-members/{id}")
    suspend fun eliminarMiembro(
        @Path("id") id: Int
    ): Response<DeletedMember>

}