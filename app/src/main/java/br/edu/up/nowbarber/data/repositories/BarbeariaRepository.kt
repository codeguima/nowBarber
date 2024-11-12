package br.edu.up.nowbarber.data.repositories


import br.edu.up.nowbarber.data.models.Barbearia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BarbeariaRepository(
    private val remoteRepo: BarbeariaRemoteRepository,
    private val localRepo: BarbeariaLocalRepository
) : IRepository<Barbearia> {

    override fun listar(): Flow<List<Barbearia>> = callbackFlow {
        // Primeiro tenta pegar os dados do remoto
        remoteRepo.listar().collect { barbearias ->
            // Atualiza localmente quando obtém dados do remoto
            barbearias.forEach { barbearia ->
                localRepo.gravar(barbearia)  // Armazena no banco local
            }
            trySend(barbearias)  // Envia os dados para o fluxo
        }

        // Fallback para dados locais, caso o remoto falhe
        localRepo.listar().collect { localData ->
            if (localData.isNotEmpty()) {
                trySend(localData)  // Caso o remoto falhe, retorna os dados locais
            }
        }
    }

    override suspend fun buscarPorId(id: Int): Barbearia? {
        // Tenta buscar no banco local
        var barbearia = localRepo.buscarPorId(id)
        if (barbearia == null) {
            // Caso não tenha, tenta buscar no remoto
            barbearia = remoteRepo.buscarPorId(id)
            barbearia?.let {
                localRepo.gravar(it)  // Se encontrado remotamente, armazena localmente
            }
        }
        return barbearia
    }

    override suspend fun gravar(item: Barbearia) {
        localRepo.gravar(item)  // Grava no banco local
        remoteRepo.gravar(item)  // Também grava no Firestore
    }

    override suspend fun excluir(item: Barbearia) {
        localRepo.excluir(item)  // Exclui do banco local
        remoteRepo.excluir(item)  // Exclui do Firestore
    }
}
