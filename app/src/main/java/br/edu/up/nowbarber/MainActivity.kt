package br.edu.up.nowbarber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.views.PrincipalPage
import br.edu.up.nowbarber.ui.views.TelaCadastro
import br.edu.up.nowbarber.ui.views.TelaLogin
import br.edu.up.nowbarber.data.repositories.ServicoRepository
import br.edu.up.nowbarber.data.repositories.ServicoRemoteRepository
import br.edu.up.nowbarber.data.repositories.ServicoLocalRepository
import br.edu.up.nowbarber.ui.navigation.TelaRotasBottom
import br.edu.up.nowbarber.data.db.abrirBanco
import br.edu.up.nowbarber.data.repositories.ClienteLocalRepository
import br.edu.up.nowbarber.data.repositories.ClienteRemoteRepository
import br.edu.up.nowbarber.data.repositories.ClienteRepository

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {




            //Abertura do banco de dados

            val db = remember { abrirBanco(this) }


            // Criação dos repositórios
            val servicoRemoteRepo = ServicoRemoteRepository()
            val servicoLocalRepo = ServicoLocalRepository(db.getServicoDao())

            val clienteRemoteRepo = ClienteRemoteRepository()
            val clienteLocalRepo = ClienteLocalRepository(db.getClienteoDao())

            // Criação do repositório de serviço
            val servicoRepository = ServicoRepository(servicoRemoteRepo, servicoLocalRepo)


            // Instanciando o ClienteViewModel (passando o repositório de serviço conforme necessário)
            val clienteRepository = ClienteRepository(clienteRemoteRepo, clienteLocalRepo)

            val clienteViewModel = ClienteViewModel(clienteRepository)


            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(clienteViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(clienteViewModel: ClienteViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TelaRotasBottom.TelaLogin // Tela de login
    ) {
        composable(TelaRotasBottom.TelaLogin) {
            TelaLogin(navController, clienteViewModel) {  // Ação de login
                navController.navigate(TelaRotasBottom.TelaInicio) {
                    popUpTo(TelaRotasBottom.TelaLogin) { inclusive = true }
                }
            }
        }

        composable(TelaRotasBottom.TelaCadastro) {
            TelaCadastro(navController,clienteViewModel) {
                navController.navigate(TelaRotasBottom.TelaLogin) {  // Navegação após o sucesso do cadastro
                    popUpTo(TelaRotasBottom.TelaCadastro) { inclusive = true }
                }
            }
        }

        composable(TelaRotasBottom.TelaInicio) {
            PrincipalPage(onLogout = {
                navController.navigate(TelaRotasBottom.TelaLogin) {
                    popUpTo(TelaRotasBottom.TelaInicio) { inclusive = true }
                }
            })
        }
    }
}
