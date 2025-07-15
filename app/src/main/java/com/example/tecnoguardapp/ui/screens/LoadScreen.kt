package com.example.tecnoguardapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadScreen() {
    Box(Modifier
        .fillMaxSize()
        .background(color = Color.Black.copy(alpha = 0.3f))) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}