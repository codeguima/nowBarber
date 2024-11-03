package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.TopAppBar

@Composable
fun TelaMeusAcessos(state: DrawerState) {
    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoMeusAcessos(Modifier.padding(p)) },
        //bottomBar = { BarberBottomBar() }
    )
}

@Composable
fun ConteudoMeusAcessos(modifier: Modifier) {
    var email by remember { mutableStateOf("usuario@exemplo.com") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Meus Acessos", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Campo de E-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Botão Salvar Edição
        Button(
            onClick = { /* Ação para salvar o e-mail */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.principal), // Cor de fundo do botão
                contentColor = Color.White // Cor do texto do botão
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Salvar Edição")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão Vincular com Google
        Button(
            onClick = { /* Ação para vincular com Google */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4)) // Azul do Google
        ) {
            Text(text = "Vincular com Google", color = Color.White)
        }

        // Botão Vincular com Facebook
        Button(
            onClick = { /* Ação para vincular com Facebook */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4267B2)) // Azul do Facebook
        ) {
            Text(text = "Vincular com Facebook", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaMeusAcessos() {
    TelaMeusAcessos(state = DrawerState(DrawerValue.Closed))
}
