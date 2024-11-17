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
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel
import br.edu.up.nowbarber.ui.views.*

@Composable
fun BarberNavHost(
    navController: NavHostController,
    state: DrawerState,
    servicoViewModel: ServicoViewModel,
    clienteViewModel: ClienteViewModel,
    sessionViewModel: SessionViewModel // SessionViewModel gerenciando o usuário logado
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
            TelaAgendamento(state, clienteViewModel, servicoViewModel)
        }
        composable(TelaRotasBottom.TelaSeguranca) {
            TelaSeguranca(state, sessionViewModel)
        }
        composable(TelaRotasBottom.TelaMeusAcessos) {
            TelaMeusAcessos(state, sessionViewModel)
        }
        composable(TelaRotasBottom.TelaPayments) {
            TelaPayments(state)
        }
        composable(TelaRotasBottom.TelaAccountUser) {
            TelaAccountUser(state, sessionViewModel,clienteViewModel)
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
            TelaLogin(navController, sessionViewModel, onLoginSuccess = {
                    navController.navigate(TelaRotasBottom.TelaInicio) {
                        popUpTo(TelaRotasBottom.TelaLogin) { inclusive = true }
                    }
                }
            )
        }



        composable(TelaRotasBottom.TelaCadastroLogin) {
            TelaCadastroLogin(navController, sessionViewModel) {
                navController.navigate(TelaRotasBottom.TelaLogin) {
                    popUpTo(TelaRotasBottom.TelaCadastroLogin) { inclusive = true }
                }
            }
        }
    }
}
