package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.repositories.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServicoViewModel(
    private val repository: IRepository<Servico>
) : ViewModel() {

    // Estado para os serviços
    private val _servicos = MutableStateFlow<List<Servico>>(emptyList())
    val servicos: StateFlow<List<Servico>> get() = _servicos

    // Estado para o carregamento (loading)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        carregarServicos()
    }

    // Carregar os serviços do repositório
    private fun carregarServicos() {
        viewModelScope.launch {
            _isLoading.value = true  // Indica que os dados estão sendo carregados
            try {
                repository.listar().collect {
                    _servicos.value = it  // Atualiza a lista de serviços
                }
            } catch (e: Exception) {
                _servicos.value = emptyList()  // Se houver erro, limpa a lista
            } finally {
                _isLoading.value = false  // Carregamento finalizado
            }
        }
    }

    // Buscar serviço por ID
    suspend fun buscarPorId(id: String): Servico? = repository.buscarPorId(id)

    // Gravar um novo serviço
    fun gravar(servico: Servico) {
        viewModelScope.launch {
            _isLoading.value = true  // Indica que a operação está em andamento
            try {
                repository.gravar(servico)  // Grava o serviço
                // Em vez de recarregar todos os serviços, podemos adicionar diretamente ao estado
                _servicos.value = _servicos.value + servico
            } catch (e: Exception) {
                // Tratar erro de gravação, se necessário
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Excluir um serviço
    fun excluir(servico: Servico) {
        viewModelScope.launch {
            _isLoading.value = true  // Indica que a operação está em andamento
            try {
                repository.excluir(servico)  // Exclui o serviço
                // Remover o serviço da lista localmente, sem precisar recarregar todos
                _servicos.value = _servicos.value.filter { it.id != servico.id }
            } catch (e: Exception) {
                // Tratar erro de exclusão, se necessário
            } finally {
                _isLoading.value = false
            }
        }
    }
}
