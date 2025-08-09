package com.example.tecnoguardapp.ui.screens.configuration.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MyAccountScreen(
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    val userData by accountViewModel.userData.observeAsState()
    LaunchedEffect(Unit) {
        accountViewModel.getUserData()
    }
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Mi Cuenta", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        Text(text = buildAnnotatedString {
            append("Nombre: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(userData?.name ?: "No disponible")
        }, fontSize = 16.sp)
        Text(text = buildAnnotatedString {
            append("Rol: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(userData?.role?.name ?: "No disponible")
        }, fontSize = 16.sp)
        Text(text = buildAnnotatedString {
            append("Dirección: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(userData?.direccion ?: "No disponible")
        }, fontSize = 16.sp)
        Text(text = buildAnnotatedString {
            append("Correo: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(userData?.email ?: "No disponible")
        }, fontSize = 16.sp)
        Text(text = buildAnnotatedString {
            append("Contacto: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(userData?.phone ?: "No disponible")
        }, fontSize = 16.sp)
        Text(text = buildAnnotatedString {
            append("Cerrada asignada: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(userData?.family_group?.cerrada?.group_name ?: "No disponible")
        }, fontSize = 16.sp)
        Text(text = buildAnnotatedString {
            append("Miembros maximos: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("3")
        }, fontSize = 16.sp)
        Text(text = buildAnnotatedString {
            append("Fecha de expiración de membresia: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("2025-09-12")
        }, fontSize = 16.sp)
        Text(text = buildAnnotatedString {
            append("Monto de membresia: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("MX$1500")
        }, fontSize = 16.sp)

    }
}