package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.TopAppBar

@Composable
fun TelaPayments(state: DrawerState) {
    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoDaPayments(Modifier.padding(p)) },

    )
}

@Composable
fun ConteudoDaPayments(modifier: Modifier) {
    var cardNumber by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Editar Cartões", fontSize = 30.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Campo de Número do Cartão
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Número do Cartão") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Nome do Titular
        OutlinedTextField(
            value = cardHolderName,
            onValueChange = { cardHolderName = it },
            label = { Text("Nome do Titular") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Data de Validade
        OutlinedTextField(
            value = expiryDate,
            onValueChange = { expiryDate = it },
            label = { Text("Data de Validade (MM/AA)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo CVV
        OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text("Código de Segurança (CVV)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de Salvar
        Button(
            onClick = {
                // Lógica para salvar as informações do cartão
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.principal), // Cor de fundo do botão
                contentColor = Color.White // Cor do texto do botão
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }
    }
}
