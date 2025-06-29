package com.example.tecnoguardapp.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.ui.theme.BlueMain
import com.example.tecnoguardapp.ui.theme.BlueThird

@Composable
fun ButtonMain(
    text: String,
    containerColor: Color = BlueMain,
    contentColor: Color = Color.White,
    borderColor: Color = BlueThird,
    action: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(250.dp)
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 0.dp, y = 4.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(5.dp)
                )
        )
        Button(
            onClick = action,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, borderColor, RoundedCornerShape(5.dp)),
            shape = RoundedCornerShape(5.dp),
            elevation = null // Desactiva la elevación interna
        ) {
            Text(text, fontSize = 20.sp)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ButtonPreview() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonMain("Text button") { }
    }
}

