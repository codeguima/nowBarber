package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Barbearia
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class BarbeariaRemoteRepository : IRepository<Barbearia> {

    private val firestore = FirebaseFirestore.getInstance()
    private val barbeariaCollection = firestore.collection("barbearias")

    override fun listar(): Flow<List<Barbearia>> = callbackFlow {
        val listener = barbeariaCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            snapshot?.let {
                val barbearias = it.documents.mapNotNull { doc ->
                    val barbearia = doc.toObject(Barbearia::class.java)
                    barbearia?.copy(servicos = barbearia.ajustarServicos()) // Ajusta as chaves dos serviços
                }
                trySend(barbearias).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }


    override suspend fun buscarPorCidade(cidade: String): List<Barbearia> {
        val snapshot = barbeariaCollection.whereEqualTo("cidade", cidade).get().await()
        return snapshot.documents.mapNotNull { it.toObject(Barbearia::class.java) }
    }

    override suspend fun buscarPorId(id: String): Barbearia? {
        val doc = barbeariaCollection.document(id).get().await()
        return doc.toObject(Barbearia::class.java)
    }

    override suspend fun gravar(item: Barbearia) {
        barbeariaCollection.document(item.id).set(item).await()
    }

    override suspend fun excluir(item: Barbearia) {
        barbeariaCollection.document(item.id).delete().await()
    }
}
