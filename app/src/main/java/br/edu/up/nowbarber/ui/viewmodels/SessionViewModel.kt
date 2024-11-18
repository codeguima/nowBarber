package br.edu.up.nowbarber.ui.viewmodels

import androidx.datastore.preferences.core.stringPreferencesKey
import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first


val Application.dataStore by preferencesDataStore(name = "user_data")

class SessionViewModel(application: Application) : AndroidViewModel(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val dataStore = application.dataStore  // Usando o dataStore da Application

    // Chave para armazenar o usuarioId
    private val usuarioIdKey = stringPreferencesKey("usuario_id")

    // Estados do ViewModel
    private val _usuarioId = MutableStateFlow<String?>(null)
    val usuarioId: StateFlow<String?> get() = _usuarioId

    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: LiveData<Boolean?> get() = _loginStatus.asLiveData()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: LiveData<String> get() = _errorMessage.asLiveData()

    init {
        // Carregar o usuarioId do DataStore ao iniciar o ViewModel
        viewModelScope.launch {
            carregarUsuarioId()
        }
    }

    // Função para carregar o usuarioId do DataStore
    suspend fun carregarUsuarioId() {
        val usuarioIdValue = dataStore.data.first()[usuarioIdKey]
        _usuarioId.value = usuarioIdValue
    }

    // Atualizar senha com segurança
    fun atualizarSenha(senhaAtual: String, novaSenha: String) {
        val user = auth.currentUser
        user?.let {
            val credential = EmailAuthProvider.getCredential(user.email ?: "", senhaAtual)
            user.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updatePassword(novaSenha).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            _errorMessage.value = "Senha alterada com sucesso!"
                        } else {
                            _errorMessage.value = "Erro ao alterar a senha. Tente novamente."
                        }
                    }
                } else {
                    _errorMessage.value = "Senha atual incorreta!"
                }
            }
        } ?: run {
            _errorMessage.value = "Usuário não autenticado!"
        }
    }

    // Cadastro de usuário com tratamento de erros
    fun cadastrarUsuario(email: String, senha: String) {
        if (senha.length < 6) {
            _errorMessage.value = "A senha deve ter pelo menos 6 caracteres!"
            return
        }

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _loginStatus.value = true
                _errorMessage.value = ""
            } else {
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

    // Função de login com Firebase e DataStore
    fun realizarLogin(email: String, senha: String, onLoginSuccess: () -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
                    // Atualiza o estado do usuarioId no SessionViewModel
                    _usuarioId.value = usuarioId
                    // Salvar usuarioId no DataStore
                    viewModelScope.launch {
                        salvarUsuarioId(usuarioId)
                    }
                    onLoginSuccess()  // Chama a função de sucesso após login
                } else {
                    _errorMessage.value = "Erro ao fazer login"
                }
            }
    }



    fun atualizarEmail(emailAtual: String, novaSenha: String, novoEmail: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null && user.email != null) {
            val credential = EmailAuthProvider.getCredential(emailAtual, novaSenha)

            // Reautenticar antes de atualizar
            user.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updateEmail(novoEmail).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            onSuccess()
                        } else {
                            onError("Erro ao atualizar e-mail.")
                        }
                    }
                } else {
                    onError("Reautenticação falhou.")
                }
            }
        } else {
            onError("Usuário não autenticado.")
        }
    }


    // Função para salvar o usuarioId no DataStore
    private suspend fun salvarUsuarioId(usuarioId: String?) {
        dataStore.edit { preferences ->
            preferences[usuarioIdKey] = usuarioId ?: ""
        }
    }

    // Logout do usuário
    fun logout() {
        auth.signOut()
        _usuarioId.value = null
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences.remove(usuarioIdKey)  // Remover usuarioId do DataStore
            }
        }
    }

    // Limpar status de login
    fun limparLoginStatus() {
        _loginStatus.value = null
    }

    // Atualizar ID do usuário
    fun atualizarUsuarioId(id: String?) {
        _usuarioId.value = id
    }

    // Definir uma mensagem de erro
    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }
}
