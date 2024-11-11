package br.edu.up.nowbarber.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.up.nowbarber.data.models.originalBarbearias
import br.edu.up.nowbarber.ui.components.BottomAppBar
import br.edu.up.nowbarber.ui.views.TelaAgendamento
import br.edu.up.nowbarber.ui.views.TelaCadastro
import br.edu.up.nowbarber.ui.views.TelaInicio
import br.edu.up.nowbarber.ui.views.TelaLogin
import br.edu.up.nowbarber.ui.views.TelaSearchBarber
import br.edu.up.nowbarber.ui.components.TelaDetalhesBarbearia


@Composable
fun MenuRotas(state: DrawerState) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TelaRotasBottom.TelaInicio
    ) {
        composable(TelaRotasBottom.TelaInicio) {
            TelaInicio(state) { BottomAppBar(navController) }
        }
        composable(TelaRotasBottom.TelaSearchBarber) {
            TelaSearchBarber(state, navController) { BottomAppBar(navController) }
        }
        composable(TelaRotasBottom.TelaAgendamento) {
            TelaAgendamento(state) { BottomAppBar(navController) }
        }
        composable(TelaRotasBottom.TelaDetalhesBarbearia + "/{barbeariaNome}") { backStackEntry ->
            val barbeiroNome = backStackEntry.arguments?.getString("barbeariaNome")
            val barbeiro = originalBarbearias.find { it.name == barbeiroNome }
            barbeiro?.let {
                TelaDetalhesBarbearia(it, navController)
            }
        }

        // Nova rota para Tela de Login
        composable(TelaRotasBottom.TelaLogin) {
            TelaLogin(navController = navController) { /* Lógica para login bem-sucedido */ }
        }
        // Nova rota para Tela de Cadastro
        composable(TelaRotasBottom.TelaCadastro) {
            TelaCadastro(navController = navController) { /* Lógica para registro bem-sucedido */ }
        }
    }
}

