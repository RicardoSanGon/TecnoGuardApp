package com.example.tecnoguardapp.ui.screens.dashboard

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.screens.LoadScreen
import com.example.tecnoguardapp.ui.screens.camera.CameraScreen
import com.example.tecnoguardapp.ui.screens.home.HomeScreen
import com.example.tecnoguardapp.ui.screens.tokens.ModalToken
import com.example.tecnoguardapp.ui.screens.tokens.TokensScreen
import com.example.tecnoguardapp.ui.screens.tokens.TokensViewModel
import com.example.tecnoguardapp.ui.theme.BackgroundColor
import com.example.tecnoguardapp.ui.theme.ColorSecond
import com.example.tecnoguardapp.ui.theme.Yellow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
    tokensViewModel: TokensViewModel = hiltViewModel()
) {
    val showModal by tokensViewModel.showModal.observeAsState(false)
    val isLoading by tokensViewModel.isLoading.observeAsState(false)

    val tokenCode by tokensViewModel.tokenCode.observeAsState("")
    val expirationDate by tokensViewModel.expirationDate.observeAsState("")

    val selectedIndex = remember { mutableIntStateOf(1) }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(horizontal = 20.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.height(550.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (selectedIndex.intValue) {
                0 -> TokensScreen()
                1 -> HomeScreen()
                2 -> {
                    CameraScreen()
                    Spacer(Modifier.height(20.dp))
                    ButtonMain(
                        action = {},
                        containerColor = Yellow,
                        roundedSize = 20.dp,
                        modifier = Modifier.size(width = 250.dp, height = 51.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera_icon),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Tomar Foto", fontSize = 23.sp, color = Color.Black)
                    }
                }
            }

        }
        Spacer(Modifier.height(20.dp))
        ButtonMain(
            action = {},
            containerColor = ColorSecond,
            roundedSize = 20.dp,
            modifier = Modifier.size(width = 306.dp, height = 51.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("Mirar Camara", fontSize = 23.sp)
        }
        Spacer(Modifier.height(40.dp))
        Options(
            selectedIndex = selectedIndex.intValue,
            onOptionSelected = { index -> selectedIndex.intValue = index }
        )
    }

    if (showModal) {
        ModalToken(
            fechaExpiracion = expirationDate,
            acceso = tokenCode
        ) { tokensViewModel.closeModal() }
    }
    if (isLoading) {
        LoadScreen()
    }
}

@Composable
fun Options(
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    val options = listOf(
        R.drawable.key_icon to "Accesos",
        R.drawable.home_icon to "Home",
        R.drawable.settings_icon to "Config."
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .background(Color.White)
            .height(75.dp)
            .padding(horizontal = 22.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, (icon, label) ->
                val isSelected = selectedIndex == index
                val alphaAnim by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.4f,
                    label = "alphaAnimation"
                )
                val textColor = if (isSelected) Color.Black else Color.Gray

                Box(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onOptionSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    DashboardOption {
                        DashboardOptionIcon(icon, alpha = alphaAnim)
                        Text(
                            label,
                            color = textColor,
                            modifier = Modifier.alpha(alphaAnim)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardOption(
    content: @Composable (ColumnScope.() -> Unit)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        content()
    }
}

@Composable
fun DashboardOptionIcon(@DrawableRes iconRes: Int, alpha: Float) {
    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = Modifier
            .size(30.dp)
            .alpha(alpha),
        tint = Color.Black
    )
}
