package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.up.nowbarber.data.models.Agendamento
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.viewmodels.AgendamentoViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TelaMeusAgendamentos(
    state: DrawerState,
    agendamentoViewModel: AgendamentoViewModel = viewModel()
) {
    // Observa a lista de agendamentos e serviços
    val agendamentos by agendamentoViewModel.agendamentos.collectAsState()
    val servicosMap by agendamentoViewModel.servicosMap.collectAsState()

    Scaffold(
        topBar = { TopAppBar(state) },
        content = { paddingValues ->
            ConteudoTelaMeusAgendamentos(
                modifier = Modifier.padding(paddingValues),
                agendamentos = agendamentos,
                servicosMap = servicosMap,
                onDelete = { agendamento ->
                    agendamentoViewModel.excluir(agendamento) // Exclui o agendamento via ViewModel
                }
            )
        }
    )
}

@Composable
fun ConteudoTelaMeusAgendamentos(
    modifier: Modifier = Modifier,
    agendamentos: List<Agendamento>,
    servicosMap: Map<String, Servico>,
    onDelete: (Agendamento) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (agendamentos.isEmpty()) {
            Text(
                text = "Nenhum agendamento encontrado.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            agendamentos.forEach { agendamento ->
                val servico = servicosMap[agendamento.servicoId] // Obtém o serviço relacionado ao agendamento
                if (servico != null) {
                    AgendamentoItem(
                        agendamento = agendamento,
                        servico = servico,
                        onDelete = onDelete
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AgendamentoItem(
    agendamento: Agendamento,
    servico: Servico,
    onDelete: (Agendamento) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagem do serviço
        servico.imageResId.takeIf { it.isNotEmpty() }?.let { imageResId ->
            Image(
                painter = painterResource(imageResId.toInt()),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Informações do agendamento e serviço
        Column(modifier = Modifier.weight(1f)) {
            // Nome do serviço
            Text(
                text = servico.nome,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Preço do serviço
            Text(
                text = "R$ ${servico.preco}",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )

            // Nome da barbearia
            servico.barbeariaNome?.let {
                Text(
                    text = "Barbearia: $it",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Formatação da data e hora
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(Date(agendamento.dataHora))
            val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(Date(agendamento.dataHora))

            Text(
                text = "Data: $formattedDate | Hora: $formattedTime",
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Ícone de exclusão
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Agendamento",
            modifier = Modifier
                .size(32.dp)
                .clickable { onDelete(agendamento) }
                .padding(8.dp),
            tint = Color.Red
        )
    }
}
