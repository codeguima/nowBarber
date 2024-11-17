package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Cliente
import com.google.firebase.firestore.FirebaseFirestore

class ClienteRepository(
    private val remoteRepo: ClienteRemoteRepository,
    private val localRepo: ClienteLocalRepository
) : IRepository<Cliente> {


    private val firestore = FirebaseFirestore.getInstance()
    private val clienteCollection = firestore.collection("clientes")

    override suspend fun gravar(item: Cliente) {

        localRepo.gravar(item)
        remoteRepo.gravar(item)
    }


    override fun listar() = remoteRepo.listar()


    override suspend fun buscarPorId(id: String): Cliente? {
        var cliente = localRepo.buscarPorId(id)
        if (cliente == null) {
            cliente = remoteRepo.buscarPorId(id)
            cliente?.let { localRepo.gravar(it) }  // Atualiza local caso encontrado no remoto
        }
        return cliente
    }

    

    override suspend fun excluir(item: Cliente) {
        localRepo.excluir(item)
        remoteRepo.excluir(item)
    }
    
}
