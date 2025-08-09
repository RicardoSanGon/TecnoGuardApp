package com.example.tecnoguardapp.ui.screens.tokens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.data.responses.Tokens.get.TokensData
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.components.inputs.MainInput
import com.example.tecnoguardapp.ui.components.inputs.SelectInput
import com.example.tecnoguardapp.ui.theme.CyanGreen
import com.example.tecnoguardapp.utils.formateDate
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TokensScreen(
    tokensViewModel: TokensViewModel = hiltViewModel()
) {
    val optionSelected by tokensViewModel.selectedOption.observeAsState("")
    val tokenName by tokensViewModel.tokenName.observeAsState("")
    val isButtonEnabled by tokensViewModel.isButtonEnabled.observeAsState(false)

    val coroutine = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .height(540.dp)
            .background(color = Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Accesos", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text(text = "Generar nuevo", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Text(text = "Nombre del Acceso", fontSize = 20.sp)
        MainInput(
            value = tokenName,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.key_icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            },
            modifier = Modifier.height(50.dp)
        ) {
            tokensViewModel.onChangeTokenName(it)
        }
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
            SelectInput(selectedText = optionSelected) { tokensViewModel.onOptionChange(it) }
        }
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonMain(
                action = {
                    coroutine.launch {
                        tokensViewModel.createToken()
                    }
                },
                roundedSize = 20.dp,
                modifier = Modifier.size(width = 240.dp, height = 45.dp),
                isEnabled = isButtonEnabled
            ) {
                Text(text = "Crear Acceso", color = Color.Black, fontSize = 20.sp)
            }
            ButtonMain(
                action = {
                    coroutine.launch {
                        tokensViewModel.obtenerTokens()
                    }
                },
                roundedSize = 20.dp,
                containerColor = CyanGreen,
                modifier = Modifier.size(width = 240.dp, height = 45.dp),
            ) {
                Text(text = "Ver Accesos creados", color = Color.Black, fontSize = 15.sp)
            }
        }
    }

}


@Composable
fun ModalToken(
    acceso: String = "2323232",
    fechaExpiracion: String = "2025-08-21 13:30:12",
    closeModal: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
            .pointerInput(Unit) {} ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.White)
                .padding(20.dp)
                .height(320.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Acceso Creado", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Text("Acceso generado:", fontSize = 20.sp)
            Text(acceso, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Fecha de expiración:", fontSize = 20.sp)
            Text(fechaExpiracion, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                "Recuerde compartir el token con la persona que quiere que acceda, igualmente puede verificar que sea la persona correcta mediante la cámara.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.width(270.dp)
            )
            ButtonMain(
                action = { closeModal() },
                roundedSize = 20.dp,
                modifier = Modifier.size(width = 240.dp, height = 45.dp)
            ) {
                Text(text = "Aceptar", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TokensTable(listTokens: List<TokensData>?, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Tabla de datos", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))

                // Cabecera
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Nombre", "Codigo", "Fecha Exp", "Usado").forEach {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                listTokens?.forEach { token ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            token.nombre,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                        Text(
                            token.valor,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                        Text(
                            formateDate(token.fecha_expiracion),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                        Text(
                            if (token.usos == 1) "No" else "Si",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
