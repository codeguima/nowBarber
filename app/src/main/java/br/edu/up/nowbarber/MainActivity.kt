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
import br.edu.up.nowbarber.data.models.ServicoDao
import br.edu.up.nowbarber.data.repositories.IRepository
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.views.PrincipalPage
import br.edu.up.nowbarber.ui.views.TelaCadastro
import br.edu.up.nowbarber.ui.views.TelaLogin
import br.edu.up.nowbarber.data.repositories.ServicoRepository
import br.edu.up.nowbarber.data.repositories.ServicoRemoteRepository
import br.edu.up.nowbarber.data.repositories.ServicoLocalRepository
import br.edu.up.nowbarber.ui.navigation.TelaRotasBottom
import br.edu.up.nowbarber.data.db.abrirBanco

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


        val isLocal = false

            //Abertura do banco de dados
            //val context = LocalContext.current

                val db = remember { abrirBanco(this) }


                // Criação dos repositórios
                val servicoRemoteRepo = ServicoRemoteRepository()
                val servicoLocalRepo = ServicoLocalRepository(db.getServicoDao())

                // Criação do repositório de serviço
                val servicoRepository = ServicoRepository(servicoRemoteRepo, servicoLocalRepo)

                // Instanciando o ClienteViewModel (passando o repositório de serviço conforme necessário)
                val clienteViewModel = ClienteViewModel(repository = servicoRepository)  // Dependendo de onde o ClienteRepository se conecta ao ServicoRepository


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
            TelaLogin(navController) {  // Ação de login
                navController.navigate(TelaRotasBottom.TelaInicio) {
                    popUpTo(TelaRotasBottom.TelaLogin) { inclusive = true }
                }
            }
        }

        composable(TelaRotasBottom.TelaCadastro) {
            TelaCadastro(
                navController = navController,
                clienteViewModel = clienteViewModel  // Passando o ViewModel de Cliente
            ) {
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
