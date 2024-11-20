package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Servico
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class ServicoRepository(
    private val servicoRemoteRepository: ServicoRemoteRepository,
    private val servicoLocalRepository: ServicoLocalRepository
) : IRepository<Servico> {

    override fun listar(): Flow<List<Servico>> = flow {
        // Coletar dados locais primeiro
        val servicosLocais = servicoLocalRepository.listar().first()

        // Coletar dados remotos
        val servicosRemotos = servicoRemoteRepository.listar().first()

        // Comparar listas e remover serviços locais que não existem mais no remoto
        val idsRemotos = servicosRemotos.map { it.id }.toSet()
        val idsLocais = servicosLocais.map { it.id }.toSet()

        // Serviços locais que não estão mais no remoto (deletar do local)
        val idsParaExcluir = idsLocais - idsRemotos
        idsParaExcluir.forEach { id ->
            servicoLocalRepository.excluir(Servico(id = id))  // Excluir do banco local
        }

        // Sincronizar com o banco local
        servicosRemotos.forEach { servico ->
            // Grava os novos serviços remotos no banco local
            servicoLocalRepository.gravar(servico)
        }

        // Emitir a lista final de serviços, após a sincronização
        emit(servicosRemotos)
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
