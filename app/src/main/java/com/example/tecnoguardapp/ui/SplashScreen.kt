package com.example.tecnoguardapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.core.Screen.Home
import com.example.tecnoguardapp.core.Screen.Login
import com.example.tecnoguardapp.core.Screen.Splash
import com.example.tecnoguardapp.ui.theme.InvertLogo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000L)
        val isLoggedIn = checkLoginState()
        navController.navigate(
            if (isLoggedIn) Home.route else Login.route
        ) {
            popUpTo(Splash.route) { inclusive = true }
        }
    }

    Box(Modifier.fillMaxSize().background(color = InvertLogo)) {
        Image(
            painter = painterResource(id = R.drawable.logo_2_invertido_sin_fondo),
            contentDescription = "Logo Invertiodo",
            modifier = Modifier.fillMaxWidth().align(Alignment.Center).size(297.dp)
        )
    }
}

private fun checkLoginState(): Boolean {
    return false
}

