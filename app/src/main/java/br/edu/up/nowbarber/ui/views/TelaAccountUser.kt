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
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.TopAppBar


@Composable
fun TelaAccountUser(state: DrawerState) {
    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoAccountUser(Modifier.padding(p)) },

    )
}

@Composable
fun ConteudoAccountUser(modifier: Modifier) {
    var nomeCompleto by remember { mutableStateOf("João da Silva") }
    var celular by remember { mutableStateOf("(11) 98765-4321") }
    var dataNascimento by remember { mutableStateOf("01/01/1990") }
    var genero by remember { mutableStateOf("Masculino") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Minha Conta", fontSize = 20.sp)

        // Campo Nome Completo
        OutlinedTextField(
            value = nomeCompleto,
            onValueChange = { nomeCompleto = it },
            label = { Text("Nome Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo Celular
        OutlinedTextField(
            value = celular,
            onValueChange = { celular = it },
            label = { Text("Celular") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo Data de Nascimento
        OutlinedTextField(
            value = dataNascimento,
            onValueChange = { dataNascimento = it },
            label = { Text("Data de Nascimento") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo Gênero
        Text(text = "Gênero", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderButton(
                gender = "Masculino",
                selectedGender = genero,
                onClick = { genero = "Masculino" }
            )
            GenderButton(
                gender = "Feminino",
                selectedGender = genero,
                onClick = { genero = "Feminino" }
            )
            GenderButton(
                gender = "Outro",
                selectedGender = genero,
                onClick = { genero = "Outro" }
            )
        }

        // Botão Salvar
        Button(
            onClick = { /* Implementar ação de salvar */ }
            ,
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

@Preview(showBackground = true)
@Composable
fun PreviewTelaAccountUser() {
    TelaAccountUser(state = DrawerState(DrawerValue.Closed))
}
