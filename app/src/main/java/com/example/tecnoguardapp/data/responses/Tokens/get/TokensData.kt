package com.example.tecnoguardapp.data.responses.Tokens.get

data class TokensData(
    val created_at: String,
    val fecha_expiracion: String,
    val id: Int,
    val nombre: String,
    val puerta: String,
    val tipo_token: String,
    val updated_at: String,
    val usos: Int,
    val usuario_id: Int,
    val valor: String
)