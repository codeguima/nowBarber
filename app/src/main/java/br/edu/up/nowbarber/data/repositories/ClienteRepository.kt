package br.edu.up.nowbarber.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import br.edu.up.nowbarber.data.models.Cliente
import kotlinx.coroutines.tasks.await
import org.mindrot.jbcrypt.BCrypt

class ClienteRepository(
    private val remoteRepo: ClienteRemoteRepository,
    private val localRepo: ClienteLocalRepository
) : IRepository<Cliente> {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()  // Instância do FirebaseAuth

    private fun hashSenha(senha: String): String {
        return BCrypt.hashpw(senha, BCrypt.gensalt())
    }

    private fun verificarSenha(senha: String, hash: String): Boolean {
        return BCrypt.checkpw(senha, hash)
    }

    // Função de cadastro usando Firebase Authentication
    suspend fun cadastrar(email: String, senha: String): FirebaseUser? {
        return try {
            val authResult: AuthResult = auth.createUserWithEmailAndPassword(email, senha).await()
            authResult.user  // Retorna o usuário autenticado
        } catch (e: Exception) {
            null  // Retorna null em caso de erro
        }
    }

    // Função de login usando Firebase Authentication
    suspend fun login(email: String, senha: String): FirebaseUser? {
        return try {
            val authResult: AuthResult = auth.signInWithEmailAndPassword(email, senha).await()
            authResult.user  // Retorna o usuário autenticado
        } catch (e: Exception) {
            null  // Retorna null em caso de erro
        }
    }

    // Função de logout
    fun logout() {
        auth.signOut()
    }

    override fun listar() = remoteRepo.listar()

    override suspend fun buscarPorId(id: String?): Cliente? {
        var cliente = localRepo.buscarPorId(id)
        if (cliente == null) {
            cliente = remoteRepo.buscarPorId(id)
            cliente?.let { localRepo.gravar(it) }  // Atualiza local caso encontrado no remoto
        }
        return cliente
    }

    override suspend fun gravar(item: Cliente) {
        val clienteComHash = item.copy(senha = hashSenha(item.senha))
        localRepo.gravar(clienteComHash)
        remoteRepo.gravar(clienteComHash)
    }

    override suspend fun excluir(item: Cliente) {
        localRepo.excluir(item)
        remoteRepo.excluir(item)
    }

    // Verifica login, agora com autenticação Firebase
    override suspend fun verificarLogin(email: String, senha: String): Boolean {
        val usuarioFirebase = login(email, senha)
        return usuarioFirebase != null
    }
}
