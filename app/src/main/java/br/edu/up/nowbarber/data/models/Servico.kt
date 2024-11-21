package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude

@Entity(tableName = "tab_servico")

data class Servico(
    @PrimaryKey
    var id: String = "",
    val nome: String = "",
    val descricao: String = "",
    val preco: Double = 0.0,
    var barbeariaId: String = "",
    var imageResId: String ="",
    val barbeariaNome: String?
)
