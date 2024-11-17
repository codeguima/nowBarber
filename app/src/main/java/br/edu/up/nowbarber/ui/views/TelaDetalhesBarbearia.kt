package br.edu.up.nowbarber.ui.views

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
import androidx.navigation.NavController
import br.edu.up.nowbarber.data.models.Agendamento
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.components.SelectDateAndTime
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.components.ServicoItem
import br.edu.up.nowbarber.ui.viewmodels.AgendamentoViewModel
import br.edu.up.nowbarber.ui.viewmodels.ServicoViewModel
import java.util.Calendar
import java.util.UUID

@Composable
fun TelaDetalhesBarbearia(
    state: DrawerState,
    navController: NavController,
    servicoViewModel: ServicoViewModel,
    agendamentoViewModel: AgendamentoViewModel
) {
    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p ->
            ConteudoTelaDetalhesBarbearia(
                Modifier.padding(p),
                navController,
                servicoViewModel,
                agendamentoViewModel
            )
        }
    )
}

@Composable
fun ConteudoTelaDetalhesBarbearia(
    modifier: Modifier,
    navController: NavController,
    servicoViewModel: ServicoViewModel,
    agendamentoViewModel: AgendamentoViewModel
) {
    val servicos by servicoViewModel.servicos.collectAsState()
    val context = LocalContext.current
    var isFavorito by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Exibe a imagem da barbearia
        servicos.firstOrNull()?.imageResId?.let {
            Image(
                painter = painterResource(id = it.toInt()),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
            )
        }

        // Botão de Favorito
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
                    // Exibe o seletor de data e hora
                    SelectDateAndTime { date, time ->
                        val agendamento = Agendamento(
                            id = UUID.randomUUID().toString(),
                            clienteUid = "user_id", // Ajuste conforme necessário
                            barbeariaId = "barbearia_id", // Ajuste conforme necessário
                            servicoId = servico.id,
                            dataHora = "${date.get(Calendar.DAY_OF_MONTH)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.YEAR)} ${time.get(Calendar.HOUR_OF_DAY)}:00",
                            status = "pendente"
                        )
                        agendamentoViewModel.gravar(agendamento)

                        Toast.makeText(
                            context,
                            "Agendado para: ${date.time} às ${time.get(Calendar.HOUR_OF_DAY)}:00",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
