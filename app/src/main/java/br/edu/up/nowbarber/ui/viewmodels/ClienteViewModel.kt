package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.repositories.IRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ClienteViewModel(
    private val repository: IRepository<Cliente>
) : ViewModel() {

    private val _cliente = MutableStateFlow<List<Cliente>>(emptyList())
    val lista: StateFlow<List<Cliente>> get() = _cliente

    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: LiveData<Boolean?> get() = _loginStatus.asLiveData()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: LiveData<String> get() = _errorMessage.asLiveData()

    // Instância do FirebaseAuth para autenticação
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        carregarClientes()
    }

    private fun carregarClientes() {
        viewModelScope.launch {
            repository.listar().collect { lista ->
                _cliente.value = lista
            }
        }
    }

    suspend fun buscarPorId(clienteId: String?): Cliente? {
        return repository.buscarPorId(clienteId.toString())
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



    fun cadastrarUsuario(email: String, senha: String) {
        // Verificar se a senha tem pelo menos 6 caracteres
        if (senha.length < 6) {
            _errorMessage.value = "A senha deve ter pelo menos 6 caracteres!"
            return
        }

        // Tentar criar o usuário
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginStatus.value = true
                    _errorMessage.value = "" // Limpa a mensagem de erro se o cadastro for bem-sucedido
                } else {
                    // Tratar o erro específico
                    when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            _errorMessage.value = "Credenciais inválidas. Verifique o email e a senha."
                        }
                        is FirebaseAuthUserCollisionException -> {
                            _errorMessage.value = "Esse e-mail já está cadastrado."
                        }
                        else -> {
                            _errorMessage.value = "Erro desconhecido. Tente novamente."
                        }
                    }
                }
            }
    }


    fun realizarLogin(email: String, senha: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, senha).await()
                val user = result.user
                if (user != null) {
                    _loginStatus.value = true
                    _errorMessage.value = ""
                    onLoginSuccess()  // Chamando a função de sucesso e redirecionando para a tela inicial
                } else {
                    _loginStatus.value = false
                    _errorMessage.value = "Credenciais inválidas"
                }
            } catch (e: Exception) {
                _loginStatus.value = false
                _errorMessage.value = "Erro ao tentar fazer login. Tente novamente."
            }
        }
    }


    // Função de logout
    fun logout() {
        auth.signOut()
        _loginStatus.value = null
    }

    // Limpar status de login após logout ou erro
    fun limparLoginStatus() {
        _loginStatus.value = null
    }
}
