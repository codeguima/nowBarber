package br.edu.up.nowbarber.ui.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.data.models.Barbearia
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.views.Agendamento
import br.edu.up.nowbarber.ui.views.AgendamentoItem
import java.util.*


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDetalhesBarbearia(barbeiro: Barbearia, navController: NavController) {
    var isFavorito by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var selectedTime by remember { mutableStateOf<Calendar?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Estado para agendamentos
    val agendamentos = remember { mutableStateListOf<Agendamento>() }

    Scaffold(
        topBar = { TopAppBar(title = { Text(barbeiro.name) }) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = barbeiro.imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                )

                IconButton(
                    onClick = { isFavorito = !isFavorito },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = if (isFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorito) Color.Red else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Serviços da barbearia
                val servicos = listOf(
                    Servico(1,"Corte de cabelo", "R$ 50,00"),
                    Servico(2,"Barba", "R$ 30,00"),
                    Servico(3,"Corte + Barba", "R$ 70,00")
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(servicos) { servico ->
                        ServicoItem(servico) {
                            // Chama o DatePicker para agendar o serviço
                            selectedDate = null
                            selectedTime = null
                            showDatePicker = true
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
                if (showTimePicker) {
                    TimePickerDialog(
                        onDismissRequest = { showTimePicker = false },
                        onTimeSelected = { time ->
                            selectedTime = time
                            // Finaliza o agendamento
                            val agendamento = Agendamento(
                                name = barbeiro.name,
                                serviceValue = servicos[0].price, // aqui, use o valor correto do serviço
                                date = selectedDate!!.time,
                                time = "${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}",
                                imageResId = barbeiro.imageResId
                            )
                            agendamentos.add(agendamento)
                            showTimePicker = false // Fecha o TimePicker após a seleção
                            Toast.makeText(context, "Agendado para: ${selectedDate!!.time} às ${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // Exibir lista de agendamentos
                if (agendamentos.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Agendamentos:", style = MaterialTheme.typography.headlineSmall)
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(agendamentos) { agendamento ->
                            AgendamentoItem(agendamento = agendamento, onDelete = { agendamentos.remove(it) })
                        }
                    }
                }
            }
        }
    )
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
    DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
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

// @Preview para a tela de detalhes da barbearia
@Preview(showBackground = true)
@Composable
fun PreviewTelaDetalhesBarbearia() {
    // Simular um barbeiro para pré-visualização
    val barberPreview = Barbearia(
        name = "Barbearia Teste",
        location = "Rua Exemplo, 123",
        imageResId = R.drawable.ic_launcher_foreground // Substitua pelo seu recurso de imagem
    )

    // Você pode usar um NavController fake ou uma função de navegação vazia para fins de pré-visualização
    TelaDetalhesBarbearia(barbeiro = barberPreview, navController = rememberNavController())
}
