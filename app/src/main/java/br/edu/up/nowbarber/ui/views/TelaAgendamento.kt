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

import java.text.SimpleDateFormat
import java.util.*

data class Agendamento(val name: String, val serviceValue: String, val date: Date, val time: String, val imageResId: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAgendamento(state: DrawerState, bottonNavBar: @Composable () -> Unit) {
    val agendamentos = remember {
        mutableStateListOf(
            Agendamento("Barbearia do Zé", 50.00.toString(), Date(), "14:00", R.drawable.logo2),
            Agendamento("Corte Rápido", 40.00.toString(), Date(), "15:00", R.drawable.logo2),
            Agendamento("Barba e Cabelo", 70.00.toString(), Date(), "16:00", R.drawable.logo2)
        )
    }

    Scaffold(
        content = { paddingValues ->
            ConteudoTelaAgendamento(
                modifier = Modifier.padding(paddingValues),
                agendamentos = agendamentos,
                onDelete = { agendamento -> agendamentos.remove(agendamento) }
            )
        },
        bottomBar = { bottonNavBar() }
    )
}

@Composable
fun ConteudoTelaAgendamento(
    modifier: Modifier,
    agendamentos: List<Agendamento>,
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
            Text(text = "Nenhum agendamento encontrado.", style = MaterialTheme.typography.bodyLarge)
        } else {
            agendamentos.forEach { agendamento ->
                AgendamentoItem(agendamento = agendamento, onDelete = onDelete)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AgendamentoItem(agendamento: Agendamento, onDelete: (Agendamento) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(agendamento.imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = agendamento.name,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "R$ ${agendamento.serviceValue}",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium
            )

            val sdf = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
            Text(
                text = "${sdf.format(agendamento.date)} às ${agendamento.time}",
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodySmall
            )
        }

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
