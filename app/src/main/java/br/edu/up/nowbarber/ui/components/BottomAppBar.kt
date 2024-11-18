package br.edu.up.nowbarber.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.edu.up.nowbarber.ui.navigation.TelaRotasBottom


@Composable
fun BottomAppBar(navController: NavController) {
    // Obter o destino atual da navegação
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Definir a rota atual
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        // Item para TelaInicio
        NavigationBarItem(
            selected = currentRoute == TelaRotasBottom.TelaInicio,
            onClick = {
                if (currentRoute != TelaRotasBottom.TelaInicio) {
                    navController.navigate(TelaRotasBottom.TelaInicio)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Inicio",
                    modifier = Modifier.size(40.dp)
                )
            },
            label = { Text(text = "Inicio") }
        )

        // Item para TelaSearchBarber
        NavigationBarItem(
            selected = currentRoute == TelaRotasBottom.TelaSearchBarber,
            onClick = {
                if (currentRoute != TelaRotasBottom.TelaSearchBarber) {
                    navController.navigate(TelaRotasBottom.TelaSearchBarber)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Pesquisar",
                    modifier = Modifier.size(40.dp)
                )
            },
            label = { Text(text = "Pesquisar") }
        )

        // Item para TelaAgendamento
        NavigationBarItem(
            selected = currentRoute == TelaRotasBottom.TelaMeusAgendamentos,
            onClick = {
                if (currentRoute != TelaRotasBottom.TelaMeusAgendamentos) {
                    navController.navigate(TelaRotasBottom.TelaMeusAgendamentos)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Agendamentos",
                    modifier = Modifier.size(40.dp)
                )
            },
            label = { Text(text = "Agendamento") }
        )
    }
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun BarberBottomBar() {
    BottomAppBar(
        containerColor = Color(0x52196F3)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Inicio",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Pesquisar",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Icon(
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = "Agendamento",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}