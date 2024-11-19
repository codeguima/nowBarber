package br.edu.up.nowbarber.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.navigation.NavController
import br.edu.up.nowbarber.R
import br.edu.up.nowbarber.ui.components.BarbeiroItem
import br.edu.up.nowbarber.ui.viewmodels.BarbeariaViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TelaSearchBarber(
    state: DrawerState,
    navController: NavController,
    barbeariaViewModel: BarbeariaViewModel

) {
    Scaffold(
        topBar = { br.edu.up.nowbarber.ui.components.TopAppBar(state) },
        content = { p -> ConteudoTelaSearchBarber(
            Modifier.padding(p),
            navController,
            barbeariaViewModel
        ) },
    )
}


@Composable
fun ConteudoTelaSearchBarber(
    modifier: Modifier,
    navController: NavController,
    barbeariaViewModel: BarbeariaViewModel
) {
    var searchText by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    val barbearias by barbeariaViewModel.barbearias.collectAsState()

    // Obtenha o nome do usuário logado
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: "Visitante"

    // Filtro das barbearias com base no texto e na cidade
    val filteredBarbearias = barbearias.filter {
        val matchesCity = selectedCity == null || it.cidade.equals(selectedCity, ignoreCase = true)
        val matchesSearch = it.nome.contains(searchText, ignoreCase = true) ||
                it.endereco.contains(searchText, ignoreCase = true) ||
                it.cidade.contains(searchText, ignoreCase = true)

        matchesCity && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Texto "Olá, [Nome do Usuário]" no topo
        Text(
            text = "Olá, $userName",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        // Caixa de Pesquisa arredondada
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Pesquisar...") },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .border(1.dp, Color.Gray, CircleShape)
                .clip(CircleShape)
        )

        // Dropdown de Cidades
        var expanded by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { expanded = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.principal),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationCity,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = selectedCity ?: "Selecione a Cidade")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenuItem(
                    text = { Text("Todas as Cidades") },
                    onClick = {
                        selectedCity = null
                        expanded = false
                    }
                )
                barbearias.map { it.cidade }
                    .distinct()
                    .forEach { cidade ->
                        DropdownMenuItem(
                            text = { Text(cidade) },
                            onClick = {
                                selectedCity = cidade
                                expanded = false
                            }
                        )
                    }
            }
        }

        // Exibe a lista de barbearias
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredBarbearias) { barbearia ->
                BarbeiroItem(barbearia, navController)
            }
        }
    }
}


