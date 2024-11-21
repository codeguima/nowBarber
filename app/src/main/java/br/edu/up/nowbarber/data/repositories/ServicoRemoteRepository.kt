package br.edu.up.nowbarber.data.repositories


import br.edu.up.nowbarber.data.models.Servico
import com.google.firebase.firestore.DocumentReference
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
                close(error)
                return@addSnapshotListener
            }

            val servicos = snapshot?.documents?.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null
                val barbeariaId = (data["barbeariaId"] as? DocumentReference)?.id ?: ""
                Servico(
                    id = doc.id,
                    nome = data["nome"] as? String ?: "",
                    descricao = data["descricao"] as? String ?: "",
                    preco = (data["preco"] as? Number)?.toDouble() ?: 0.0,
                    barbeariaId = barbeariaId,
                    imageResId = data["imageResId"] as? String ?: "",
                    barbeariaNome = data["barbeariaNome"] as? String ?: "",
                )
            } ?: emptyList()
            trySend(servicos).isSuccess
        }
        awaitClose { listener.remove() }
    }

    override suspend fun buscarPorId(id: String): Servico? {
        val doc = servicoCollection.document(id).get().await()
        return doc.toObject(Servico::class.java)?.apply { this.id = doc.id }
    }

    override suspend fun gravar(item: Servico) {
        val servicoMap = hashMapOf(
            "nome" to item.nome,
            "descricao" to item.descricao,
            "preco" to item.preco,
            "barbeariaId" to firestore.collection("barbearias").document(item.barbeariaId)
        )
        servicoCollection.document(item.id).set(servicoMap).await()
    }

    override suspend fun excluir(item: Servico) {
        servicoCollection.document(item.id).delete().await()
    }
}
