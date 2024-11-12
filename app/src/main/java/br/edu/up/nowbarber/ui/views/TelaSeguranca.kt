package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.TopAppBar


@Composable
fun TelaSeguranca(state: DrawerState) {
    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoTelaSeguranca(Modifier.padding(p)) },
    )
}

@Composable
fun ConteudoTelaSeguranca(modifier: Modifier) {
    var senhaAtual by remember { mutableStateOf("") }
    var novaSenha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Senhas de acesso", fontSize = 20.sp, modifier = Modifier.padding(bottom = 32.dp))

        // Campo de senha atual com visualização
        OutlinedTextField(
            value = senhaAtual,
            onValueChange = { senhaAtual = it },
            label = { Text("Senha atual") },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (senhaVisivel) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Campo de nova senha com visualização
        OutlinedTextField(
            value = novaSenha,
            onValueChange = { novaSenha = it },
            label = { Text("Nova senha") },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (senhaVisivel) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Campo de confirmar senha com visualização
        OutlinedTextField(
            value = confirmarSenha,
            onValueChange = { confirmarSenha = it },
            label = { Text("Confirmar nova senha") },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (senhaVisivel) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                // Lógica para salvar as senhas
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.principal), // Cor de fundo do botão
                contentColor = Color.White // Cor do texto do botão
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Salvar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaSeguranca() {
    TelaSeguranca(state = DrawerState(DrawerValue.Closed))
}
