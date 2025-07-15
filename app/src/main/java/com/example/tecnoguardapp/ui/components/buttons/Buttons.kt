package com.example.tecnoguardapp.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.ui.theme.ColorMain

@Composable
fun ButtonMain(
    containerColor: Color = ColorMain,
    contentColor: Color = Color.White,
    modifier: Modifier = Modifier.size(width = 240.dp, height = 51.dp),
    roundedSize: Dp = 5.dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    action: () -> Unit,
    content: @Composable (RowScope.() -> Unit)
) {
    Button(
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier,
        shape = RoundedCornerShape(roundedSize),
        contentPadding = contentPadding
    ) {
        content()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ButtonMain(action = {}) {
            Text("Iniciar sesion")
        }
    }
}

