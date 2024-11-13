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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.TopAppBar
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel


@Composable
fun TelaSeguranca(state: DrawerState, clienteViewModel: ClienteViewModel, usuarioId: Int) {


    val viewModel: ClienteViewModel = viewModel()
    var senhaAtual by remember { mutableStateOf("") }
    var novaSenha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }

    // Usando LaunchedEffect para carregar as configurações de segurança
    LaunchedEffect(usuarioId) {
        usuarioId?.let {
            // Buscar as configurações de segurança do usuário de forma assíncrona
            val configuracoesSeguranca = viewModel.buscarPorId(usuarioId)
            configuracoesSeguranca?.let {
                // Carregar as informações da configuração de segurança
                senhaAtual = it.senha
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoTelaSeguranca(Modifier.padding(p), senhaAtual, novaSenha, confirmarSenha, senhaVisivel) }
    )
}

@Composable
fun ConteudoTelaSeguranca(
    modifier: Modifier,
    senhaAtual: String,
    novaSenha: String,
    confirmarSenha: String,
    senhaVisivel: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Senhas de Acesso", fontSize = 20.sp, modifier = Modifier.padding(bottom = 32.dp))

        // Campos de senha
        OutlinedTextField(
            value = senhaAtual,
            onValueChange = { /* Atualizar senha atual */ },
            label = { Text("Senha atual") },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (senhaVisivel) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { senhaVisivel != senhaVisivel }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = novaSenha,
            onValueChange = { /* Atualizar nova senha */ },
            label = { Text("Nova senha") },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (senhaVisivel) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { senhaVisivel != senhaVisivel }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = confirmarSenha,
            onValueChange = { /* Atualizar confirmar senha */ },
            label = { Text("Confirmar nova senha") },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (senhaVisivel) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { senhaVisivel != senhaVisivel }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Button(
            onClick = { /* Lógica para salvar as senhas */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.principal),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Salvar")
        }
    }
}
