package com.example.tecnoguardapp.data.responses.Tokens.get

data class GetTokens(
    val `data`: List<TokensData>,
    val message: String,
    val status: Boolean
)