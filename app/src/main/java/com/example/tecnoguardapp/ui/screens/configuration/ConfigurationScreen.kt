package com.example.tecnoguardapp.ui.screens.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.R


@Composable
fun ConfigurationScreen(
    showAccountScreen: (Int) -> Unit,
    showAddMemberModal: () -> Unit,
    onLogOut: () -> Unit,
    isJefeFamilia: Boolean,
    haveFamily: Boolean,
    giveHelp: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        Text("Configuración", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    showAccountScreen(4)
                })
        ) {
            Icon(
                painter = painterResource(id = R.drawable.user_icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text("Mi cuenta", fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.arrow_icon_black),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
        if (haveFamily && isJefeFamilia) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        showAddMemberModal()
                    })
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.person_add_icon),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Text("Agregar miembro", fontSize = 18.sp)
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.arrow_icon_black),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = Color.Black.copy(0.7f))
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clickable(onClick = { giveHelp() })) {
            Icon(
                painter = painterResource(id = R.drawable.mail_icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text("Solicitar ayuda", fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.arrow_icon_black),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = Color.Black.copy(0.7f))
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onLogOut() })
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout_icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text("Cerrar sesión", fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.arrow_icon_black),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}