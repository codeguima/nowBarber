package br.edu.up.nowbarber.data.repositories

import android.util.Log
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
            val listener = servicoCollection.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("ServicoRepo", "Erro ao sincronizar: ${error.message}")
                    close(error)
                    return@addSnapshotListener
                }
                snapshot?.let {
                    val servicos =
                        it.documents.mapNotNull { doc -> doc.toObject(Servico::class.java) }
                    trySend(servicos).isSuccess  // Envia os serviços ao Flow
                }
            }
            awaitClose {
                listener.remove()  // Remove o listener quando o Flow for fechado
            }
        }

        override suspend fun buscarPorId(id: String): Servico? {
            val doc = servicoCollection.document(id).get().await()
            return doc.toObject(Servico::class.java)
        }

        override suspend fun gravar(item: Servico) {
            if (item.id.isEmpty()) {
                item.id = servicoCollection.document().id
            }
            servicoCollection.document(item.id).set(item).await()

        }

        override suspend fun excluir(item: Servico) {
            servicoCollection.document(item.id).delete().await()
        }

}
