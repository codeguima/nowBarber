package br.edu.up.nowbarber.data.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

// 2) Data Access Objects (DAOs)
@Dao
interface BarbeariaDao {

    @Insert
    suspend fun gravar(barbearia: Barbearia)

    @Delete
    suspend fun excluir(barbearia: Barbearia)

    @Query("SELECT * FROM tab_barbearia")
    fun listar(): Flow<List<Barbearia>>

    @Query("SELECT * FROM tab_barbearia WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Barbearia?
}
