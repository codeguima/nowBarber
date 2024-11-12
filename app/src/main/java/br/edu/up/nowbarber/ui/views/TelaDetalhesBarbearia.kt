package br.edu.up.nowbarber.ui.views

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.edu.up.nowbarber.data.models.Barbearia
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.components.ServicoItem
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import java.util.*

// Função principal da tela
@Composable
fun TelaDetalhesBarbearia(
    state: DrawerState,
    navController: NavController,
    servicoViewModel: ServicoViewModel
) {


    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoTelaDetalhesBarbearia(Modifier.padding(p), navController, servicoViewModel) }
    )
}

// Função do conteúdo da tela
@Composable
fun ConteudoTelaDetalhesBarbearia(modifier: Modifier, navController: NavController, servicoViewModel: ServicoViewModel) {

    // Observar a lista de serviços
    val servicos by servicoViewModel.servicos.collectAsState()

    // Estado do agendamento e serviços
    var isFavorito by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Exibe a imagem da barbearia e o botão de favorito
        servicos.firstOrNull()?.imageResId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
            )
        }

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

        // Exibe a lista de serviços
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(servicos) { servico ->
                ServicoItem(servico) {
                    // Chama o DatePicker e TimePicker para agendar o serviço
                    selectDateAndTime(context) { date, time ->
                        val agendamento = Servico(
                            name = servico.name,
                            price = servico.price,
                            date = "${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}",
                            imageResId = servico.imageResId
                        )
                        // Grava o agendamento
                        servicoViewModel.gravarServico(agendamento)
                        Toast.makeText(
                            context,
                            "Agendado para: ${date.time} às ${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

// Função para selecionar data e horário
fun selectDateAndTime(
    context: Context,
    onDateTimeSelected: (Calendar, Calendar) -> Unit
) {
    val calendar = Calendar.getInstance()

    // Exibe o DatePicker
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            // Exibe o TimePicker após escolher a data
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    val selectedTime = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, minute)
                    }
                    // Passa a data e o horário selecionado
                    onDateTimeSelected(selectedDate, selectedTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePicker.show()
}