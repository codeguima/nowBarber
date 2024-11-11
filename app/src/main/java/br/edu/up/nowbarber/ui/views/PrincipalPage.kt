package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.edu.up.nowbarber.ui.navigation.MenuRotas
import br.edu.up.nowbarber.R


import kotlinx.coroutines.launch

object BarberAppRotas {
    const val MENU_ROTAS = "navegação"
    const val TELA_SEGURANCA = "segurança"
    const val TELA_MEUS_ACESSOS = "meus acessos"
    const val TELA_PAYMENTS = "cartoes"
    const val TELA_ACCOUNT_USER = "conta"
    const val TELA_FAVORITOS = "favoritos"
}


@Composable
fun PrincipalPage(onLogout: () -> Unit) {
    val state = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val navController = rememberNavController()
    val currentBack by navController.currentBackStackEntryAsState()
    val rotaAtual = currentBack?.destination?.route ?: BarberAppRotas.MENU_ROTAS

    val telaUmSelect = rotaAtual == BarberAppRotas.MENU_ROTAS
    val SecuritySelect = rotaAtual == BarberAppRotas.TELA_SEGURANCA
    val AcessSelect = rotaAtual == BarberAppRotas.TELA_MEUS_ACESSOS
    val PaymentsSelect = rotaAtual == BarberAppRotas.TELA_PAYMENTS
    val AccountSelect = rotaAtual == BarberAppRotas.TELA_ACCOUNT_USER
    val FavoriteSelect = rotaAtual == BarberAppRotas.TELA_FAVORITOS


    ModalNavigationDrawer(
        drawerState = state,
        drawerContent = {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .background(colorResource(id = R.color.principal))
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                TextButton( colors = ButtonDefaults.buttonColors(
                    containerColor = getBack(telaUmSelect)
                ),
                    onClick = {
                        navController.navigate(BarberAppRotas.MENU_ROTAS)
                        coroutineScope.launch { state.close() }
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Inicio",
                        modifier = Modifier.size(30.dp),
                        tint = getTint(telaUmSelect))

                    Text(
                        color = getTint(telaUmSelect),
                        text = "Inicio", fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getBack(SecuritySelect)
                    ),
                    onClick = {
                        navController.navigate(BarberAppRotas.TELA_SEGURANCA)
                        coroutineScope.launch { state.close() }
                    }) {

                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Segurança",
                        modifier = Modifier.size(30.dp),
                        tint = getTint(SecuritySelect))

                    Text(
                        color = getTint(SecuritySelect),
                        text = "Segurança", fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }


                TextButton( colors = ButtonDefaults.buttonColors(
                    containerColor = getBack(AcessSelect)
                ),
                    onClick = {
                        navController.navigate(BarberAppRotas.TELA_MEUS_ACESSOS)
                        coroutineScope.launch { state.close() }
                    }) {


                    Icon(
                        imageVector = Icons.Filled.ManageAccounts,
                        contentDescription = "Meus Acessos",
                        modifier = Modifier.size(30.dp),
                        tint = getTint(AcessSelect))

                    Text(
                        color = getTint(AcessSelect),
                        text = "Meus Acessos", fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                TextButton( colors = ButtonDefaults.buttonColors(
                    containerColor = getBack(PaymentsSelect)
                ),
                    onClick = {
                        navController.navigate(BarberAppRotas.TELA_PAYMENTS)
                        coroutineScope.launch { state.close() }
                    }) {


                    Icon(
                        imageVector = Icons.Filled.Payment,
                        contentDescription = "Cartões",
                        modifier = Modifier.size(30.dp),
                        tint = getTint(PaymentsSelect))

                    Text(
                        color = getTint(PaymentsSelect),
                        text = "Cartões", fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                TextButton( colors = ButtonDefaults.buttonColors(
                    containerColor = getBack(AccountSelect)
                ),
                    onClick = {
                        navController.navigate(BarberAppRotas.TELA_ACCOUNT_USER)
                        coroutineScope.launch { state.close() }
                    }) {


                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Conta",
                        modifier = Modifier.size(30.dp),
                        tint = getTint(AccountSelect))

                    Text(
                        color = getTint(AccountSelect),
                        text = "Conta", fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getBack(FavoriteSelect)
                    ),
                    onClick = {
                        navController.navigate(BarberAppRotas.TELA_FAVORITOS)
                        coroutineScope.launch { state.close() }
                    }) {

                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Favoritos",
                        modifier = Modifier.size(30.dp),
                        tint = getTint(FavoriteSelect))

                    Text(
                        color = getTint(FavoriteSelect),
                        text = "Favoritos", fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(300.dp))

                TextButton(
                    onClick = {
                        onLogout()
                        coroutineScope.launch { state.close() }
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
            BarberNavHost(navController, state)
        }
    )
}

@Composable
private fun BarberNavHost(
    navController: NavHostController,
    state: DrawerState
) {
    NavHost(
        navController = navController,
        startDestination = BarberAppRotas.MENU_ROTAS
    ) {
        composable(BarberAppRotas.MENU_ROTAS) {
            MenuRotas(state)
        }
        composable(BarberAppRotas.TELA_SEGURANCA) {
            TelaSeguranca(state)
        }
        composable(BarberAppRotas.TELA_MEUS_ACESSOS) {
            TelaMeusAcessos(state)
        }
        composable(BarberAppRotas.TELA_PAYMENTS) {
            TelaPayments(state)
        }
        composable(BarberAppRotas.TELA_ACCOUNT_USER) {
            TelaAccountUser(state)
        }
        composable(BarberAppRotas.TELA_FAVORITOS) {
            TelaFavoritos(state)
        }

    }
}


fun getTint(selected: Boolean): Color {
    return if (selected) Color.Black
    else Color.DarkGray
}

fun getBack(selected: Boolean): Color {
    return if (selected) Color.White
    else Color.Transparent
}


