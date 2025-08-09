package com.example.tecnoguardapp.data.responses.Family_Members.store

data class Data(
    val created_at: String,
    val direccion: Any,
    val direccion_verified: Any,
    val email: String,
    val email_verified_at: String,
    val family_group_isactive: Any,
    val family_id: Int,
    val id: Int,
    val is_active: Boolean,
    val name: String,
    val phone: String,
    val role_id: Int,
    val two_factor_enabled: Boolean,
    val updated_at: String
)