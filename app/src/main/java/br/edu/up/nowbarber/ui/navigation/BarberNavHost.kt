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
    servicoViewModel: ServicoViewModel,  // ViewModel para serviços
    clienteViewModel: ClienteViewModel,   // ViewModel para cliente
    usuarioId: Int // Passando o usuarioId para ser usado nas telas
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
            // Passando o usuarioId e clienteViewModel para manipular dados de segurança
            TelaSeguranca(state, clienteViewModel, usuarioId)
        }
        composable(TelaRotasBottom.TelaMeusAcessos) {
            // Passando o usuarioId para tela de meus acessos
            TelaMeusAcessos(state, usuarioId)
        }
        composable(TelaRotasBottom.TelaPayments) {
            TelaPayments(state)
        }
        composable(TelaRotasBottom.TelaAccountUser) {
            // Passando o usuarioId para tela de conta do usuário
            TelaAccountUser(state, usuarioId)
        }
        composable(TelaRotasBottom.TelaFavoritos) {
            TelaFavoritos(state)
        }
        composable(TelaRotasBottom.TelaDetalhesBarbearia + "/{barbeariaNome}") { backStackEntry ->
            val barbeiroNome = backStackEntry.arguments?.getString("barbeariaNome")
            val barbeiro = originalBarbearias.find { it.name == barbeiroNome }
            if (barbeiro != null) {
                TelaDetalhesBarbearia(state, navController, servicoViewModel) // Passando o ViewModel de serviços
            } else {
                Text("Barbearia não encontrada")
            }
        }
        composable(TelaRotasBottom.TelaLogin) {
            TelaLogin(navController, clienteViewModel) {
                navController.navigate(TelaRotasBottom.TelaInicio) {
                    popUpTo(TelaRotasBottom.TelaLogin) { inclusive = true }
                }
            }
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
