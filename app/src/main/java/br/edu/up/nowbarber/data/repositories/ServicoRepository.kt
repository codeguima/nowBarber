package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Servico
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServicoRepository(
    private val servicoRemoteRepository: ServicoRemoteRepository,
    private val servicoLocalRepository: ServicoLocalRepository
) : IRepository<Servico> {

    override fun listar(): Flow<List<Servico>> = flow {
        // Coletar dados locais primeiro
        servicoLocalRepository.listar().collect { servicosLocais ->
            emit(servicosLocais) // Emite a lista concreta de serviços locais
        }

        // Sincronizar com o repositório remoto e atualizar o repositório local
        servicoRemoteRepository.listar().collect { servicosRemotos ->
            servicosRemotos.forEach { servico ->
                servicoLocalRepository.gravar(servico)  // Atualiza o banco local
            }
            emit(servicosRemotos) // Emite a lista de serviços remotos
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
