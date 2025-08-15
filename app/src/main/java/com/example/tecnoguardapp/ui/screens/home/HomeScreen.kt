package com.example.tecnoguardapp.ui.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.screens.UbicationViewModel
import com.example.tecnoguardapp.ui.theme.CyanGreen
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    ubicationViewModel: UbicationViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { homeViewModel.getCerradaName() }

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val nombreCerrada by homeViewModel.cerradaName.observeAsState()
    val areButtonsEnabled by homeViewModel.areButtonsEnabled.observeAsState(true)

    // Estado para mensajes al usuario
    var status by remember { mutableStateOf<String?>(null) }

    // Si el usuario solicitó permiso al pulsar un botón, guardamos qué puerta quería abrir
    var pendingDoorType by remember { mutableStateOf<String?>(null) }

    // Launcher para pedir múltiples permisos
    val permisos = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.values.all { it }
        if (allGranted) {
            // Si fueron concedidos y había una acción pendiente, la ejecutamos
            val tipo = pendingDoorType
            pendingDoorType = null
            if (tipo != null) {
                coroutine.launch {
                    // Reutilizamos la misma lógica que usamos cuando ya hay permisos
                    status = "Permisos concedidos. Verificando ubicación..."
                    val loc = ubicationViewModel.obtenerUbicacionActual(context)
                    if (loc == null) {
                        status = "No se pudo obtener la ubicación. Verifica GPS o señal."
                        return@launch
                    }
                    val cerca = ubicationViewModel.estaCerca(
                        latActual = loc.latitude,
                        lonActual = loc.longitude,
                        distanciaMaxMetros = 5f
                    )
                    if (cerca) {
                        homeViewModel.disableButtonsForSeconds(10)
                        homeViewModel.openDoor(tipo)
                        status = "Abriendo puerta ($tipo)..."
                    } else {
                        status = "Debes estar a ≤ 5 metros de la entrada."
                    }
                }
            } else {
                // No había acción pendiente
                status = null
            }
        } else {
            // Usuario negó al menos un permiso
            pendingDoorType = null
            Toast.makeText(context, "Permisos de ubicación requeridos para abrir puertas", Toast.LENGTH_LONG).show()
            status = "Permisos de ubicación requeridos."
        }
    }

    // Helper: cuando se presiona un botón
    fun onDoorButtonClicked(tipo: String) {
        // 1) Checar permisos ya concedidos
        val permisosConcedidos = permisos.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (permisosConcedidos) {
            // Ejecutar validación y apertura en coroutine
            coroutine.launch {
                status = "Verificando ubicación..."
                val loc = ubicationViewModel.obtenerUbicacionActual(context)
                if (loc == null) {
                    status = "No se pudo obtener la ubicación. Verifica GPS y permisos."
                    return@launch
                }
                val cerca = ubicationViewModel.estaCerca(
                    latActual = loc.latitude,
                    lonActual = loc.longitude,
                    distanciaMaxMetros = 5f
                )
                if (cerca) {
                    homeViewModel.disableButtonsForSeconds(10)
                    homeViewModel.openDoor(tipo)
                    status = "Abriendo puerta ($tipo)..."
                } else {
                    status = "Debes estar a ≤ 5 metros de la entrada."
                }
            }
        } else {
            // Guardamos la intención y lanzamos el request
            pendingDoorType = tipo
            permissionLauncher.launch(permisos)
        }
    }

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
        Text(nombreCerrada ?: "Sin asignar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
            DoorsButtons(
                isCerradaAsigned = nombreCerrada != null && areButtonsEnabled,
                openCarDoorAction = { tipo ->
                    onDoorButtonClicked(tipo)
                }
            )
        }

        // Mensaje de estado
        status?.let {
            Spacer(Modifier.height(12.dp))
            Text(
                text = it,
                color = Color(0xFFB00020),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
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
                "Nota: Necesita estar al menos 5 metros de distancia para abrir las puertas",
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
        }
    }
}

/* DoorsButtons idéntico al que ya tenías. */
@Composable
private fun DoorsButtons(
    openCarDoorAction: (String) -> Unit,
    isCerradaAsigned: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Puerta Peatonal", fontSize = 16.sp)
        Spacer(Modifier.weight(1f))
        ButtonMain(
            action = { openCarDoorAction("P") },
            containerColor = CyanGreen,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp),
            isEnabled = isCerradaAsigned
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            action = { openCarDoorAction("A") },
            containerColor = CyanGreen,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp),
            isEnabled = isCerradaAsigned
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            action = { openCarDoorAction("S") },
            containerColor = CyanGreen,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp),
            isEnabled = isCerradaAsigned
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
