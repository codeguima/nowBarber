package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_servico")

data class Servico(
    @PrimaryKey
    var id: String = "",
    var nome: String = "",
    var descricao: String = "",
    var preco: Double = 0.0,
    var barbeariaId: String = "",
    var imageResId : String = ""
)
