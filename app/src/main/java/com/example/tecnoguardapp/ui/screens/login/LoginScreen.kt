package com.example.tecnoguardapp.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.theme.ColorSecond

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.background_1_mobile),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp, top = 63.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(30.dp))
                .size(width = 292.dp, height = 67.dp)
                .background(color = Color.White.copy(alpha = 0.1f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                "Tecno Guard",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.width(5.dp))
            Icon(
                painterResource(id = R.drawable.lock_icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )
        }
        Spacer(Modifier.height(120.dp))
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(30.dp))
                .size(width = 292.dp, height = 353.dp)
                .background(color = Color.White.copy(alpha = 0.1f))
                .padding(vertical = 30.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Iniciar sesión",
                color = Color.White,
                fontSize = 23.sp,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(24.dp))
            ButtonMain(action = {viewModel.login(context)}) {
                Text("Acceder", fontSize = 23.sp)
            }
            Spacer(Modifier.height(14.dp))
            Row(
                Modifier.height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Spacer(
                    Modifier
                        .size(width = 100.dp, height = 3.dp)
                        .background(color = Color.White)
                )
                Text("Ó", fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(
                    Modifier
                        .size(width = 100.dp, height = 3.dp)
                        .background(color = Color.White)
                )
            }
            Spacer(Modifier.height(9.dp))
            Text(
                "Crear cuenta",
                color = Color.White,
                fontSize = 23.sp,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(24.dp))
            ButtonMain(action = {viewModel.register(context)}, containerColor = ColorSecond) {
                Text("Registarse", fontSize = 23.sp)
            }

        }
        Spacer(Modifier.height(74.dp))
        Image(painterResource(id = R.drawable.logo_2_sin_fondo), contentDescription = null)
    }
}

