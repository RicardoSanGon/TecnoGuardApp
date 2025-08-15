package com.example.tecnoguardapp.data.responses.User

data class Cerrada(
    val configuration_pay_date: Int,
    val created_at: String,
    val description: String,
    val group_name: String,
    val guard_id: Any,
    val id: Int,
    val jefe_cerrada_id: Int,
    val updated_at: String,
    val localidades_entradas: List<LocalidadesEntrada>
)