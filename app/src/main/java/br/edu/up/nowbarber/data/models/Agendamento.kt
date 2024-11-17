package br.edu.up.nowbarber.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_agendamento")
data class Agendamento(
    @PrimaryKey
    var id: String = "", // Identificador único
    var clienteUid: String = "", // Relacionamento com Cliente
    var barbeariaId: String = "", // Relacionamento com Barbearia
    var servicoId: String = "", // Relacionamento com Serviço
    var dataHora: String = "", // Data e hora do agendamento
    var status: String = "pendente" // Status do agendamento
)
