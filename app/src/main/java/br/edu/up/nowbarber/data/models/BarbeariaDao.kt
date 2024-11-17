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


    @Query("SELECT * FROM tab_barbearia")
    fun listar(): Flow<List<Barbearia>>

    @Query("select * from tab_barbearia where id = :idx")
    suspend fun buscarPorId(idx: String?): Barbearia

    @Insert
    suspend fun gravar(barbearia: Barbearia)

    @Delete
    suspend fun excluir(barbearia: Barbearia)
}
