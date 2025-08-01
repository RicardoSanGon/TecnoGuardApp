package com.example.tecnoguardapp.ui.screens.tokens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.components.inputs.MainInput
import com.example.tecnoguardapp.ui.components.inputs.SelectInput
import java.nio.file.WatchEvent

@Preview()
@Composable
fun TokensScreen() {

    var tipoSeleccionado by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .height(620.dp)
            .background(color = Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Accesos", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text(text = "Generar nuevo", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Text(text = "Nombre del Acceso", fontSize = 20.sp)
        MainInput(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.key_icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            },
            modifier = Modifier.height(45.dp)
        )
        Text(
            text = "El nombre debe de contener almenos 4 caracteres, inlcuyendo letras, guiones y numeros.",
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.7f),
            lineHeight = 25.sp,
            modifier = Modifier.width(250.dp)
        )
        Text(
            text = buildAnnotatedString {
                append("Tiempo de expiración: ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("5 horas después de crearse")
                pop()
            },
            fontSize = 20.sp
        )
        Text(
            text = buildAnnotatedString {
                append("Tipo de acceso: ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("Visita")
                pop()
            },
            fontSize = 20.sp
        )
        Text(
            text = buildAnnotatedString {
                append("Usos maximos: ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("1")
                pop()
            },
            fontSize = 20.sp
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Entrada: ", fontSize = 20.sp)
            SelectInput { newValue -> tipoSeleccionado = newValue }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonMain(action = {}, roundedSize = 20.dp) {
                Text(text = "Crear Acceso", color = Color.Black, fontSize = 20.sp)
            }
            ButtonMain(action = {}, roundedSize = 20.dp) {
                Text(text = "Ver Accesos creados", color = Color.Black, fontSize = 20.sp)
            }
        }
    }
}