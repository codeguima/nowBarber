package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Barbearia
import br.edu.up.nowbarber.data.repositories.BarbeariaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class BarbeariaViewModel(
    private val repository: BarbeariaRepository
): ViewModel() {

    // Estado para armazenar a lista de barbearias
    private val _barbearias = MutableStateFlow<List<Barbearia>>(emptyList())
    val barbearias: StateFlow<List<Barbearia>> = _barbearias

    // Estado para armazenar a busca
    private val _resultadoBusca = MutableStateFlow<List<Barbearia>>(emptyList())
    val resultadoBusca: StateFlow<List<Barbearia>> = _resultadoBusca

    init {
        // Carregar as barbearias no início
        viewModelScope.launch {
            repository.listar().collectLatest { lista ->
                _barbearias.value = lista
            }
        }
    }

    fun buscarBarbearia(nome: String) {
        viewModelScope.launch {
            val resultado = _barbearias.value.filter { it.name.contains(nome, ignoreCase = true) }
            _resultadoBusca.value = resultado // Armazenar os resultados de busca em _resultadoBusca
        }
    }


    suspend fun buscarBarbeariaPorId(barbeariaId: Int): Barbearia? {
        return repository.buscarPorId(barbeariaId)
    }

    fun gravarBarbearia(barbearia: Barbearia) {
        viewModelScope.launch {
            try {
                repository.gravar(barbearia)
            } catch (e: Exception) {
                // Tratar erro (exibir mensagem, log, etc.)
            }
        }
    }

    fun excluirBarbearia(barbearia: Barbearia) {
        viewModelScope.launch {
            repository.excluir(barbearia)
        }
    }
}
