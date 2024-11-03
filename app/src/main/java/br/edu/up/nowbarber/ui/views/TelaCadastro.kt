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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.up.nowbarber.R


@Composable
fun TelaCadastro(navController: NavController, onRegisterSuccess: () -> Unit) {
    // Variáveis de estado para armazenar nome, email, senha e a mensagem de erro
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
                    painter = painterResource(id = R.drawable.logo3), // Substitua pelo nome da sua imagem
                    contentDescription = "Logotipo",
                    modifier = Modifier
                        .size(200.dp) // Tamanho do logotipo
                        .padding(bottom = 16.dp) // Espaçamento abaixo do logotipo
                )

                // Título
                Text(
                    text = "Criar Conta",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Campo de Nome
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Senha
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

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
                        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                            errorMessage = "" // Limpa a mensagem de erro
                            onRegisterSuccess() // Chama a função de sucesso ao cadastrar
                        } else {
                            errorMessage = "Preencha todos os campos" // Exibe mensagem de erro
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.principal), // Cor de fundo do botão
                        contentColor = Color.White // Cor do texto do botão
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botão de Voltar
                TextButton(
                    onClick = {
                        // Navegar de volta para a tela de login
                        navController.navigate("login") {
                            popUpTo("cadastro") { inclusive = true }
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
