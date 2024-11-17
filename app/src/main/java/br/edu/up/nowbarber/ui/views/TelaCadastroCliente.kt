package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.ui.navigation.TelaRotasBottom


@Composable
fun TelaCadastroCliente(
    navController: NavController,
    clienteViewModel: ClienteViewModel,
    onRegisterSuccess: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(color = Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logotipo no topo
                Image(
                    painter = painterResource(id = R.drawable.logo3),
                    contentDescription = "Logotipo",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp)
                )

                // Título
                Text(
                    text = "Criar Conta",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Campos do Formulário
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text("Telefone") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = dataNascimento,
                    onValueChange = { dataNascimento = it },
                    label = { Text("Data Nascimento") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    label = { Text("Gênero") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Exibir mensagem de erro, se houver
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Botão de Cadastro
                Button(
                    onClick = {
                        if (nome.isNotEmpty() && email.isNotEmpty() && telefone.isNotEmpty() && dataNascimento.isNotEmpty() && genero.isNotEmpty()) {
                            val cliente = Cliente(
                                nome = nome,
                                telefone = telefone,
                                dataNascimento = dataNascimento,
                                email = email,
                                genero = genero
                            )
                            clienteViewModel.gravar(cliente)
                            errorMessage = ""
                            onRegisterSuccess() // Sucesso no registro
                        } else {
                            errorMessage = "Preencha todos os campos"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.principal),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Voltar para a tela de Login
                TextButton(
                    onClick = {
                        navController.navigate(TelaRotasBottom.TelaLogin) {
                            popUpTo(TelaRotasBottom.TelaCadastroCliente) { inclusive = true }
                        }
                    }
                ) {
                    Text(
                        color = Color.Blue,
                        text = "Já tem uma conta? Faça login",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}
