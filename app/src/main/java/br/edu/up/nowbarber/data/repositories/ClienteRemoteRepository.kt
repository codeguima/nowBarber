package br.edu.up.nowbarber.data.repositories



import br.edu.up.nowbarber.data.models.Cliente
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class ClienteRemoteRepository : IRepository<Cliente> {

    private val firestore = FirebaseFirestore.getInstance()
    private val clienteCollection = firestore.collection("clientes")



        override fun listar(): Flow<List<Cliente>> = callbackFlow {
            val listener = clienteCollection.addSnapshotListener {
                    dados, erros ->
                if (erros != null) {
                    close(erros)
                    return@addSnapshotListener
                }
                if (dados != null) {
                    val clientes = dados.documents.mapNotNull {
                        it.toObject(Cliente::class.java)
                    }
                    trySend(clientes).isSuccess
                }
            }
            awaitClose { listener.remove() }
        }

    override suspend fun buscarPorId(id: Int): Cliente? {
        val doc = clienteCollection.document(id.toString()).get().await()
        return doc.toObject(Cliente::class.java)
    }

    override suspend fun gravar(item: Cliente) {
        val docRef = clienteCollection.document(item.id.toString())
        docRef.set(item).await()
    }

    override suspend fun excluir(item: Cliente) {
        clienteCollection.document(item.id.toString()).delete().await()
    }
}
