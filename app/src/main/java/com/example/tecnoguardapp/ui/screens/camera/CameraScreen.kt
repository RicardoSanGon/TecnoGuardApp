package com.example.tecnoguardapp.ui.screens.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.ui.screens.MjpegStreamView
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview()
@Composable
fun CameraScreen() {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .height(440.dp)
            .background(color = Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cámara en tiempo real", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.Black)
        ) {
            val streamUrl = "https://f9ee1c89640b.ngrok-free.app/?action=stream"
            MjpegStreamView(
                streamUrl = streamUrl,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        FechaHoraActual()
    }
}

@Composable
fun FechaHoraActual() {
    var fechaHora by remember { mutableStateOf("") }
    val formato = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }

    LaunchedEffect(Unit) {
        while (true) {
            fechaHora = formato.format(Date())
            delay(1000)
        }
    }

    Text(
        text = buildAnnotatedString {
            append("Fecha y Hora: ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(fechaHora)
        },
        fontSize = 18.sp
    )
}