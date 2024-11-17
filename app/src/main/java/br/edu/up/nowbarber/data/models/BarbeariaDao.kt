package br.edu.up.nowbarber.data.models

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BarbeariaDao {
    @Query("SELECT * FROM tab_barbearia")
    fun listar(): Flow<List<Barbearia>>

    @Query("SELECT * FROM tab_barbearia WHERE id = :id")
    suspend fun buscarPorId(id: String): Barbearia?

    @Query("SELECT * FROM tab_barbearia WHERE cidade = :cidade")
    suspend fun buscarPorCidade(cidade: String): List<Barbearia>

    @Upsert
    suspend fun gravar(barbearia: Barbearia)

    @Query("DELETE FROM tab_barbearia WHERE id = :id")
    suspend fun excluir(id: String)
}
