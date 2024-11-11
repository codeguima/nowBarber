package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.repositories.ClienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ClienteViewModel(
    private val repository: ClienteRepository
) : ViewModel() {

    private val _cliente = MutableStateFlow<List<Cliente>>(emptyList())
    val lista: StateFlow<List<Cliente>> get() = _cliente

    // Novo StateFlow para refletir o status do login
    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: StateFlow<Boolean?> get() = _loginStatus

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> get() = _loginSuccess

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> get() = _errorMessage


    init {
        viewModelScope.launch {
            repository.listar().collectLatest { lista ->
                _cliente.value = lista
            }
        }
    }

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            val result = repository.verificarLogin(email, senha)
            if (result) {
                _loginSuccess.value = true
                _errorMessage.value = ""
            } else {
                _loginSuccess.value = false
                _errorMessage.value = "Credenciais inválidas"
            }
        }
    }


    suspend fun buscarClientePorId(clienteId: Int): Cliente? {
        return repository.buscarPorId(clienteId)
    }

    fun gravarCliente(cliente: Cliente) {
        viewModelScope.launch {
            repository.gravar(cliente)
        }
    }

    fun excluirCliente(cliente: Cliente) {
        viewModelScope.launch {
            repository.excluir(cliente)
        }
    }

    // Função para autenticar o login do cliente
    fun fazerLogin(email: String, senha: String) {
        viewModelScope.launch {
            _loginStatus.value = repository.verificarLogin(email, senha)
        }
    }
}
