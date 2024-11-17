package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_servico")

data class Servico(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val price: String,
    val date: String?,// preço formatado como String
    val imageResId: Int? = null // opcional, caso haja uma imagem associada ao serviço
)

val servicoDBarbearia = listOf(
    Servico(1, "Corte de cabelo", "R$ 50,00", null),
    Servico(2, "Barba", "R$ 30,00", null),
    Servico(3, "Corte + Barba", "R$ 70,00", null)
)