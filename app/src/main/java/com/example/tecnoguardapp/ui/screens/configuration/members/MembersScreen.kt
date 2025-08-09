package com.example.tecnoguardapp.ui.screens.configuration.members

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.data.responses.Family_Members.get.MemberData
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.components.inputs.MainInput
import com.example.tecnoguardapp.ui.theme.ColorSecond
import com.example.tecnoguardapp.ui.theme.Danger

@Composable
fun AddMemberModal(
    onCancelAction: () -> Unit,
    onDeleteAction: (MemberData) -> Unit,
    memberEmail: String,
    onChangeEmail: (String) -> Unit,
    onAcceptAction: () -> Unit,
    listMembers: List<MemberData>?,
    isEnabledAcceptButton: Boolean
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
            .padding(20.dp)
            .pointerInput(Unit) { },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.White)
                .padding(horizontal = 20.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Agregar miembro", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text("Email", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            MainInput(
                value = memberEmail,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.mail_icon),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                },
                placeholder = { Text("example@tecnoguard.site") },
            ) { onChangeEmail(it) }
            Text("Verifique que el correo agregado sea el correcto.", fontSize = 18.sp)
            Spacer(Modifier.height(20.dp))
            Text(
                text = buildAnnotatedString {
                    append("Cupos disponibles: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("1")
                },
                fontSize = 20.sp
            )
            ButtonMain(
                action = { onAcceptAction() },
                roundedSize = 30.dp,
                isEnabled = isEnabledAcceptButton
            ) {
                Text("Aceptar", fontSize = 23.sp)
            }
            ButtonMain(
                action = { onCancelAction() },
                roundedSize = 30.dp,
                containerColor = ColorSecond
            ) {
                Text("Cancelar", fontSize = 23.sp)
            }
            if (listMembers?.isNotEmpty() == true) {
                Spacer(Modifier.height(20.dp))
                listMembers.forEach { member ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.user_icon),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(member.name, fontSize = 18.sp)
                        Spacer(Modifier.weight(1f))
                        Icon(
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable(onClick = { onDeleteAction(member) }),
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DeleteMemberModal(
    memberSelected: MemberData?,
    onCancelAction: () -> Unit,
    onAcceptAction: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
            .padding(20.dp)
            .pointerInput(Unit) { },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.White)
                .padding(horizontal = 20.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.exclamation_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .background(color = Danger),
                    tint = Color.White
                )
                Text("Eliminar miembro", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
            Text(text = buildAnnotatedString {
                append("Esta seguro que quieres eliminar al miembro ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append(memberSelected?.name)
                pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
                append("?")

            }, fontSize = 18.sp)
            Spacer(Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonMain(
                    action = { onCancelAction() },
                    modifier = Modifier.size(width = 120.dp, height = 40.dp),
                    roundedSize = 30.dp,
                    containerColor = ColorSecond
                ) {
                    Text("Cancelar")
                }
                ButtonMain(
                    action = { onAcceptAction() },
                    modifier = Modifier.size(width = 120.dp, height = 40.dp),
                    roundedSize = 30.dp,
                    containerColor = Danger
                ) {
                    Text("Aceptar")
                }
            }
        }
    }
}
