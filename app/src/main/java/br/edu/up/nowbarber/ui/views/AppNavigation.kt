package br.edu.up.nowbarber.ui.views


import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.up.nowbarber.ui.navigation.TelaRotasBottom
import br.edu.up.nowbarber.ui.viewmodels.AgendamentoViewModel
import br.edu.up.nowbarber.ui.viewmodels.BarbeariaViewModel
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel

@Composable
fun AppNavigation(
    sessionViewModel: SessionViewModel,
    clienteViewModel: ClienteViewModel,
    servicoViewModel: ServicoViewModel,
    agendamentoViewModel : AgendamentoViewModel,
    barbeariaViewModel : BarbeariaViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TelaRotasBottom.TelaLogin
    ) {
        composable(TelaRotasBottom.TelaLogin) {
            TelaLogin(navController, sessionViewModel, onLoginSuccess = {
                navController.navigate(TelaRotasBottom.TelaInicio) {
                    popUpTo(TelaRotasBottom.TelaLogin) { inclusive = true }
                }
            })
        }

        composable(TelaRotasBottom.TelaCadastroLogin) {
            TelaCadastroLogin(navController, sessionViewModel) {
                // Após o cadastro de login, navegue para a tela de Cadastro Cliente
                navController.navigate(TelaRotasBottom.TelaCadastroCliente) {
                    popUpTo(TelaRotasBottom.TelaLogin) { inclusive = true }
                }
            }
        }

        composable(TelaRotasBottom.TelaCadastroCliente) {
            TelaCadastroCliente(navController, clienteViewModel) {
                // Após o cadastro do cliente, redireciona para a tela de login
                navController.navigate(TelaRotasBottom.TelaLogin) {
                    popUpTo(TelaRotasBottom.TelaCadastroCliente) { inclusive = true }
                }
            }
        }

        composable(TelaRotasBottom.TelaInicio) {
            PrincipalPage(
                sessionViewModel = sessionViewModel,
                clienteViewModel = clienteViewModel,
                servicoViewModel = servicoViewModel,
                agendamentoViewModel = agendamentoViewModel,
                barbeariaViewModel = barbeariaViewModel,
                onLogout = {
                    sessionViewModel.logout()
                    navController.navigate(TelaRotasBottom.TelaLogin) {
                        popUpTo(TelaRotasBottom.TelaInicio) { inclusive = true }
                    }
                }
            )
        }
    }
}
