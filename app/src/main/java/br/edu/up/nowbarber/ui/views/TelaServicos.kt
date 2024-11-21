package br.edu.up.nowbarber.ui.views

import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.edu.up.nowbarber.data.models.Agendamento
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.viewmodels.AgendamentoViewModel
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel
import java.util.*

@Composable
fun TelaServicos(
    state: DrawerState,
    navController: NavController,
    servicoViewModel: ServicoViewModel,
    agendamentoViewModel: AgendamentoViewModel,
    clienteViewModel: ClienteViewModel,
    sessionViewModel: SessionViewModel
) {
    val servicos by servicoViewModel.servicos.collectAsState()
    val isLoading by servicoViewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val usuarioId by sessionViewModel.usuarioId.collectAsState()

    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var selectedTime by remember { mutableStateOf<Calendar?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedServico by remember { mutableStateOf<Servico?>(null) }
    var cliente by remember { mutableStateOf<Cliente?>(null) }

    // Buscar cliente associado ao usuário
    LaunchedEffect(usuarioId) {
        cliente = usuarioId?.let { clienteViewModel.buscarPorId(it) }
    }

    Scaffold(
        topBar = { TopAppBar(state) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                servicos.isEmpty() -> {
                    EmptyState(onNavigateUp = { navController.navigateUp() })
                }
                else -> {
                    ListaServicos(
                        servicos = servicos,
                        onServicoClick = { servico ->
                            selectedServico = servico
                            showDatePicker = true
                        }
                    )
                }
            }
        }

        // Mostrar DatePicker se showDatePicker for verdadeiro
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                onDateSelected = { date ->
                    selectedDate = date
                    showTimePicker = true // Abre o TimePicker após selecionar a data
                }
            )
        }

        // Mostrar TimePicker se showTimePicker for verdadeiro
        if (showTimePicker && selectedDate != null) {
            TimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                onTimeSelected = { time ->
                    selectedTime = time
                    // Finaliza o agendamento
                    realizarAgendamento(
                        cliente = cliente,
                        servico = selectedServico!!,
                        date = selectedDate!!,
                        time = time,
                        context = context,
                        agendamentoViewModel = agendamentoViewModel,
                        onComplete = {
                            navController.navigate("pesquisa") {
                                popUpTo("servicos/${selectedServico!!.id}") { inclusive = true }
                            }
                        }
                    )
                    showTimePicker = false // Fecha o TimePicker após a seleção
                }
            )
        }
    }
}

@Composable
fun EmptyState(onNavigateUp: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nenhum serviço disponível.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateUp) {
            Text("Voltar")
        }
    }
}

@Composable
fun ListaServicos(
    servicos: List<Servico>,
    onServicoClick: (Servico) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(servicos) { servico ->
            ItemServico(
                servico = servico,
                onClick = { onServicoClick(servico) }
            )
        }
    }
}

@Composable
fun ItemServico(
    servico: Servico,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = servico.nome,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = servico.descricao,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "R$ ${servico.preco}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun realizarAgendamento(
    cliente: Cliente?,
    servico: Servico,
    date: Calendar,
    time: Calendar,
    context: Context,
    agendamentoViewModel: AgendamentoViewModel,
    onComplete: () -> Unit
) {
    val agendamento = cliente?.let {
        Agendamento(
            id = UUID.randomUUID().toString(),
            clienteUid = it.uid,
            barbeariaId = servico.barbeariaId,
            servicoId = servico.id,
            dataHora = "${date.get(Calendar.DAY_OF_MONTH)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.YEAR)} ${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}",
            status = "pendente"
        )
    }

    agendamento?.let {
        agendamentoViewModel.gravar(it)
        Toast.makeText(
            context,
            "Agendado para: ${date.time} às ${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}",
            Toast.LENGTH_SHORT
        ).show()
        onComplete()
    }
}

// Componente para mostrar o DatePicker
@Composable
fun DatePickerDialog(onDismissRequest: () -> Unit, onDateSelected: (Calendar) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Lógica para o DatePicker
    android.app.DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
        val selectedDate = Calendar.getInstance().apply {
            set(selectedYear, selectedMonth, selectedDay)
        }
        onDateSelected(selectedDate) // Chama a função de callback com a data selecionada
        onDismissRequest() // Fecha o diálogo após a seleção
    }, year, month, day).show()
}

// Componente para mostrar o TimePicker
@Composable
fun TimePickerDialog(onDismissRequest: () -> Unit, onTimeSelected: (Calendar) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // Lógica para o TimePicker
    TimePickerDialog(context, { _, selectedHour, selectedMinute ->
        val selectedTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, selectedHour)
            set(Calendar.MINUTE, selectedMinute)
        }
        onTimeSelected(selectedTime) // Chama a função de callback com o horário selecionado
        onDismissRequest() // Fecha o diálogo após a seleção
    }, hour, minute, true).show()
}
