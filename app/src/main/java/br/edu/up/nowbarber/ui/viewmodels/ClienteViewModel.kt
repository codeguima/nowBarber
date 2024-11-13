package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.repositories.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ClienteViewModel(
    private val repository: IRepository<Cliente>
) : ViewModel() {

    private val _cliente = MutableStateFlow<List<Cliente>>(emptyList())
    val lista: StateFlow<List<Cliente>> get() = _cliente

    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: LiveData<Boolean?> get() = _loginStatus.asLiveData()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: LiveData<String> get() = _errorMessage.asLiveData()

    init {
        carregarClientes()
    }

    private fun carregarClientes() {
        viewModelScope.launch {
            repository.listar().collectLatest { lista ->
                _cliente.value = lista
            }
        }
    }

    suspend fun buscarPorId(clienteId: Int): Cliente?{
        // return withContext(Dispatchers.IO) {
        return repository.buscarPorId(clienteId.toString())
        //}
    }

    fun gravar(cliente: Cliente) {
        viewModelScope.launch {
            repository.gravar(cliente)
            carregarClientes()
        }
    }

    fun excluir(cliente: Cliente) {
        viewModelScope.launch {
            repository.excluir(cliente)
            carregarClientes()
        }
    }

    // Função de login unificada
    fun verificarLogin(email: String, senha: String) {
        viewModelScope.launch {
            val result = repository.verificarLogin(email, senha)
            if (result) {
                _loginStatus.value = true
                _errorMessage.value = ""
            } else {
                _loginStatus.value = false
                _errorMessage.value = "Credenciais inválidas"
            }
        }
    }

    // Limpar status de login após logout ou erro
    fun limparLoginStatus() {
        _loginStatus.value = null
    }
}
