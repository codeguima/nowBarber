package br.edu.up.nowbarber.data.repositories



import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.models.ClienteDao
import kotlinx.coroutines.flow.Flow

class ClienteLocalRepository(
    private val dao: ClienteDao
) : IRepository<Cliente> {

    override fun listar(): Flow<List<Cliente>> = dao.listar()
    override suspend fun buscarPorId(id: String?): Cliente? = dao.buscarPorId(id)
    override suspend fun gravar(item: Cliente) = dao.gravar(item)
    override suspend fun excluir(item: Cliente) = dao.excluir(item)

    override suspend fun verificarLogin(email: String, senha: String): Boolean {
        val cliente = dao.buscarPorEmail(email)
        return cliente?.senha == senha
    }
}
