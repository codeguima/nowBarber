package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_cliente")
data class Cliente(
    @PrimaryKey
    var uid: String = "",  // Usaremos o UID do Firebase Authentication
    var nome: String = "",
    var telefone: String = "",
    var email: String = "",
    var dataNascimento: String = "",
    var genero: String = ""
) {
    constructor() : this("", "", "", "", "", "")
}
