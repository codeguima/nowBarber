package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Cliente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ClienteRemoteRepository : IRepository<Cliente> {

    private val firestore = FirebaseFirestore.getInstance()
    private val clienteCollection = firestore.collection("clientes")

    override fun listar(): Flow<List<Cliente>> = callbackFlow {
        val listener = clienteCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val clientes = snapshot.documents.mapNotNull {
                    it.toObject(Cliente::class.java)
                }
                trySend(clientes).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun buscarPorId(id: String): Cliente? {
        val doc = clienteCollection.document(id).get().await()
        return doc.toObject(Cliente::class.java)
    }

    override suspend fun gravar(item: Cliente) {
        val uid = item.uid.ifEmpty { FirebaseAuth.getInstance().currentUser?.uid ?: return }
        clienteCollection.document(uid).set(item.copy(uid = uid)).await()
    }

    override suspend fun excluir(item: Cliente) {
        clienteCollection.document(item.uid).delete().await()
    }
}
