package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.up.nowbarber.R

@Entity(tableName = "tab_barbearia")
data class Barbearia(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val location: String,
    val imageResId: Int, // ID da imagem de recurso (drawable)
    var isFavorito: Boolean = false // Adiciona o campo para favoritar
)

// Lista de Barbeiros para pré-exibir
val originalBarbearias = listOf(
    Barbearia(1,"Barbeiro chique no ultimo", "Batel", R.drawable.barbeiro1),
    Barbearia(2,"Deuses da Navalha", "Colombo", R.drawable.logo1),
    Barbearia(3,"Barbearia do Zé", "Piraquara", R.drawable.logo2),
    Barbearia(4,"Corte & Cia", "Bairro Alto", R.drawable.logo4),
    Barbearia(5,"Corte & Barba", "Pinhais", R.drawable.semfoto),
    Barbearia(6,"Ai q gatuu..", "Tatuquara", R.drawable.logo5)
)
