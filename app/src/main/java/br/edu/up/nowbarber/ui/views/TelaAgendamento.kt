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
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.data.models.Agendamento
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TelaAgendamento(
    state: DrawerState,
    clienteViewModel: ClienteViewModel,
    servicoViewModel: ServicoViewModel,
    sessionViewModel: SessionViewModel = viewModel()

) {

    // Obtendo o ID do usuário logado
    val usuarioId = sessionViewModel.usuarioId

    // Resto da lógica da tela
    if (usuarioId != null) {
        // Aqui você pode usar o usuarioId
    } else {
        Text("Usuário não autenticado!")
    }



    Scaffold(
        topBar = { TopAppBar(state) },
        content = { paddingValues ->
            ConteudoTelaAgendamento(
                modifier = Modifier.padding(paddingValues),
                agendamentos = agendamentos,
                onDelete = { agendamento -> agendamentos.remove(agendamento) }
            )
        }
    )
}

@Composable
fun ConteudoTelaAgendamento(
    modifier: Modifier = Modifier,
    agendamentos: List<Servico>,
    onDelete: (Servico) -> Unit
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
                AgendamentoItem(
                    agendamento = agendamento,
                    onDelete = onDelete
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AgendamentoItem(
    agendamento: Agendamento,
    servico : Servico,
    onDelete: (Agendamento) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagem do agendamento
        servico.imageResId?.let { imageResId ->
            Image(
                painter = painterResource(imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Informações do agendamento
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = servico.nome,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "R$ ${servico.preco}",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )

            val formattedDate = remember {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(Date(agendamento.dataHora))
            }
            Text(
                text = formattedDate,
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
