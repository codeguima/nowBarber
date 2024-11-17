package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel


@Composable
fun TelaAccountUser(
    state: DrawerState,
    sessionViewModel: SessionViewModel = viewModel(),
    clienteViewModel: ClienteViewModel = viewModel()
) {

    val usuarioId by sessionViewModel.usuarioId.collectAsState()


    var cliente by remember { mutableStateOf<Cliente?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(usuarioId) {
        isLoading = true
        cliente = clienteViewModel.buscarPorId(usuarioId.toString())
        isLoading = false
    }

    Scaffold(
        topBar = { TopAppBar(state) },
        content = { padding ->
            when {
                isLoading -> LoadingScreen()
                cliente == null -> UsuarioNaoAutenticadoScreen()
                else -> {
                    if (isCadastroCompleto(cliente)) {
                        TelaDadosCliente(
                            modifier = Modifier.padding(padding),
                            cliente = cliente!!,
                            onSaveClick = { clienteViewModel.gravar(it) }
                        )
                    } else {
                        TelaCadastroIncompleto(
                            modifier = Modifier.padding(padding),
                            cliente = cliente!!,
                            onSaveClick = { clienteViewModel.gravar(it) }
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun UsuarioNaoAutenticadoScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Usuário não autenticado!")
    }
}

fun isCadastroCompleto(cliente: Cliente?): Boolean {
    return !cliente?.nome.isNullOrEmpty() &&
            !cliente?.telefone.isNullOrEmpty() &&
            !cliente?.dataNascimento.isNullOrEmpty() &&
            !cliente?.genero.isNullOrEmpty()
}


@Composable
fun TelaCadastroIncompleto(
    modifier: Modifier = Modifier,
    cliente: Cliente,
    onSaveClick: (Cliente) -> Unit
) {
    TelaDadosCliente(
        modifier = modifier,
        cliente = cliente,
        onSaveClick = onSaveClick,
        titulo = "Complete seu Cadastro"
    )
}


@Composable
fun TelaDadosCliente(
    modifier: Modifier = Modifier,
    cliente: Cliente,
    onSaveClick: (Cliente) -> Unit,
    titulo: String = "Minha Conta"
) {
    var nome by remember { mutableStateOf(cliente.nome) }
    var celular by remember { mutableStateOf(cliente.telefone) }
    var dataNascimento by remember { mutableStateOf(cliente.dataNascimento) }
    var genero by remember { mutableStateOf(cliente.genero) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = titulo, fontSize = 20.sp)

        // Campos de entrada
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome Completo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = celular,
            onValueChange = { celular = it },
            label = { Text("Celular") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dataNascimento,
            onValueChange = { dataNascimento = it },
            label = { Text("Data de Nascimento") },
            modifier = Modifier.fillMaxWidth()
        )

        // Gênero
        Text(text = "Gênero", fontSize = 18.sp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderButton("Masculino", genero) { genero = "Masculino" }
            GenderButton("Feminino", genero) { genero = "Feminino" }
            GenderButton("Outro", genero) { genero = "Outro" }
        }

        // Botão de salvar
        Button(
            onClick = {
                onSaveClick(
                    cliente.copy(
                        nome = nome,
                        telefone = celular,
                        dataNascimento = dataNascimento,
                        genero = genero
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.principal),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }
    }
}

@Composable
fun GenderButton(gender: String, selectedGender: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (gender == selectedGender)
                colorResource(id = R.color.principal)
            else MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier.width(100.dp)
    ) {
        Text(text = gender)
    }
}
