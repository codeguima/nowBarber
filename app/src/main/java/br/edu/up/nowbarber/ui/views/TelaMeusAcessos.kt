package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.up.nowbarber.R

import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel

@Composable
fun TelaMeusAcessos(
    state: DrawerState,
    sessionViewModel: SessionViewModel = viewModel()

) {

    val usuarioId = sessionViewModel.usuarioId

    // Resto da lógica da tela
    if (usuarioId != null) {
        // Aqui você pode usar o usuarioId
    } else {
        Text("Usuário não autenticado!")
    }

    val viewModel: ClienteViewModel = viewModel()
    var email by remember { mutableStateOf("") }

    // Usando LaunchedEffect para carregar os dados de acesso (como e-mail)
    LaunchedEffect(usuarioId) {
        usuarioId?.let {
            // Buscar o e-mail do usuário de forma assíncrona
            val usuario = viewModel.buscarPorId(usuarioId)
            usuario?.let {
                email = it.email
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoMeusAcessos(Modifier.padding(p), email) }
    )
}

@Composable
fun ConteudoMeusAcessos(modifier: Modifier, email: String) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Meus Acessos", fontSize = 20.sp)

        // Campo de E-mail
        OutlinedTextField(
            value = email,
            onValueChange = { /* Atualizar e-mail */ },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botões para vinculação de acesso, por exemplo, Google/Facebook
        Button(
            onClick = { /* Implementar ação de vinculação */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.principal),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Vincular Conta do Google")
        }
    }
}
