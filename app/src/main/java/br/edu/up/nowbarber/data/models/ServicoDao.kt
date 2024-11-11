package br.edu.up.nowbarber.data.models


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

// 2) Data Access Objects (DAOs)
@Dao
interface ServicoDao {
    //CRUD
    @Query("select * from tab_servico")
    fun listar(): Flow<List<Servico>>

    @Query("select * from tab_servico where id = :idx")
    suspend fun buscarPorId(idx: Int): Servico

    //@Update @Insert
    @Upsert
    suspend fun gravar(servico: Servico)

    @Delete
    suspend fun excluir(servico: Servico)
}