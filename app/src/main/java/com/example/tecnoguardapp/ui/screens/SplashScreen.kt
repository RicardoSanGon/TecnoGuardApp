package com.example.tecnoguardapp.ui.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.core.Screen.Dashboard
import com.example.tecnoguardapp.core.Screen.Login
import com.example.tecnoguardapp.core.Screen.Splash
import com.example.tecnoguardapp.ui.theme.BackgroundColor
import com.example.tecnoguardapp.utils.AuthViewModel

@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        val isLoggedIn = authViewModel.getData()
        navController.navigate(
            if (true) Dashboard.route else Login.route
        ) {
            popUpTo(Splash.route) { inclusive = true }
        }
    }

    Box(Modifier.fillMaxSize().background(color = BackgroundColor)) {
        Image(
            painter = painterResource(id = R.drawable.logo_2_sin_fondo),
            contentDescription = "Logo sin fondo",
            modifier = Modifier.fillMaxWidth().align(Alignment.Center).size(297.dp)
        )
    }
}

