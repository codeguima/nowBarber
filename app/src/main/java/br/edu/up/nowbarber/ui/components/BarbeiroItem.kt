package br.edu.up.nowbarber.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.data.models.Barbearia

@Composable
fun BarbeiroItem(barbearia: Barbearia, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {navController.navigate("servicos/${barbearia.id}")
          },
        verticalAlignment = Alignment.CenterVertically
    ) {

        val imageResId = try {
            barbearia.image.toInt()
        } catch (e: NumberFormatException) {

            R.drawable.semfoto
        }

        Image(
            painter = painterResource(imageResId),
            contentDescription = barbearia.nome,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f) 
        ) {
            Text(
                text = barbearia.nome,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = barbearia.endereco,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = barbearia.cidade,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
