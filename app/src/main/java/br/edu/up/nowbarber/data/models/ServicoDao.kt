package br.edu.up.nowbarber.data.models


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ServicoDao {
    @Query("SELECT * FROM tab_servico")
    fun listar(): Flow<List<Servico>>

    @Query("SELECT * FROM tab_servico WHERE id = :id")
    suspend fun buscarPorId(id: String): Servico?

    @Upsert
    suspend fun gravar(servico: Servico)

    @Query("DELETE FROM tab_servico WHERE id = :id")
    suspend fun excluir(id: String)

    @Query("DELETE FROM tab_servico WHERE id NOT IN (:idsRemotos)")
    suspend fun excluirIdsNaoPresentes(idsRemotos: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun atualizarServicos(servicos: List<Servico>)
}

