package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_servico")
data class Servico(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val price: String,  // preço formatado como String
    val imageResId: Int? = null // opcional, caso haja uma imagem associada ao serviço
)