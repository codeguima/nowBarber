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
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel

@Composable
fun TelaMeusAcessos(
    state: DrawerState,
    sessionViewModel: SessionViewModel,
    clienteViewModel: ClienteViewModel
) {
    val usuarioId by sessionViewModel.usuarioId.collectAsState()
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    // Busca os dados do usuário quando a tela é carregada ou o ID do usuário muda
    LaunchedEffect(usuarioId) {
        isLoading = true
        errorMessage = ""
        successMessage = ""
        try {
            usuarioId?.let {
                val usuario = clienteViewModel.buscarPorId(it)
                if (usuario != null) {
                    email = usuario.email
                } else {
                    errorMessage = "Dados do usuário não encontrados."
                }
            } ?: run {
                errorMessage = "Usuário não autenticado!"
            }
        } catch (e: Exception) {
            errorMessage = "Erro ao carregar os dados: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { TopAppBar(state) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    errorMessage.isNotEmpty() -> ErrorScreen(message = errorMessage)
                    else -> ConteudoMeusAcessos(
                        email = email,
                        onEmailChange = { email = it },
                        onSaveClick = {
                            usuarioId?.let { id ->
                                clienteViewModel.atualizarEmail(id, email, onSuccess = {
                                    successMessage = "E-mail atualizado com sucesso!"
                                }, onError = { error ->
                                    errorMessage = "Erro ao atualizar o e-mail: $error"
                                })
                            }
                        },
                        successMessage = successMessage
                    )
                }
            }
        }
    )
}


@Composable
fun ConteudoMeusAcessos(
    email: String,
    onEmailChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    successMessage: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Meus Acessos", fontSize = 20.sp)

        // Campo de E-mail
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange, // Permite edição do e-mail
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )

        if (successMessage.isNotEmpty()) {
            Text(
                text = successMessage,
                color = Color.Green,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Botão para salvar alterações
        Button(
            onClick = onSaveClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.principal),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Salvar Alterações")
        }
    }
}


@Composable
fun ErrorScreen(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Ação de retry se necessário */ }) {
            Text("Tentar novamente")
        }
    }
}
