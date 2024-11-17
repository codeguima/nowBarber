package br.edu.up.nowbarber.data.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Query("SELECT * FROM tab_cliente")
    fun listar(): Flow<List<Cliente>>

    @Query("SELECT * FROM tab_cliente WHERE uid = :uid")
    suspend fun buscarPorId(uid: String): Cliente?

    @Query("SELECT * FROM tab_cliente WHERE email = :email")
    suspend fun buscarPorEmail(email: String): Cliente?

    @Upsert
    suspend fun gravar(cliente: Cliente)

    @Delete
    suspend fun excluir(cliente: Cliente)
}
