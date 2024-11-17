package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_servico")

data class Servico(
    @PrimaryKey
    var id: String = "",  // Identificador único
    var barbeariaId: String = "",  // Relacionamento com Barbearia
    var nome: String = "",
    var descricao: String = "",
    var preco: Double = 0.0,
    var imageResId : String = ""
)
