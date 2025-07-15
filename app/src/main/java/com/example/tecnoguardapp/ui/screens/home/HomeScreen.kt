package com.example.tecnoguardapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.theme.BackgroundColor
import com.example.tecnoguardapp.ui.theme.ColorSecond
import com.example.tecnoguardapp.ui.theme.CyanGreen

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .height(550.dp)
            .background(color = Color.White)
            .padding(20.dp)
    ) {
        Row {
            Text("Bienvenido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.account_circle_icon),
                contentDescription = null,
                modifier = Modifier.size(41.dp)
            )
        }
        Text("Accesos", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Nom de Cerrada", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .background(color = Color.Transparent)
                .border(2.dp, Color.Gray.copy(alpha = 0.4f), RoundedCornerShape(30.dp))
                .padding(horizontal = 10.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.spacedBy(27.dp)
        ) {
            DoorsButtons()
        }
        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .clip(shape = RoundedCornerShape(30.dp))
                .border(2.dp, CyanGreen, RoundedCornerShape(30.dp))
                .background(color = CyanGreen.copy(0.4f))
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Text(
                "Nota: Necesita estar almenos 5 metros de distancia para abrir las puertas",
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun DoorsButtons() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Puerta Peatonal", fontSize = 16.sp)
        Spacer(Modifier.weight(1f))
        ButtonMain(
            action = {},
            containerColor = CyanGreen,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.door_front_icon),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = Color.Black
                )
                Text(
                    text = "Abrir",
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Entrada Automovil", fontSize = 16.sp)
        Spacer(Modifier.weight(1f))
        ButtonMain(
            action = {},
            containerColor = CyanGreen,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.door_front_icon),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = Color.Black
                )
                Text(
                    text = "Abrir",
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Salida Automovil", fontSize = 16.sp)
        Spacer(Modifier.weight(1f))
        ButtonMain(
            action = {},
            containerColor = CyanGreen,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.door_front_icon),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = Color.Black
                )
                Text(
                    text = "Abrir",
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}