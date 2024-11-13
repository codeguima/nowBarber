package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Barbearia
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class BarbeariaRemoteRepository : IRepository<Barbearia> {

    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("barbearias")

    override fun listar(): Flow<List<Barbearia>> = callbackFlow {
        val listener = collection.addSnapshotListener { dados, erros ->
            if (erros != null) {
                close(erros)
                return@addSnapshotListener
            }
            val barbearias = dados?.documents?.mapNotNull { it.toObject(Barbearia::class.java) }
            trySend(barbearias ?: emptyList()).isSuccess
        }
        awaitClose { listener.remove() }
    }

    override suspend fun buscarPorId(id: String?): Barbearia? {
        val doc = collection.document(id.toString()).get().await()
        return doc.toObject(Barbearia::class.java)
    }

    override suspend fun gravar(item: Barbearia) {
        val docRef = collection.document(item.id.toString())
        docRef.set(item).await()
    }

    override suspend fun excluir(item: Barbearia) {
        collection.document(item.id.toString()).delete().await()
    }
}
