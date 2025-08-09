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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tecnoguardapp.R
import com.example.tecnoguardapp.ui.components.buttons.ButtonMain
import com.example.tecnoguardapp.ui.screens.LoadScreen
import com.example.tecnoguardapp.ui.screens.camera.CameraScreen
import com.example.tecnoguardapp.ui.screens.configuration.ConfigurationScreen
import com.example.tecnoguardapp.ui.screens.configuration.ConfigurationViewModel
import com.example.tecnoguardapp.ui.screens.configuration.account.MyAccountScreen
import com.example.tecnoguardapp.ui.screens.configuration.members.AddMemberModal
import com.example.tecnoguardapp.ui.screens.configuration.members.DeleteMemberModal
import com.example.tecnoguardapp.ui.screens.home.HomeScreen
import com.example.tecnoguardapp.ui.screens.tokens.ModalToken
import com.example.tecnoguardapp.ui.screens.tokens.TokensScreen
import com.example.tecnoguardapp.ui.screens.tokens.TokensTable
import com.example.tecnoguardapp.ui.screens.tokens.TokensViewModel
import com.example.tecnoguardapp.ui.theme.BackgroundColor
import com.example.tecnoguardapp.ui.theme.ColorSecond
import com.example.tecnoguardapp.ui.theme.Yellow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
    tokensViewModel: TokensViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    configurationViewModel: ConfigurationViewModel = hiltViewModel(),
) {

    val isLoadingTokens by tokensViewModel.loadingManager.isLoading.collectAsState()
    val isLoadingConfig by configurationViewModel.loadingManager.isLoading.collectAsState()

    val coroutine = rememberCoroutineScope()

    val showModal by tokensViewModel.showModal.observeAsState(false)

    val tokenCode by tokensViewModel.tokenCode.observeAsState("")
    val expirationDate by tokensViewModel.expirationDate.observeAsState("")
    val showTableTokens by tokensViewModel.showTable.observeAsState(false)
    val lisTokens by tokensViewModel.listTokens.observeAsState()

    val selectedScreen by dashboardViewModel.selectedScreen.observeAsState(1)

    val showAddMemberModal by configurationViewModel.showAddMemberModal.observeAsState(false)
    val showDeleteMemberModal by configurationViewModel.showDeleteMemberModal.observeAsState(false)
    val memberEmail by configurationViewModel.newMemberEmail.observeAsState("")
    val listMembers by configurationViewModel.listMembers.observeAsState()
    val memberSelected by configurationViewModel.selectedMember.observeAsState()
    val isEnableAddMemberButton by configurationViewModel.enableAddMemberButton.observeAsState(false)

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
            when (selectedScreen) {
                0 -> TokensScreen()
                1 -> HomeScreen()
                2 -> ConfigurationScreen(
                    showAccountScreen = { dashboardViewModel.onScreenChange(4) },
                    showAddMemberModal = { configurationViewModel.showAddMember() })

                3 -> {
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

                4 -> {
                    MyAccountScreen()
                }
            }

        }
        Spacer(Modifier.height(20.dp))
        ButtonMain(
            action = { dashboardViewModel.onScreenChange(3) },
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
            selectedIndex = selectedScreen,
            onOptionSelected = { dashboardViewModel.onScreenChange(it) }
        )
    }

    if (showModal) {
        ModalToken(
            fechaExpiracion = expirationDate,
            acceso = tokenCode
        ) { tokensViewModel.closeModal() }
    }


    if (showTableTokens) {
        TokensTable(lisTokens) {
            tokensViewModel.closeTable()
        }
    }
    if (showAddMemberModal) {
        AddMemberModal(
            onCancelAction = { configurationViewModel.closeAddMember() },
            onDeleteAction = { configurationViewModel.showDeleteMember(it) },
            memberEmail = memberEmail,
            onAcceptAction = {
                coroutine.launch {
                    configurationViewModel.addMember()
                }
            },
            onChangeEmail = { configurationViewModel.onChangeEmail(it) },
            listMembers = listMembers,
            isEnabledAcceptButton = isEnableAddMemberButton
        )
    }
    if (showDeleteMemberModal) {
        DeleteMemberModal(
            onCancelAction = { configurationViewModel.closeDeleteMember() },
            memberSelected = memberSelected,
            onAcceptAction = { coroutine.launch { configurationViewModel.deleteMember() } }
        )
    }
    if (isLoadingTokens || isLoadingConfig) {
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
