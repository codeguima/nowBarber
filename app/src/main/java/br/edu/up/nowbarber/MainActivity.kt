package br.edu.up.nowbarber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.data.repositories.ServicoRepository
import br.edu.up.nowbarber.data.repositories.ServicoRemoteRepository
import br.edu.up.nowbarber.data.repositories.ServicoLocalRepository
import br.edu.up.nowbarber.data.db.abrirBanco
import br.edu.up.nowbarber.data.repositories.ClienteLocalRepository
import br.edu.up.nowbarber.data.repositories.ClienteRemoteRepository
import br.edu.up.nowbarber.data.repositories.ClienteRepository
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel
import br.edu.up.nowbarber.ui.views.AppNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialização de dependências
        val dependencies = provideDependencies()

        setContent {
            // Instância de SessionViewModel (para gerenciar sessões de usuários)
            val sessionViewModel: SessionViewModel = viewModel()

            // ViewModels específicos
            val clienteViewModel = ClienteViewModel(dependencies.clienteRepository)
            val servicoViewModel = ServicoViewModel(dependencies.servicoRepository)

            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(
                        sessionViewModel = sessionViewModel,
                        clienteViewModel = clienteViewModel,
                        servicoViewModel = servicoViewModel
                    )
                }
            }
        }
    }

    private fun provideDependencies(): DependencyProvider {
        val db = abrirBanco(this)

        val servicoRemoteRepo = ServicoRemoteRepository()
        val servicoLocalRepo = ServicoLocalRepository(db.getServicoDao())
        val servicoRepository = ServicoRepository(servicoRemoteRepo, servicoLocalRepo)

        val clienteRemoteRepo = ClienteRemoteRepository()
        val clienteLocalRepo = ClienteLocalRepository(db.getClienteoDao())
        val clienteRepository = ClienteRepository(clienteRemoteRepo, clienteLocalRepo)

        return DependencyProvider(
            clienteRepository = clienteRepository,
            servicoRepository = servicoRepository
        )
    }
}

// Classe de provider personalizada para suas dependências
data class DependencyProvider(
    val clienteRepository: ClienteRepository,
    val servicoRepository: ServicoRepository
)
