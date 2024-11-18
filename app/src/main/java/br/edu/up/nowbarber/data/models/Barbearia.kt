package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.edu.up.nowbarber.data.db.Converters
import com.google.firebase.firestore.GeoPoint

@Entity(tableName = "tab_barbearia")
@TypeConverters(Converters::class)
data class Barbearia(
    @PrimaryKey
    val id: String = "",
    val nome: String = "",
    val endereco: String = "",
    val cidade: String = "",  // Campo adicionado
    val telefone: String = "",
    var coordenadas: GeoPoint? = null,
    val image: String = "",
    val isFavorite: Boolean = false,
    val servicos: Map<String, Double>
){
    constructor() : this(
        id = "",
        nome = "",
        endereco = "",
        cidade = "",
        telefone = "",
        coordenadas = null,
        image = "",
        isFavorite = false,
        servicos = emptyMap()
    )

    // Função para substituir chaves com caracteres especiais
    fun ajustarServicos(): Map<String, Double> {
        return servicos.mapKeys { (key, _) ->
            key.replace(" + ", "_mais_")
                .replace(" ", "_") // Substituir espaços por underscores
        }
    }
}
