package br.edu.up.nowbarber.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.data.models.Barbearia


@Composable
fun BarbeariaItem(barbearia: Barbearia, onFavoritoClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { /* Ação de clique para abrir detalhes */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Recuperando o nome do arquivo da imagem do Firebase
        val imageResName = barbearia.imageResId  // Ex: "logo1"

        // Usando getIdentifier para pegar o ID do drawable dinamicamente
        val context = LocalContext.current
        val imageResId = context.resources.getIdentifier(imageResName, "drawable", context.packageName)

        // Verificando se o recurso existe
        if (imageResId != 0) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = barbearia.nome,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        } else {
            // Se não encontrar o recurso, podemos exibir uma imagem padrão
            Image(
                painter = painterResource(id = R.drawable.semfoto),
                contentDescription = barbearia.nome,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = barbearia.nome, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = barbearia.endereco, fontSize = 14.sp, color = Color.Gray)
            Text(text = barbearia.cidade, fontSize = 14.sp, color = Color.Gray)
        }

        IconButton(onClick = { onFavoritoClick() }) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favoritar")
        }
    }
}
