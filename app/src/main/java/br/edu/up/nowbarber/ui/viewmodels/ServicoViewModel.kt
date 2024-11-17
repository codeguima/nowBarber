package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.models.servicoDBarbearia
import br.edu.up.nowbarber.data.repositories.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class ServicoViewModel(
    private val repository: IRepository<Servico>
) : ViewModel() {

    private val _servico = MutableStateFlow<List<Servico>>(emptyList())
    val servicos: StateFlow<List<Servico>> get() = _servico

    init {
        // Tente carregar os dados do repositório
        viewModelScope.launch {
            try {
                // Coleta os dados do Flow emitido por repository.listar()
                repository.listar().collectLatest { listaDeServicos ->
                    // Log para ver a lista de serviços
                    println("Serviços carregados do repositório: $listaDeServicos")
                    _servico.value = listaDeServicos
                }
            } catch (e: Exception) {
                // Se falhar ao carregar do repositório, use a lista estática
                println("Falha ao carregar serviços, usando lista estática: ${servicoDBarbearia}")
                _servico.value = servicoDBarbearia
            }
        }
    }


    suspend fun buscarPorId(servicoId: StateFlow<String?>): Servico? {
        return servicoId?.let { repository.buscarPorId(it.toString()) }
    }


    fun gravarServico(servico: Servico) {
        viewModelScope.launch {
            repository.gravar(servico)
        }
    }


    fun excluirServico(servico: Servico) {
        viewModelScope.launch {
            repository.excluir(servico)
        }
    }
}
