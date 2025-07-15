package com.example.tecnoguardapp.ui.screens.dashboard

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.screens.home.HomeScreen
import com.example.tecnoguardapp.ui.theme.BackgroundColor
import com.example.tecnoguardapp.ui.theme.ColorSecond

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreen() {

    Column(
        Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(horizontal = 20.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeScreen()
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
        Options()
    }
}

@Composable
fun Options() {
    val options = listOf(
        R.drawable.key_icon to "Accesos",
        R.drawable.home_icon to "Home",
        R.drawable.settings_icon to "Config."
    )
    val selectedIndex = remember { mutableStateOf(1) }

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
                val isSelected = selectedIndex.value == index
                val alphaAnim by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.4f,
                    label = "alphaAnimation"
                )

                Box(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { selectedIndex.value = index },
                    contentAlignment = Alignment.Center
                ) {
                    DashboardOption {
                        DashboardOptionIcon(icon, alpha = alphaAnim)
                        Text(
                            label,
                            color = Color.Black,
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
