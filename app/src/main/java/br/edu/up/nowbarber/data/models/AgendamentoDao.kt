package br.edu.up.nowbarber.data.models

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendamentoDao {
    @Query("SELECT * FROM tab_agendamento")

    fun listar(): Flow<List<Agendamento>>

    @Query("SELECT * FROM tab_agendamento WHERE id = :id")
    suspend fun buscarPorId(id: String): Agendamento?

    @Upsert
    suspend fun gravar(agendamento: Agendamento)

    @Query("DELETE FROM tab_agendamento WHERE id = :id")
    suspend fun excluir(id: String)
}
