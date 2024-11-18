package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.repositories.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClienteViewModel(
    private val repository: IRepository<Cliente>
) : ViewModel() {

    private val _cliente = MutableStateFlow<List<Cliente>>(emptyList())
    val lista: StateFlow<List<Cliente>> get() = _cliente

    init {
        carregarClientes()
    }

    private fun carregarClientes() {
        viewModelScope.launch {
            repository.listar().collect { lista ->
                _cliente.value = lista  // Atualiza a lista reativamente
            }
        }
    }

    suspend fun buscarPorId(clienteId: String): Cliente? {
        return repository.buscarPorId(clienteId)
    }

    fun gravar(cliente: Cliente) {
        viewModelScope.launch {
            repository.gravar(cliente)
            carregarClientes()  // Recarrega a lista após gravar
        }
    }

    fun atualizarEmail(id: String, novoEmail: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {

            repository.atualizarEmail(id, novoEmail).collect { result ->

                if (result.isSuccess) {
                    onSuccess()
                } else {
                    val errorMessage = result.exceptionOrNull()?.message ?: "Erro desconhecido"
                    onError(errorMessage)
                }
            }
        }
    }


    fun excluir(cliente: Cliente) {
        viewModelScope.launch {
            repository.excluir(cliente)
            carregarClientes()  // Recarrega a lista após exclusão
        }
    }
}

