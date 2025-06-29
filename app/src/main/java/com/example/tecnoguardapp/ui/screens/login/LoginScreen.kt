package com.example.tecnoguardapp.ui.screens.login

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.theme.BackgroundColor
import com.example.tecnoguardapp.ui.theme.BlueMain
import com.example.tecnoguardapp.ui.theme.BlueSecond


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(30.dp))
            Text(
                "Inicio de Sesión",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Spacer(Modifier.height(25.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_2_sin_fondo),
                contentDescription = "Logo TecnoGuard",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(220.dp)
            )
            Spacer(Modifier.height(106.dp))
            ButtonMain("Iniciar Sesión") {
               viewModel.login(context)
            }
            Spacer(Modifier.height(43.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(Modifier
                    .size(105.dp, 2.dp)
                    .background(color = Color.Black))
                Text("Ó", fontSize = 32.sp)
                Spacer(Modifier
                    .size(105.dp, 2.dp)
                    .background(color = Color.Black))
            }
            Spacer(Modifier.height(43.dp))
            ButtonMain("Registrarse", containerColor = BlueSecond, borderColor = BlueMain) {  }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginPreview(){
    LoginScreen()
}