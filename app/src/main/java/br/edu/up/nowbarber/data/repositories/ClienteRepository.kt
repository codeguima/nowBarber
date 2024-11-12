package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Cliente
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.toList
import org.mindrot.jbcrypt.BCrypt

class ClienteRepository(
    private val remoteRepo: ClienteRemoteRepository,
    private val localRepo: ClienteLocalRepository
) : IRepository<Cliente> {

    // Função para gerar o hash da senha
    private fun hashSenha(senha: String): String {
        return BCrypt.hashpw(senha, BCrypt.gensalt())
    }

    // Função para verificar a senha
    private fun verificarSenha(senha: String, hash: String): Boolean {
        return BCrypt.checkpw(senha, hash)
    }

    override fun listar(): Flow<List<Cliente>> = callbackFlow {
        remoteRepo.listar().collect { clientes ->
            clientes.forEach { cliente ->
                localRepo.gravar(cliente)  // Atualiza banco local com dados do remoto
            }
            trySend(clientes)
        }

        localRepo.listar().collect { localData ->
            if (localData.isNotEmpty()) {
                trySend(localData)
            }
        }
    }

    override suspend fun buscarPorId(id: Int): Cliente? {
        var cliente = localRepo.buscarPorId(id)
        if (cliente == null) {
            cliente = remoteRepo.buscarPorId(id)
            cliente?.let { localRepo.gravar(it) }  // Atualiza local caso encontrado no remoto
        }
        return cliente
    }

    override suspend fun gravar(item: Cliente) {
        // Cria um novo objeto Cliente com a senha em hash
        val clienteComHash = item.copy(senha = hashSenha(item.senha))
        localRepo.gravar(clienteComHash)
        remoteRepo.gravar(clienteComHash)
    }

    override suspend fun excluir(item: Cliente) {
        localRepo.excluir(item)
        remoteRepo.excluir(item)
    }

    // Função para verificar login (usada no ViewModel)
    suspend fun verificarLogin(email: String, senha: String): Boolean {
        // Coleta todos os clientes em uma lista
        val clientes = localRepo.listar().toList().flatten() // Transforma o Flow em uma lista
        val cliente = clientes.find { it.email == email }

        return cliente?.let { verificarSenha(senha, it.senha) } ?: false
    }
}


