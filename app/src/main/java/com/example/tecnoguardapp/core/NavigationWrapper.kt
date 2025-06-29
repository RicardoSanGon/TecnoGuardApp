package com.example.tecnoguardapp.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tecnoguardapp.core.Screen.Home
import com.example.tecnoguardapp.core.Screen.Login
import com.example.tecnoguardapp.core.Screen.Splash
import com.example.tecnoguardapp.ui.SplashScreen
import com.example.tecnoguardapp.ui.screens.home.HomeScreen
import com.example.tecnoguardapp.ui.screens.login.LoginScreen

@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Splash.route){
        composable(Splash.route) {
            SplashScreen(navController)
        }
        composable(Login.route){
            LoginScreen()
        }
        composable(Home.route){
            HomeScreen()
        }

    }
}