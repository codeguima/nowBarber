package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import br.edu.up.nowbarber.ui.viewmodels.AgendamentoViewModel
import br.edu.up.nowbarber.ui.viewmodels.BarbeariaViewModel
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel
import kotlinx.coroutines.launch

@Composable
fun PrincipalPage(
    sessionViewModel: SessionViewModel,
    clienteViewModel: ClienteViewModel,
    servicoViewModel: ServicoViewModel,
    agendamentoViewModel: AgendamentoViewModel,
    barbeariaViewModel: BarbeariaViewModel,
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

                // Botões do Drawer
                DrawerButton("Inicio", Icons.Filled.Home, rotaAtual == TelaRotasBottom.TelaInicio) {
                    navController.navigate(TelaRotasBottom.TelaInicio)
                    coroutineScope.launch { drawerState.close() }
                }

                DrawerButton("Pesquisar Barbearias", Icons.Filled.Search, rotaAtual == TelaRotasBottom.TelaSearchBarber) {
                    navController.navigate(TelaRotasBottom.TelaSearchBarber)
                    coroutineScope.launch { drawerState.close() }
                }

                DrawerButton("Meus Agendamentos", Icons.Filled.CalendarMonth, rotaAtual == TelaRotasBottom.TelaMeusAgendamentos) {
                    navController.navigate(TelaRotasBottom.TelaMeusAgendamentos)
                    coroutineScope.launch { drawerState.close() }
                }

                DrawerButton("Segurança", Icons.Filled.Lock, rotaAtual == TelaRotasBottom.TelaSeguranca) {
                    navController.navigate(TelaRotasBottom.TelaSeguranca)
                    coroutineScope.launch { drawerState.close() }
                }

                DrawerButton("Meus Acessos", Icons.Filled.ManageAccounts, rotaAtual == TelaRotasBottom.TelaMeusAcessos) {
                    navController.navigate(TelaRotasBottom.TelaMeusAcessos)
                    coroutineScope.launch { drawerState.close() }
                }

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
                    label = "Minha Conta",
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
                // Outros botões...
                Spacer(modifier = Modifier.height(300.dp))

                // Botão de Logout
                TextButton(onClick = {
                    onLogout()
                    sessionViewModel.logout()
                    coroutineScope.launch { drawerState.close() }
                }) {
                    Text(color = Color.Red, text = "Sair", fontSize = 20.sp, modifier = Modifier.padding(30.dp, 5.dp))
                }
            }
        },
        content = {
            BarberNavHost(
                navController = navController,
                state = drawerState,
                sessionViewModel = sessionViewModel,
                clienteViewModel = clienteViewModel,
                servicoViewModel = servicoViewModel,
                agendamentoViewModel = agendamentoViewModel,
                barbeariaViewModel = barbeariaViewModel
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
