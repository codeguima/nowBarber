package br.edu.up.nowbarber.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.up.nowbarber.data.models.Servico


@Composable
fun ServicoItem(servico: Servico, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },  // Chama uma função regular no lugar de uma Composable
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(text = servico.nome, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                // Convertendo o preço para string (se for número)
                Text(text = "R$ ${servico.preco}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
