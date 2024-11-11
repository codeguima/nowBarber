package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_cliente")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val nome: String,
    val telefone: String,
    val nascimento: String,
    val genero: String,
    val email: String,
    val senha: String

)

