package br.edu.up.nowbarber.data.repositories


import br.edu.up.nowbarber.data.models.Barbearia
import br.edu.up.nowbarber.data.models.BarbeariaDao

import kotlinx.coroutines.flow.Flow

class BarbeariaLocalRepository(
    private val dao: BarbeariaDao
) : IRepository<Barbearia> {

    override fun listar(): Flow<List<Barbearia>> = dao.listar()

    override suspend fun buscarPorId(id: String): Barbearia? = dao.buscarPorId(id)

    override suspend fun buscarPorCidade(cidade: String): List<Barbearia> = dao.buscarPorCidade(cidade)

    override suspend fun gravar(item: Barbearia) = dao.gravar(item)

    override suspend fun excluir(item: Barbearia) {
        item.id.let { dao.excluir(it) }
    }
}

