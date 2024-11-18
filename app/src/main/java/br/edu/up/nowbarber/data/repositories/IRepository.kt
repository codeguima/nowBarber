package br.edu.up.nowbarber.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IRepository<T> {

    fun listar(): Flow<List<T>>
    suspend fun buscarPorId(id: String): T?
    suspend fun gravar(item: T)
    suspend fun excluir(item: T)

    suspend fun buscarPorCidade(cidade: String): List<T> {
        return emptyList()
    }

    suspend fun atualizarEmail(id: String, novoEmail: String): Flow<Result<Unit>> = flow {
        emit(Result.failure<Unit>(UnsupportedOperationException("Função não suportada")))
    }

}
