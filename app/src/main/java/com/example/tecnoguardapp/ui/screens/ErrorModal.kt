package com.example.tecnoguardapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.theme.ColorSecond
import com.example.tecnoguardapp.ui.theme.Danger


@Composable
fun ErrorModal(
    errorMessage: String,
    closeModal: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
            .pointerInput(Unit) { closeModal() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .clip(shape = RoundedCornerShape(30.dp))
                .background(color = Color.White)
                .padding(18.dp)
                .width(230.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.exclamation_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(30.dp))
                        .background(color = Danger)
                        .size(35.dp)
                )
                Text(
                    "Error",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                )
            }
            Text(
                errorMessage,
                fontSize = 18.sp
            )
            ButtonMain(action = {}, roundedSize = 30.dp, containerColor = ColorSecond) {
                Text("Aceptar", fontSize = 18.sp)
            }
        }
    }
}