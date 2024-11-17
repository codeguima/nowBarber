package br.edu.up.nowbarber.data.repositories

import kotlinx.coroutines.flow.Flow

interface IRepository<T> {
    fun listar(): Flow<List<T>>
    suspend fun buscarPorId(id: String): T?
    suspend fun gravar(item: T)
    suspend fun excluir(item: T)

}
