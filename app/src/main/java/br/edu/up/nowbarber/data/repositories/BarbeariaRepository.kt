package br.edu.up.nowbarber.data.repositories


import br.edu.up.nowbarber.data.models.Barbearia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BarbeariaRepository(
    private val localRepo: BarbeariaLocalRepository,
    private val remoteRepo: BarbeariaRemoteRepository
) : IRepository<Barbearia> {

    override fun listar(): Flow<List<Barbearia>> = remoteRepo.listar()

    override suspend fun buscarPorId(id: String): Barbearia? {
        var barbearia = localRepo.buscarPorId(id)
        if (barbearia == null) {
            barbearia = remoteRepo.buscarPorId(id)
            barbearia?.let { localRepo.gravar(it) }
        }
        return barbearia
    }


    override suspend fun buscarPorCidade(cidade: String): List<Barbearia> {
        val barbeariasLocal = localRepo.buscarPorCidade(cidade)
        return if (barbeariasLocal.isNotEmpty()) {
            barbeariasLocal
        } else {
            val barbeariasRemoto = remoteRepo.buscarPorCidade(cidade)
            barbeariasRemoto.forEach { localRepo.gravar(it) } // Cache local
            barbeariasRemoto
        }
    }

    override suspend fun gravar(item: Barbearia) {
        localRepo.gravar(item)
        remoteRepo.gravar(item)
    }

    override suspend fun excluir(item: Barbearia) {
        localRepo.excluir(item)
        remoteRepo.excluir(item)
    }
}

