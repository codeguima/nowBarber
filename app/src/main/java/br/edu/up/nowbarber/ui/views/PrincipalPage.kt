package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.navigation.BarberNavHost
import br.edu.up.nowbarber.ui.navigation.TelaRotasBottom
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import kotlinx.coroutines.launch

@Composable
fun PrincipalPage(
    usuarioId: Int,
    clienteViewModel: ClienteViewModel,
    servicoViewModel: ServicoViewModel,
    onLogout: () -> Unit

) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentBack by navController.currentBackStackEntryAsState()
    val rotaAtual = currentBack?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .background(colorResource(id = R.color.principal))
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                DrawerButton(
                    label = "Inicio",
                    icon = Icons.Filled.Home,
                    isSelected = rotaAtual == TelaRotasBottom.TelaInicio,
                    onClick = {
                        navController.navigate(TelaRotasBottom.TelaInicio)
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                DrawerButton(
                    label = "Segurança",
                    icon = Icons.Filled.Lock,
                    isSelected = rotaAtual == TelaRotasBottom.TelaSeguranca,
                    onClick = {
                        navController.navigate(TelaRotasBottom.TelaSeguranca)
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                DrawerButton(
                    label = "Meus Acessos",
                    icon = Icons.Filled.ManageAccounts,
                    isSelected = rotaAtual == TelaRotasBottom.TelaMeusAcessos,
                    onClick = {
                        navController.navigate(TelaRotasBottom.TelaMeusAcessos)
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                DrawerButton(
                    label = "Cartões",
                    icon = Icons.Filled.Payment,
                    isSelected = rotaAtual == TelaRotasBottom.TelaPayments,
                    onClick = {
                        navController.navigate(TelaRotasBottom.TelaPayments)
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                DrawerButton(
                    label = "Conta",
                    icon = Icons.Filled.AccountCircle,
                    isSelected = rotaAtual == TelaRotasBottom.TelaAccountUser,
                    onClick = {
                        navController.navigate(TelaRotasBottom.TelaAccountUser)
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                DrawerButton(
                    label = "Favoritos",
                    icon = Icons.Filled.FavoriteBorder,
                    isSelected = rotaAtual == TelaRotasBottom.TelaFavoritos,
                    onClick = {
                        navController.navigate(TelaRotasBottom.TelaFavoritos)
                        coroutineScope.launch { drawerState.close() }
                    }
                )

                Spacer(modifier = Modifier.height(300.dp))

                TextButton(
                    onClick = {
                        onLogout()
                        coroutineScope.launch { drawerState.close() }
                    }
                ) {
                    Text(
                        color = Color.Red,
                        text = "Sair",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(30.dp, 5.dp)
                    )
                }
            }
        },
        content = {
            // Passando o usuarioId para o BarberNavHost
            BarberNavHost(
                navController = navController,
                state = drawerState,
                usuarioId = usuarioId,
                clienteViewModel = clienteViewModel,
                servicoViewModel = servicoViewModel
            )
        }
    )
}



@Composable
fun DrawerButton(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        colors = ButtonDefaults.buttonColors(containerColor = getBack(isSelected)),
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(30.dp),
            tint = getTint(isSelected)
        )
        Text(
            color = getTint(isSelected),
            text = label,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun getTint(selected: Boolean): Color {
    return if (selected) Color.Black else Color.DarkGray
}

fun getBack(selected: Boolean): Color {
    return if (selected) Color.White else Color.Transparent
}
