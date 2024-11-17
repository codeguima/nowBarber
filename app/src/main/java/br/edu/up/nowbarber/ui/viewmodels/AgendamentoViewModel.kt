package br.edu.up.nowbarber.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.nowbarber.data.models.Agendamento
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.repositories.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AgendamentoViewModel(
    private val agendamentoRepository: IRepository<Agendamento>,
    private val servicoRepository: IRepository<Servico> // Adicionando a dependência do ServicoRepository
) : ViewModel() {

    private val _agendamentos = MutableStateFlow<List<Agendamento>>(emptyList())
    val agendamentos: StateFlow<List<Agendamento>> get() = _agendamentos

    private val _servicosMap = MutableStateFlow<Map<String, Servico>>(emptyMap()) // Map para armazenar serviços

    val servicosMap: StateFlow<Map<String, Servico>> get() = _servicosMap

    init {
        carregarAgendamentos()
    }

    private fun carregarAgendamentos() {
        viewModelScope.launch {
            agendamentoRepository.listar().collect { agendamentos ->
                _agendamentos.value = agendamentos
                carregarServicos(agendamentos) // Carregar serviços após os agendamentos
            }
        }
    }

    // Método para carregar os serviços relacionados aos agendamentos
    private fun carregarServicos(agendamentos: List<Agendamento>) {
        viewModelScope.launch {
            val servicos = mutableMapOf<String, Servico>()
            agendamentos.forEach { agendamento ->
                val servico = servicoRepository.buscarPorId(agendamento.servicoId)
                if (servico != null) {
                    servicos[agendamento.servicoId] = servico
                }
            }
            _servicosMap.value = servicos
        }
    }

    suspend fun buscarPorId(id: String): Agendamento? = agendamentoRepository.buscarPorId(id)

    fun gravar(agendamento: Agendamento) {
        viewModelScope.launch {
            agendamentoRepository.gravar(agendamento)
            carregarAgendamentos()
        }
    }

    fun excluir(agendamento: Agendamento) {
        viewModelScope.launch {
            agendamentoRepository.excluir(agendamento)
            carregarAgendamentos()
        }
    }
}
