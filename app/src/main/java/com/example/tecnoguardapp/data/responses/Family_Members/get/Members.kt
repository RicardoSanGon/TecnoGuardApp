package com.example.tecnoguardapp.data.responses.Family_Members.get

data class Members(
    val `data`: List<MemberData>,
    val message: String,
    val status: Boolean
)