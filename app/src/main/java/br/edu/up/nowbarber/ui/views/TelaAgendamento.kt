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
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.components.TopAppBar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TelaAgendamento(state: DrawerState) {
    // Lista de agendamentos inicial com valores de exemplo
    val agendamentos = remember {
        mutableStateListOf(
            Servico(1, "Barbearia do Zé", "50.00", Date().toString(), R.drawable.logo2),
            Servico(2, "Corte Rápido", "40.00", Date().toString(), R.drawable.logo2),
            Servico(3, "Barba e Cabelo", "70.00", Date().toString(), R.drawable.logo2)
        )
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
    agendamento: Servico,
    onDelete: (Servico) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagem do agendamento
        agendamento.imageResId?.let { imageResId ->
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
                text = agendamento.name,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "R$ ${agendamento.price}",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )

            val formattedDate = remember {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(Date(agendamento.date))
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
