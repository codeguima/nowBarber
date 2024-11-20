package br.edu.up.nowbarber.ui.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.data.models.Agendamento
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.components.ServicoItem
import br.edu.up.nowbarber.ui.viewmodels.AgendamentoViewModel
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel

import java.util.Calendar
import java.util.UUID

@Composable
fun TelaDetalhesBarbearia(
    state : DrawerState,
    navController: NavController,
    servicoViewModel: ServicoViewModel,
    agendamentoViewModel: AgendamentoViewModel,
    clienteViewModel: ClienteViewModel,
    sessionViewModel: SessionViewModel,
    barbeariaId: String?
) {
    Scaffold(
        topBar = { TopAppBar(state) },
        content = { padding ->
            ConteudoTelaDetalhesBarbearia(
                modifier = Modifier.padding(padding),
                navController = navController,
                servicoViewModel = servicoViewModel,
                agendamentoViewModel = agendamentoViewModel,
                clienteViewModel = clienteViewModel,
                sessionViewModel = sessionViewModel,
                barbeariaId = barbeariaId
            )
        }
    )
}

@Composable
fun ConteudoTelaDetalhesBarbearia(
    modifier: Modifier = Modifier,
    navController: NavController,
    servicoViewModel: ServicoViewModel,
    agendamentoViewModel: AgendamentoViewModel,
    clienteViewModel: ClienteViewModel,
    sessionViewModel: SessionViewModel,
    barbeariaId: String?
) {

    Log.d("DEBUG", "Barbearia ID: $barbeariaId")

    if (barbeariaId.isNullOrBlank()) {
        // Exibir mensagem de erro caso barbeariaId seja inválido
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Erro: Barbearia não encontrada.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = { navController.navigateUp() }) {
                Text("Voltar")
            }

        }
        return
    }

    val servicos by servicoViewModel.servicos.collectAsState()
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedServico by remember { mutableStateOf<Servico?>(null) }

    val servicosFiltrados = servicos.filter { it.barbeariaId == barbeariaId }

    var cliente by remember { mutableStateOf<Cliente?>(null) }
    val usuarioId by sessionViewModel.usuarioId.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    // Carregar dados do cliente
    LaunchedEffect(usuarioId) {
        isLoading = true
        cliente = clienteViewModel.buscarPorId(usuarioId.toString())
        isLoading = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagem de destaque
        Image(
            painter = painterResource(R.drawable.semfoto),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        )

        // Título
        Text(
            text = "Serviços Disponíveis",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Indicador de carregamento
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            // Exibir lista de serviços ou mensagem de erro
            if (servicosFiltrados.isEmpty()) {
                Text(
                    text = "Nenhum serviço disponível para esta barbearia.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)

                )
                Button(onClick = { navController.navigateUp() }) {
                    Text("Voltar")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(servicosFiltrados) { servico ->
                        ServicoItem(servico) {
                            selectedServico = servico
                            showDatePicker = true
                        }
                    }
                }
            }
        }

        // Seleção de data e horário
        if (showDatePicker && selectedServico != null) {
            SelectDateAndTime { date, time ->
                val agendamento = cliente?.let {
                    Agendamento(
                        id = UUID.randomUUID().toString(),
                        clienteUid = it.uid,
                        barbeariaId = barbeariaId,
                        servicoId = selectedServico!!.id,
                        dataHora = "${date.get(Calendar.DAY_OF_MONTH)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.YEAR)} ${time.get(Calendar.HOUR_OF_DAY)}:00",
                        status = "pendente"
                    )
                }
                if (agendamento != null) {
                    agendamentoViewModel.gravar(agendamento)
                }

                Toast.makeText(
                    context,
                    "Agendado para: ${date.time} às ${time.get(Calendar.HOUR_OF_DAY)}:00",
                    Toast.LENGTH_SHORT
                ).show()

                navController.navigate("pesquisa") {
                    popUpTo("detalhesBarbearia/${barbeariaId}") { inclusive = true }
                }


                showDatePicker = false
            }
        }
    }
}



@Composable
fun SelectDateAndTime(
    onDateTimeSelected: (date: Calendar, time: Calendar) -> Unit
) {
    val context = LocalContext.current

    // Exibir DatePickerDialog
    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Exibir TimePicker com horários fixos
                showTimeSelectionDialog(context) { selectedHour ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
                    selectedTime.set(Calendar.MINUTE, 0)

                    onDateTimeSelected(selectedDate, selectedTime)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Botão para iniciar o processo de seleção
    Button(onClick = { showDatePicker() }) {
        Text(text = "Selecionar Data e Hora")
    }
}

fun showTimeSelectionDialog(context: Context, onTimeSelected: (hour: Int) -> Unit) {
    val availableHours = (8..18).toList() // Horários das 8h às 18h
    val hourOptions = availableHours.map { "$it:00" }.toTypedArray()

    android.app.AlertDialog.Builder(context)
        .setTitle("Selecione um horário")
        .setItems(hourOptions) { _, which ->
            onTimeSelected(availableHours[which])
        }
        .create()
        .show()
}
