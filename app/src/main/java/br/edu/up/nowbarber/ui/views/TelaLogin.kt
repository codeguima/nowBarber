package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.navigation.TelaRotasBottom
import br.edu.up.nowbarber.ui.viewmodels.ClienteViewModel

@Composable
fun TelaLogin(
    navController: NavController,
    clienteViewModel: ClienteViewModel,
    onLoginSuccess: () -> Unit
) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorMessage by clienteViewModel.errorMessage.observeAsState()

    // Observa o status de login
    LaunchedEffect(clienteViewModel.loginStatus.value) {
        if (clienteViewModel.loginStatus.value == true) {
            onLoginSuccess() // Executa o callback para navegação
            clienteViewModel.limparLoginStatus() // Limpa o status
        }
    }

    // Tela de Login
    LoginScreen(
        email = emailState.value,
        onEmailChange = { emailState.value = it },
        password = passwordState.value,
        onPasswordChange = { passwordState.value = it },
        errorMessage = errorMessage ?: "",
        onLoginClick = {
            clienteViewModel.realizarLogin(emailState.value, passwordState.value, onLoginSuccess)
        },
        onRegisterClick = {
            navController.navigate(TelaRotasBottom.TelaCadastro)
        }
    )
}

@Composable
fun LoginScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorMessage: String,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
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
                Logo()
                WelcomeText("Cadastre-se")
                EmailField(email, onEmailChange)
                Spacer(modifier = Modifier.height(16.dp))
                PasswordField(password, onPasswordChange)
                Spacer(modifier = Modifier.height(16.dp))
                ErrorMessage(errorMessage ?: "")
                LoginButton(onLoginClick)
                RegisterTextButton(onRegisterClick)
            }
        }
    )
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.logo3),
        contentDescription = "Logotipo",
        modifier = Modifier
            .size(300.dp)
            .padding(bottom = 16.dp)
    )
}

@Composable
fun WelcomeText(s: String) {
    Text(
        text = "Bem-Vindo",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun EmailField(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Senha") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ErrorMessage(errorMessage: String) {
    if (errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun LoginButton(onLoginClick: () -> Unit) {
    Button(
        onClick = onLoginClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.principal),
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Entre")
    }
}

@Composable
fun RegisterTextButton(onRegisterClick: () -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    TextButton(onClick = onRegisterClick) {
        Text(
            color = Color.Blue,
            text = "Cadastre-se",
            fontSize = 12.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}
