package com.example.tecnoguardapp.core

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tecnoguardapp.core.Screen.Dashboard
import com.example.tecnoguardapp.core.Screen.Login
import com.example.tecnoguardapp.core.Screen.Splash
import com.example.tecnoguardapp.ui.screens.SplashScreen
import com.example.tecnoguardapp.ui.screens.dashboard.DashboardScreen
import com.example.tecnoguardapp.ui.screens.login.LoginScreen
import com.example.tecnoguardapp.utils.AuthViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationWrapper(viewModel: AuthViewModel = hiltViewModel()) {

    val navController = rememberNavController()
    val navigateToDashboard by viewModel.navigateToDashboard.collectAsState()
    val navigateToLogin by viewModel.navigateToLogin.collectAsState()


    LaunchedEffect(navigateToLogin) {
        if (navigateToLogin) {
            Log.e("NAVIGATION", "Navegando a Login")
            navController.navigate(Login.route) {
                popUpTo(0) { inclusive = true }
            }
            viewModel.resetNavigateToLogin()
        }
    }

    LaunchedEffect(navigateToDashboard) {
        if (navigateToDashboard) {
            navController.navigate(Dashboard.route) {
                popUpTo(0) { inclusive = true }
            }
            viewModel.resetNavigateToDashboard()
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
            DashboardScreen(authViewModel = viewModel)
        }

    }
}