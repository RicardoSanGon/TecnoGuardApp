package com.example.tecnoguardapp.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tecnoguardapp.core.Screen.Login
import com.example.tecnoguardapp.core.Screen.Splash
import com.example.tecnoguardapp.ui.screens.SplashScreen
import com.example.tecnoguardapp.ui.screens.login.LoginScreen
import com.example.tecnoguardapp.utils.AuthViewModel
import androidx.compose.runtime.getValue
import com.example.tecnoguardapp.core.Screen.Dashboard
import com.example.tecnoguardapp.ui.screens.dashboard.DashboardScreen


@Composable
fun NavigationWrapper(viewModel: AuthViewModel = hiltViewModel()) {

    val navController = rememberNavController()
    val navigateToDashboard by viewModel.navigateToDashboard.collectAsState()

    LaunchedEffect(navigateToDashboard) {
        if (navigateToDashboard) {
            navController.navigate(Dashboard.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    NavHost(navController = navController, startDestination = Splash.route) {
        composable(Splash.route) {
            SplashScreen(navController)
        }
        composable(Login.route) {
            LoginScreen()
        }
        composable(Dashboard.route) {
            DashboardScreen()
        }

    }
}