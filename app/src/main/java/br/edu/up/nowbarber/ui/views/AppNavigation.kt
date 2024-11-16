package br.edu.up.nowbarber.ui.views


import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.up.nowbarber.ui.navigation.TelaRotasBottom
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel

@Composable
fun AppNavigation(
    sessionViewModel: SessionViewModel,
    clienteViewModel: ClienteViewModel,
    servicoViewModel: ServicoViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TelaRotasBottom.TelaLogin
    ) {
        composable(TelaRotasBottom.TelaLogin) {
            TelaLogin(
                navController = navController,
                clienteViewModel = clienteViewModel,
                onLoginSuccess = {
                    navController.navigate(TelaRotasBottom.TelaInicio) {
                        popUpTo(TelaRotasBottom.TelaLogin) { inclusive = true }
                    }
                }
            )
        }


        composable(TelaRotasBottom.TelaCadastro) {
            TelaCadastro(navController, clienteViewModel) {
                navController.navigate(TelaRotasBottom.TelaLogin) {
                    popUpTo(TelaRotasBottom.TelaCadastro) { inclusive = true }
                }
            }
        }

        composable(TelaRotasBottom.TelaInicio) {
            PrincipalPage(
                sessionViewModel = sessionViewModel,
                clienteViewModel = clienteViewModel,
                servicoViewModel = servicoViewModel,
                onLogout = {
                    sessionViewModel.logOut()
                    navController.navigate(TelaRotasBottom.TelaLogin) {
                        popUpTo(TelaRotasBottom.TelaInicio) { inclusive = true }
                    }
                }
            )
        }
    }
}
