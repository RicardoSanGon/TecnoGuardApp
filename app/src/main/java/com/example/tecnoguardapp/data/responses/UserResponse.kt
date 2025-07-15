package com.example.tecnoguardapp.data.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data") val data: UserData?,
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: Boolean?
)
