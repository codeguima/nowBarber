package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Agendamento
import kotlinx.coroutines.flow.Flow

class AgendamentoRepository(
    private val localRepo: AgendamentoLocalRepository,
    private val remoteRepo: AgendamentoRemoteRepository
) : IRepository<Agendamento> {

    override fun listar(): Flow<List<Agendamento>> = remoteRepo.listar()

    override suspend fun buscarPorId(id: String): Agendamento? {
        var agendamento = localRepo.buscarPorId(id)
        if (agendamento == null) {
            agendamento = remoteRepo.buscarPorId(id)
            agendamento?.let { localRepo.gravar(it) }
        }
        return agendamento
    }

    override suspend fun gravar(item: Agendamento) {
        localRepo.gravar(item)
        remoteRepo.gravar(item)
    }

    override suspend fun excluir(item: Agendamento) {
        localRepo.excluir(item)
        remoteRepo.excluir(item)
    }
}
