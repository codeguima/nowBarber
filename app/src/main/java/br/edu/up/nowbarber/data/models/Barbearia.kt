package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.GeoPoint

@Entity(tableName = "tab_barbearia")
data class Barbearia(
    @PrimaryKey
    val id: String = "",
    val nome: String = "",
    val endereco: String = "",
    val cidade: String = "",  // Campo adicionado
    val telefone: String = "",
    var coordenadas: GeoPoint? = null,
    val imageResId: String = "",
    val servicos: List<String> = emptyList()
) {
    // Construtor sem parâmetros, necessário para o Firebase e Room
    constructor() : this("0", "", "", "", "", null)
}
