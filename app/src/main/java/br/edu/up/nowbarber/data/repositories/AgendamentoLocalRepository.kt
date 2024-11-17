package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Agendamento
import br.edu.up.nowbarber.data.models.AgendamentoDao
import kotlinx.coroutines.flow.Flow

class AgendamentoLocalRepository(
    private val dao: AgendamentoDao
) : IRepository<Agendamento> {

    override fun listar(): Flow<List<Agendamento>> = dao.listar()

    override suspend fun buscarPorId(id: String): Agendamento? = dao.buscarPorId(id)


    override suspend fun gravar(item: Agendamento) = dao.gravar(item)

    override suspend fun excluir(item: Agendamento) {
        item.id.let { dao.excluir(it) }
    }
}
