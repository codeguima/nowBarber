package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.models.ServicoDao
import kotlinx.coroutines.flow.Flow

class ServicoLocalRepository(
    private val dao: ServicoDao
) : IRepository<Servico> {

    override fun listar(): Flow<List<Servico>> = dao.listar()
    override suspend fun buscarPorId(id: String?): Servico? = dao.buscarPorId(id)
    override suspend fun gravar(item: Servico) = dao.gravar(item)
    override suspend fun excluir(item: Servico) = dao.excluir(item)

    override suspend fun verificarLogin(email: String, senha: String): Boolean {
        TODO("Not yet implemented")
    }
}
