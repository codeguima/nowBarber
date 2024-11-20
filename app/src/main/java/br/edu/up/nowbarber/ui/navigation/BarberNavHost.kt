package br.edu.up.nowbarber.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.edu.up.nowbarber.ui.viewmodels.AgendamentoViewModel
import br.edu.up.nowbarber.ui.viewmodels.BarbeariaViewModel
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
    sessionViewModel: SessionViewModel,
    agendamentoViewModel: AgendamentoViewModel,
    barbeariaViewModel: BarbeariaViewModel
) {

    NavHost(
        navController = navController,
        startDestination = TelaRotasBottom.TelaInicio
    ) {
        composable(TelaRotasBottom.TelaInicio) {
            TelaInicio(state)
        }
        composable(TelaRotasBottom.TelaSearchBarber) {
            TelaSearchBarber(state, navController, barbeariaViewModel )
        }
        composable(TelaRotasBottom.TelaMeusAgendamentos) {
            TelaMeusAgendamentos(state, agendamentoViewModel)
        }
        composable(TelaRotasBottom.TelaSeguranca) {
            TelaSeguranca(state, sessionViewModel)
        }
        composable(TelaRotasBottom.TelaMeusAcessos) {
            TelaMeusAcessos(state, sessionViewModel, clienteViewModel)
        }
        composable(TelaRotasBottom.TelaPayments) {
            TelaPayments(state)
        }
        composable(TelaRotasBottom.TelaAccountUser) {
            TelaAccountUser(state, sessionViewModel, clienteViewModel)
        }
        composable(TelaRotasBottom.TelaFavoritos) {
            TelaFavoritos(state)
        }

        composable(
            route = TelaRotasBottom.TelaDetalhesBarbearia + "/{barbeariaId}",
            arguments = listOf(
                navArgument("barbeariaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val barbeariaId = backStackEntry.arguments?.getString("barbeariaId")

            TelaDetalhesBarbearia(state, navController, servicoViewModel, agendamentoViewModel,
                clienteViewModel, sessionViewModel, barbeariaId
            )
        }



        composable(TelaRotasBottom.TelaLogin) {
            TelaLogin(navController, sessionViewModel, onLoginSuccess = {
                navController.navigate(TelaRotasBottom.TelaInicio) {
                    popUpTo(TelaRotasBottom.TelaLogin) { inclusive = true }
                }
            })
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
