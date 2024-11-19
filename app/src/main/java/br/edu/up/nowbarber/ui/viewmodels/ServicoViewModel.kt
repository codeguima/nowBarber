package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.repositories.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ServicoViewModel(
    private val repository: IRepository<Servico>
) : ViewModel() {

    private val _servicos = MutableStateFlow<List<Servico>>(emptyList())
    val servicos: StateFlow<List<Servico>> get() = _servicos

    init {
        carregarServicos()
    }

    private fun carregarServicos() {
        viewModelScope.launch {
            try {
                repository.listar().collect {
                    _servicos.value = it
                }
            } catch (e: Exception) {

                _servicos.value = emptyList()

            }
        }
    }


    suspend fun buscarPorId(id: String): Servico? = repository.buscarPorId(id)

    fun gravar(servico: Servico) {
        viewModelScope.launch {
            repository.gravar(servico)
            carregarServicos()
        }
    }

    fun excluir(servico: Servico) {
        viewModelScope.launch {
            repository.excluir(servico)
            carregarServicos()
        }
    }
}
