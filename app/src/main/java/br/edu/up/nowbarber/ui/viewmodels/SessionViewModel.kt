package br.edu.up.nowbarber.ui.viewmodels

import android.app.Application
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
import kotlinx.coroutines.tasks.await

class SessionViewModel(application: Application) : AndroidViewModel(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Estados do ViewModel
    private val _usuarioId = MutableStateFlow<String?>(null)
    val usuarioId: StateFlow<String?> get() = _usuarioId

    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: LiveData<Boolean?> get() = _loginStatus.asLiveData()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: LiveData<String> get() = _errorMessage.asLiveData()

    // Atualização de senha com segurança
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

    fun realizarLogin(email: String, senha: String, onLoginSuccess: () -> Unit) {
        // Lógica de autenticação (exemplo com Firebase)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
                    // Atualiza o estado do usuarioId no SessionViewModel
                    _usuarioId.value = usuarioId
                    onLoginSuccess()  // Chama a função de sucesso após login
                } else {
                    _errorMessage.value = "Erro ao fazer login"
                }
            }
    }


    // Logout do usuário
    fun logout() {
        auth.signOut()
        _usuarioId.value = null
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
