package br.edu.up.nowbarber.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import br.edu.up.nowbarber.data.models.Barbearia
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.repositories.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BarbeariaViewModel(
    private val repository: IRepository<Barbearia>
) : ViewModel() {

    private val _barbearias = MutableStateFlow<List<Barbearia>>(emptyList())
    val barbearias: StateFlow<List<Barbearia>> get() = _barbearias

    init {
        carregarBarbearias()
    }

    private fun carregarBarbearias() {
        viewModelScope.launch {
            repository.listar().collect {
                _barbearias.value = it
            }
        }
    }

    suspend fun buscarPorId(id: String): Barbearia? = repository.buscarPorId(id)


    fun buscarPorCidade(cidade: String) {
        viewModelScope.launch {
            val lista = repository.buscarPorCidade(cidade)
            _barbearias.value = lista
        }
    }


    fun gravar(barbearia: Barbearia) {
        viewModelScope.launch {
            repository.gravar(barbearia)
            carregarBarbearias()
        }
    }

    fun excluir(barbearia: Barbearia) {
        viewModelScope.launch {
            repository.excluir(barbearia)
            carregarBarbearias()
        }
    }


}

