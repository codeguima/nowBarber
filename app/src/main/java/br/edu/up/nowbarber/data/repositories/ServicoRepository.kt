package br.edu.up.nowbarber.data.repositories


import br.edu.up.nowbarber.data.models.Servico
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class ServicoRepository (

        private val remoteRepo: ServicoRemoteRepository,
        private val localRepo: ServicoLocalRepository

    ) : IRepository<Servico> {

        override fun listar(): Flow<List<Servico>> = callbackFlow {
            remoteRepo.listar().collect { servico ->
                servico.forEach { servico ->
                    localRepo.gravar(servico)  // Atualiza banco local com dados do remoto
                }
                trySend(servico)
            }

            localRepo.listar().collect { localData ->
                if (localData.isNotEmpty()) {
                    trySend(localData)
                }
            }
        }

        override suspend fun buscarPorId(id: String?): Servico? {
            var servico = localRepo.buscarPorId(id)
            if (servico == null) {
                servico = remoteRepo.buscarPorId(id)
                servico?.let { localRepo.gravar(it) }  // Atualiza local caso encontrado no remoto
            }
            return servico
        }

    override suspend fun verificarLogin(email: String, senha: String): Boolean {
        TODO("Not yet implemented")
    }


    override suspend fun gravar(servico: Servico) {

            localRepo.gravar(servico)
            remoteRepo.gravar(servico)
        }

        override suspend fun excluir(servico: Servico) {
            localRepo.excluir(servico)
            remoteRepo.excluir(servico)
        }


}
