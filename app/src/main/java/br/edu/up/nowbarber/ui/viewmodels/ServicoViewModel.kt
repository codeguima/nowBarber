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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        carregarServicos()
    }

    private fun carregarServicos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.listar().collect { listaServicos ->
                    _servicos.value = listaServicos
                }
            } catch (e: Exception) {
                _servicos.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun gravarServico(servico: Servico) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.gravar(servico)
                _servicos.value = _servicos.value + servico
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun excluirServico(servico: Servico) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.excluir(servico)
                _servicos.value = _servicos.value.filter { it.id != servico.id }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
