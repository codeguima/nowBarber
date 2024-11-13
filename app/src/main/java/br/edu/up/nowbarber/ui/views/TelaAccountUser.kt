package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel


@Composable
fun TelaAccountUser(state: DrawerState, usuarioId: Int) {
    val viewModel: ClienteViewModel = viewModel() // Supondo que o ViewModel seja UserViewModel
    var nomeCompleto by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }

    // Usando LaunchedEffect para carregar os dados do usuário quando o usuarioId mudar
    LaunchedEffect(usuarioId) {
        usuarioId?.let {
            // Buscar os dados do usuário de forma assíncrona
            val usuario = viewModel.buscarPorId(usuarioId)
            usuario?.let {
                nomeCompleto = it.nome
                celular = it.telefone
                dataNascimento = it.dataNascimento
                genero = it.genero
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoAccountUser(Modifier.padding(p), nomeCompleto, celular, dataNascimento, genero) }
    )
}

@Composable
fun ConteudoAccountUser(
    modifier: Modifier,
    nomeCompleto: String,
    celular: String,
    dataNascimento: String,
    genero: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Minhas Conta", fontSize = 20.sp)

        // Campos para exibir e editar os dados do usuário
        OutlinedTextField(
            value = nomeCompleto,
            onValueChange = { /* Atualizar o nome */ },
            label = { Text("Nome Completo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = celular,
            onValueChange = { /* Atualizar o celular */ },
            label = { Text("Celular") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dataNascimento,
            onValueChange = { /* Atualizar a data de nascimento */ },
            label = { Text("Data de Nascimento") },
            modifier = Modifier.fillMaxWidth()
        )
        // Gênero
        Text(text = "Gênero", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderButton(
                gender = "Masculino",
                selectedGender = genero,
                onClick = { genero != "Masculino" }
            )
            GenderButton(
                gender = "Feminino",
                selectedGender = genero,
                onClick = { genero != "Feminino" }
            )
            GenderButton(
                gender = "Outro",
                selectedGender = genero,
                onClick = { genero != "Outro" }
            )
        }
        // Botão Salvar
        Button(
            onClick = { /* Implementar ação de salvar */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.principal), // Cor de fundo do botão
                contentColor = Color.White // Cor do texto do botão
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Salvar")
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
