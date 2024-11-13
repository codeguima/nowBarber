package br.edu.up.nowbarber.data.models


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

// 2) Data Access Objects (DAOs)
@Dao
interface ClienteDao {
    //CRUD
    @Query("select * from tab_cliente")
    fun listar(): Flow<List<Cliente>>

    @Query("select * from tab_cliente where id = :idx")
    suspend fun buscarPorId(idx: kotlin.String?): Cliente

    @Query("select * from tab_cliente where email = :email")
    suspend fun buscarPorEmail(email: String): Cliente?


    //@Update @Insert
    @Upsert
    suspend fun gravar(cliente: Cliente)

    @Delete
    suspend fun excluir(cliente: Cliente)


}