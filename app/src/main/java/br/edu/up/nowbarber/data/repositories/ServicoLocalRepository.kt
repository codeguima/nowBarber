package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.models.ServicoDao
import kotlinx.coroutines.flow.Flow


class ServicoLocalRepository(
    private val servicoDao: ServicoDao
) : IRepository<Servico> {

    override fun listar(): Flow<List<Servico>> = servicoDao.listar()

    override suspend fun buscarPorId(id: String): Servico? = servicoDao.buscarPorId(id)

    override suspend fun excluir(item: Servico) {
        servicoDao.excluir(item.id)
    }

    override suspend fun gravar(servico: Servico) {
        servicoDao.gravar(servico)
    }

    suspend fun excluirPorIdsNaoPresentes(ids: List<String>) {
        servicoDao.excluirIdsNaoPresentes(ids)
    }

    suspend fun gravarLista(servicos: List<Servico>) {
        servicoDao.atualizarServicos(servicos)
    }
}

