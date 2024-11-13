package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.repositories.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ServicoViewModel(
    private val repository: IRepository<Servico>
): ViewModel() {

    private val _servico = MutableStateFlow<List<Servico>>(emptyList())
    val servicos: StateFlow<List<Servico>> get() = _servico

    init {
        viewModelScope.launch {
            repository.listar().collectLatest { listaDeServicos  ->
                _servico.value = listaDeServicos
            }
        }
    }

    suspend fun buscarServicoPorId(servicoId: Int): Servico?{
        // return withContext(Dispatchers.IO) {
        return repository.buscarPorId(servicoId.toString())
        //}
    }

    fun gravarServico(servico: Servico){
        viewModelScope.launch {
            repository.gravar(servico)
        }
    }

    fun excluirServico(servico: Servico){
        viewModelScope.launch {
            repository.excluir(servico)
        }
    }
}