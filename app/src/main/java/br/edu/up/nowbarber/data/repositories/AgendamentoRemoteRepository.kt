package br.edu.up.nowbarber.data.repositories

import br.edu.up.nowbarber.data.models.Agendamento
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AgendamentoRemoteRepository : IRepository<Agendamento> {

    private val firestore = FirebaseFirestore.getInstance()
    private val agendamentoCollection = firestore.collection("agendamentos")

    override fun listar(): Flow<List<Agendamento>> = callbackFlow {
        val listener = agendamentoCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            snapshot?.let {
                val agendamentos = it.documents.mapNotNull { doc -> doc.toObject(Agendamento::class.java) }
                trySend(agendamentos).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun buscarPorId(id: String): Agendamento? {
        val doc = agendamentoCollection.document(id).get().await()
        return doc.toObject(Agendamento::class.java)
    }

    override suspend fun gravar(item: Agendamento) {
        agendamentoCollection.document(item.id).set(item).await()
    }

    override suspend fun excluir(item: Agendamento) {
        agendamentoCollection.document(item.id).delete().await()
    }
}
