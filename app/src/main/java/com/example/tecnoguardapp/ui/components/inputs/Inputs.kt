package com.example.tecnoguardapp.ui.components.inputs

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.R


@Composable
fun MainInput(
    value: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = {onValueChange(it)},
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        leadingIcon = icon
    )
}

@Composable
fun SelectInput(
    selectedText: String = "",
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(200.dp)
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                .background(color = Color.White)
                .height(40.dp)
                .fillMaxWidth()
                .padding(5.dp)
                .clickable(onClick = {expanded = true}),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(selectedText, fontSize = 20.sp)
            Icon(
                painter = painterResource(id = R.drawable.arrow_icon_black),
                contentDescription = null,
                modifier = Modifier.rotate(90f)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Peatonal") },
                onClick = {
                    onItemSelected("Peatonal")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Automovil") },
                onClick = {
                    onItemSelected("Automovil")
                    expanded = false
                }
            )

        }
    }
}
