package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.ui.components.TopAppBar

@Composable
fun TelaInicio(state: DrawerState) {
    Scaffold(
        topBar = { TopAppBar(state) },
        content = { p -> ConteudoTelaInicio (Modifier.padding(p))

        }

    )
}

@Composable
fun ConteudoTelaInicio (modifier: Modifier){

    Column(
        modifier = modifier
            .fillMaxSize()

    ) {
        // Seção de Propagandas
        SectionPropagandas(modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.height(16.dp))

        // Seção Últimos Serviços Acessados
        SectionUltimosServicos(modifier = Modifier.weight(1f))
    }

}
@Composable
fun SectionPropagandas(modifier: Modifier = Modifier) {
    // Imagem de propaganda
    val propagandaImage: Painter = painterResource(id = R.drawable.propaganda)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = propagandaImage,
            contentDescription = "Imagem de Propaganda",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp) // Define uma altura mínima
                .clip(RoundedCornerShape(8.dp)), // Adiciona cantos arredondados se desejar
            contentScale = ContentScale.Crop // Ajusta a imagem para preencher a Box
        )
    }
}

@Composable
fun SectionUltimosServicos(modifier: Modifier = Modifier) {


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Últimos Serviços Acessados",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

    }
}

@Composable
fun UltimoServicoItem(servico: Servico) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagem do serviço
        servico.imageResId?.let { painterResource(it.toInt()) }?.let {
            Image(
                painter = it,
                contentDescription = servico.nome,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Nome e preço do serviço
        Column {
            Text(
                text = servico.nome,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "R$ ${servico.preco}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

