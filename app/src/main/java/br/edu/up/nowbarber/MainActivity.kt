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
import androidx.compose.runtime.LaunchedEffect
import br.edu.up.nowbarber.data.repositories.AgendamentoLocalRepository
import br.edu.up.nowbarber.data.repositories.AgendamentoRemoteRepository
import br.edu.up.nowbarber.data.repositories.AgendamentoRepository
import br.edu.up.nowbarber.ui.viewmodels.AgendamentoViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialização de dependências
        val dependencies = provideDependencies()

        setContent {
            // Instância de SessionViewModel (para gerenciar sessões de usuários)
            val sessionViewModel: SessionViewModel = viewModel()

            // Carregar usuarioId assim que a Activity for criada
            LaunchedEffect(Unit) {
                sessionViewModel.carregarUsuarioId()
            }

            // ViewModels específicos
            val clienteViewModel = ClienteViewModel(dependencies.clienteRepository)
            val servicoViewModel = ServicoViewModel(dependencies.servicoRepository)
            val agendamentoViewModel = AgendamentoViewModel(dependencies.agendamentoRepository, dependencies.servicoRepository)

            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(
                        sessionViewModel = sessionViewModel,
                        clienteViewModel = clienteViewModel,
                        servicoViewModel = servicoViewModel,
                        agendamentoViewModel = agendamentoViewModel
                    )
                }
            }
        }
    }

    private fun provideDependencies(): DependencyProvider {
        val db = abrirBanco(this)

        val servicoRemoteRepository = ServicoRemoteRepository()
        val servicoLocalRepository = ServicoLocalRepository(db.getServicoDao())
        val servicoRepository = ServicoRepository(servicoRemoteRepository, servicoLocalRepository)

        val clienteRemoteRepo = ClienteRemoteRepository()
        val clienteLocalRepo = ClienteLocalRepository(db.getClienteDao())
        val clienteRepository = ClienteRepository(clienteRemoteRepo, clienteLocalRepo)

        val agendamentoRemoteRepo = AgendamentoRemoteRepository()
        val agendamentoLocalRepo = AgendamentoLocalRepository(db.getAgendamentoDao())
        val agendamentoRepository = AgendamentoRepository(agendamentoLocalRepo,agendamentoRemoteRepo )

        return DependencyProvider(
            clienteRepository = clienteRepository,
            servicoRepository = servicoRepository,
            agendamentoRepository = agendamentoRepository
        )
    }
}

data class DependencyProvider(
    val clienteRepository: ClienteRepository,
    val servicoRepository: ServicoRepository,
    val agendamentoRepository : AgendamentoRepository
)
