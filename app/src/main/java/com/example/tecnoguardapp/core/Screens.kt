package com.example.tecnoguardapp.core

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Dashboard : Screen("dashboard")
}