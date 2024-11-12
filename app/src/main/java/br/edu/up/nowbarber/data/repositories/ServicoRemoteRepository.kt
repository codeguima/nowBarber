package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Servico
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class ServicoRemoteRepository : IRepository<Servico> {

    private val firestore = FirebaseFirestore.getInstance()
    private val servicoCollection = firestore.collection("servicos")



    override fun listar(): Flow<List<Servico>> = callbackFlow {
        val listener = servicoCollection.addSnapshotListener {
                dados, erros ->
            if (erros != null) {
                close(erros)
                return@addSnapshotListener
            }
            if (dados != null) {
                val result = dados.documents.mapNotNull {
                    it.toObject(Servico::class.java)
                }
                trySend(result).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun buscarPorId(id: Int): Servico? {
        val doc = servicoCollection.document(id.toString()).get().await()
        return doc.toObject(Servico::class.java)
    }

    override suspend fun gravar(item: Servico) {
        val docRef = servicoCollection.document(item.id.toString())
        docRef.set(item).await()
    }

    override suspend fun excluir(item: Servico) {
        servicoCollection.document(item.id.toString()).delete().await()
    }
}
