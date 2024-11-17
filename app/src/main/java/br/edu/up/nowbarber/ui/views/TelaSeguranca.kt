package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.TopAppBar

import br.edu.up.nowbarber.ui.viewmodels.SessionViewModel


@Composable
fun TelaSeguranca(
    state: DrawerState,
    sessionViewModel: SessionViewModel = viewModel()
) {
    // Obtendo o ID do usuário logado
    var usuarioId = sessionViewModel.usuarioId.collectAsState().value

    // Variáveis locais para senhas
    var senhaAtual by remember { mutableStateOf("") }
    var novaSenha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }

    // Observe as mensagens de erro
    val errorMessage by sessionViewModel.errorMessage.observeAsState()

    // Scaffold para a tela com o formulário de segurança
    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p ->
            errorMessage?.let {
                ConteudoTelaSeguranca(
                    modifier = Modifier.padding(p),
                    senhaAtual = senhaAtual,
                    novaSenha = novaSenha,
                    confirmarSenha = confirmarSenha,
                    senhaVisivel = senhaVisivel,
                    onSaveClick = {
                        if (novaSenha == confirmarSenha) {
                            sessionViewModel.atualizarSenha(senhaAtual, novaSenha)
                        } else {
                            sessionViewModel.setErrorMessage("As senhas não coincidem!")
                        }
                    },
                    it
                )
            }
        }
    )
}



@Composable
fun ConteudoTelaSeguranca(
    modifier: Modifier,
    senhaAtual: String,
    novaSenha: String,
    confirmarSenha: String,
    senhaVisivel: Boolean,
    onSaveClick: () -> Unit,
    errorMessage: String
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
            onValueChange = { senhaAtual != it },
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
            onValueChange = { novaSenha != it },
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
            onValueChange = { confirmarSenha != it },
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

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = onSaveClick,
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
