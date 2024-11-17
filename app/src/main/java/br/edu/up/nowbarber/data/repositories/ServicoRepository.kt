package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Servico
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class ServicoRepository(
    private val servicoRemoteRepository: ServicoRemoteRepository,
    private val servicoLocalRepository: ServicoLocalRepository
) : IRepository<Servico> {

    override fun listar(): Flow<List<Servico>> = flow {
        // Emitir dados locais primeiro
        emitAll(servicoLocalRepository.listar())

        // Sincronizar com remoto e atualizar local
        servicoRemoteRepository.listar().collect { servicos ->
            servicos.forEach { servicoLocalRepository.gravar(it) }
            emit(servicos) // Atualizar a UI com os dados sincronizados
        }
    }

    override suspend fun buscarPorId(id: String): Servico? {
        var servico = servicoLocalRepository.buscarPorId(id)
        if (servico == null) {
            servico = servicoRemoteRepository.buscarPorId(id)
            servico?.let { servicoLocalRepository.gravar(it) }
        }
        return servico
    }

    override suspend fun gravar(item: Servico) {
        servicoLocalRepository.gravar(item)
        servicoRemoteRepository.gravar(item)
    }

    override suspend fun excluir(item: Servico) {
        servicoLocalRepository.excluir(item)
        servicoRemoteRepository.excluir(item)
    }
}
