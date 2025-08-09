package com.example.tecnoguardapp.data.responses.User

import com.example.tecnoguardapp.data.responses.RolData

data class UserData(
    val email: String?,
    val email_verified_at: String?,
    val family_id: Int?,
    val id: Int?,
    val is_active: Boolean?,
    val name: String?,
    val phone: String?,
    val direccion: String?,
    val membership_is_active: Boolean,
    val role: RolData,
    val family_group: FamilyGroupData
)