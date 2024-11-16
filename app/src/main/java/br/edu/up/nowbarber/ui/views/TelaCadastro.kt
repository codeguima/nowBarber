package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel




@Composable
fun TelaCadastro(
    navController: NavController,
    clienteViewModel: ClienteViewModel,
    function: () -> Unit
) {
    // Estados do formulário
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorMessage by clienteViewModel.errorMessage.observeAsState()

    // Observa mudanças no status de login para navegação após cadastro
    val loginStatus by clienteViewModel.loginStatus.observeAsState()

    LaunchedEffect(loginStatus) {
        if (loginStatus == true) {
            navController.popBackStack() // Retorna para a tela anterior
            clienteViewModel.limparLoginStatus() // Limpa status de login
        }
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TelaCadastroContent(
                    email = emailState.value,
                    onEmailChange = { emailState.value = it },
                    password = passwordState.value,
                    onPasswordChange = { passwordState.value = it },
                    errorMessage = errorMessage ?: "",
                    onRegisterClick = {
                        clienteViewModel.cadastrarUsuario(emailState.value, passwordState.value)
                    }
                )
            }
        }
    )
}

@Composable
fun TelaCadastroContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorMessage: String,
    onRegisterClick: () -> Unit
) {
    Logo()
    WelcomeText("Cadastre-se")
    Spacer(modifier = Modifier.height(16.dp))
    EmailField(email, onEmailChange)
    Spacer(modifier = Modifier.height(16.dp))
    PasswordField(password, onPasswordChange)
    Spacer(modifier = Modifier.height(16.dp))
    ErrorMessage(errorMessage)
    Spacer(modifier = Modifier.height(16.dp))
    RegisterButton(onRegisterClick)
    Spacer(modifier = Modifier.height(16.dp))
    BackToLoginTextButton()
}

@Composable
fun RegisterButton(onRegisterClick: () -> Unit) {
    Button(
        onClick = onRegisterClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp) // Altura padrão para botões
    ) {
        Text(
            text = "Cadastrar",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun BackToLoginTextButton() {
    TextButton(onClick = { /* Navegar para a tela de login */ }) {
        Text(
            text = "Já possui uma conta? Faça login",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
