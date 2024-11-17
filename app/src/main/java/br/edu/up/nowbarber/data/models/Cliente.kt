package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_cliente")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var nome: String = "",
    var telefone: String = "",
    var email: String = "",
    var dataNascimento: String = "",
    var genero: String = ""
) {
    // Construtor sem parâmetros, necessário para o Firebase e Room
    constructor() : this(0, "", "", "", "", "")
}
