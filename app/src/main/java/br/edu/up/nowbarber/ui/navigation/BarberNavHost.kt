package br.edu.up.nowbarber.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.edu.up.nowbarber.data.models.originalBarbearias
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel


import br.edu.up.nowbarber.ui.views.*

@Composable
fun BarberNavHost(
    navController: NavHostController,
    state: DrawerState,
    servicoViewModel: ServicoViewModel,  // ViewModel já passado
    clienteViewModel: ClienteViewModel
) {
    NavHost(
        navController = navController,
        startDestination = TelaRotasBottom.TelaInicio
    ) {
        composable(TelaRotasBottom.TelaInicio) {
            TelaInicio(state)
        }
        composable(TelaRotasBottom.TelaSearchBarber) {
            TelaSearchBarber(state, navController)
        }
        composable(TelaRotasBottom.TelaAgendamento) {
            TelaAgendamento(state)
        }
        composable(TelaRotasBottom.TelaSeguranca) {
            TelaSeguranca(state)
        }
        composable(TelaRotasBottom.TelaMeusAcessos) {
            TelaMeusAcessos(state)
        }
        composable(TelaRotasBottom.TelaPayments) {
            TelaPayments(state)
        }
        composable(TelaRotasBottom.TelaAccountUser) {
            TelaAccountUser(state)
        }
        composable(TelaRotasBottom.TelaFavoritos) {
            TelaFavoritos(state)
        }
        composable(TelaRotasBottom.TelaDetalhesBarbearia + "/{barbeariaNome}") { backStackEntry ->
            val barbeiroNome = backStackEntry.arguments?.getString("barbeariaNome")
            val barbeiro = originalBarbearias.find { it.name == barbeiroNome }
            if (barbeiro != null) {
                TelaDetalhesBarbearia(state, navController, servicoViewModel) // Passando viewModel
            } else {
                Text("Barbearia não encontrada")
            }
        }
        composable(TelaRotasBottom.TelaLogin) {
            TelaLogin(navController) { /* Lógica para login bem-sucedido */ }
        }
        composable(TelaRotasBottom.TelaCadastro) {
            TelaCadastro(navController, clienteViewModel) {
                navController.navigate(TelaRotasBottom.TelaLogin) {
                    popUpTo(TelaRotasBottom.TelaCadastro) { inclusive = true }
                }
            }
        }
    }
}
