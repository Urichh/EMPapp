package com.example.aplikacijazanamizneigre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aplikacijazanamizneigre.ui.theme.AplikacijaZaNamizneIGreTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacijaZaNamizneIGreTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToIskanje = { navController.navigate("iskanje") },
                onNavigateToSeznamZelja = { navController.navigate("seznamZelja") },
                onNavigateToPriporocila = { navController.navigate("priporocila") },
                viewModel = appViewModel
            )
        }
        composable("iskanje") {
            IskanjeScreen()
        }
        composable("seznamZelja") {
            SeznamZeljaScreen()
        }
        composable("priporocila") {
            PriporocilaScreen()
        }
    }
}

@Composable
fun HomeScreen(
    onNavigateToIskanje: () -> Unit,
    onNavigateToSeznamZelja: () -> Unit,
    onNavigateToPriporocila: () -> Unit,
    viewModel: AppViewModel
) {
    val state = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state.value.welcomeMessage, modifier = Modifier.padding(bottom = 24.dp))

        Button(onClick = onNavigateToIskanje, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Iskanje")
        }
        Button(onClick = onNavigateToSeznamZelja, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Seznam želja")
        }
        Button(onClick = onNavigateToPriporocila, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Priporočila")
        }
    }
}

@Composable
fun IskanjeScreen() {
    val igraQuery = remember { mutableStateOf("") }
    val rezultatiIskanja = remember { mutableStateOf(emptyList<String>()) }

    val igre = listOf("Catan", "Carcassonne", "Ticket to Ride", "Pandemic", "Šah")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        TextField(
            value = igraQuery.value,
            onValueChange = { igraQuery.value = it },
            label = { Text("Igra") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                rezultatiIskanja.value = igre.filter { igra ->
                    igra.lowercase().contains(igraQuery.value.lowercase(), ignoreCase = true)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text("Išči")
        }

        if (rezultatiIskanja.value.isEmpty()) {
            Text("0 najdenih iger", modifier = Modifier.padding(top = 16.dp))
        } else {
            rezultatiIskanja.value.forEach { game ->
                Text(
                    text = game,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun DropdownMenuZNapisom(label: String, options: List<String>, selectedOption: MutableState<String>) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        //za odpret okno
        TextButton(onClick = { expanded.value = true }) {
            Text(text = selectedOption.value.ifEmpty { label })
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption.value = option
                        expanded.value = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}

@Composable
fun SeznamZeljaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Seznam Želja")
    }
}

@Composable
fun PriporocilaScreen() {
    data class NamiznaIgra(
        val igra: String,
        val zanr: String,
        val zahtevnost: String,
        val steviloIgralcev: Int,
        val cena: Double
    )

    //trenutno staticni seznam iger
    val igre = listOf(
        NamiznaIgra("Catan", "Strategija", "Srednja", 4, 35.0),
        NamiznaIgra("Carcassonne", "Postavitev polj", "Lahka", 5, 30.0),
        NamiznaIgra("Ticket to Ride", "Strategija", "Lahka", 5, 40.0),
        NamiznaIgra("Pandemic", "Sodelujoca", "Tezka", 4, 50.0),
        NamiznaIgra("Šah", "Abstraktna", "Tezka", 2, 20.0)
    )

    val cenaQuery = remember { mutableStateOf("") }
    val izbranZanr = remember { mutableStateOf("") }
    val izbranaZahtevnost = remember { mutableStateOf("") }
    val izbranoStIgralcev = remember { mutableStateOf("") }
    val rezultatiIskanja = remember { mutableStateOf(emptyList<NamiznaIgra>()) }

    // Dropdown menu options
    val zanri = listOf("Vse", "Strategija", "Postavitev polj", "Sodelujoca", "Abstraktna")
    val tezavnosti = listOf("Vse", "Lahka", "Srednja", "Tezka")
    val steviloIgralcevs = listOf("Vse", "2", "4", "5")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        //Vnos: max cena
        TextField(
            value = cenaQuery.value,
            onValueChange = { cenaQuery.value = it },
            label = { Text("Max cena") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        //dropdown: zanr
        DropdownMenuZNapisom(
            label = "Žanr",
            options = zanri,
            selectedOption = izbranZanr
        )

        //dropdown: zahtevnost
        DropdownMenuZNapisom(
            label = "Zahtevnost",
            options = tezavnosti,
            selectedOption = izbranaZahtevnost
        )

        //dropdown: max. st. igralcev
        DropdownMenuZNapisom(
            label = "Število igralcev",
            options = steviloIgralcevs,
            selectedOption = izbranoStIgralcev
        )

        Button(
            onClick = {
                rezultatiIskanja.value = igre.filter { igra ->
                    (cenaQuery.value.isEmpty() || igra.cena <= (cenaQuery.value.toDoubleOrNull() ?: Double.MAX_VALUE)) &&
                            (izbranZanr.value == "Vse" || izbranZanr.value == igra.zanr) &&
                            (izbranaZahtevnost.value == "Vse" || izbranaZahtevnost.value == igra.zahtevnost) &&
                            (izbranoStIgralcev.value == "Vse" || izbranoStIgralcev.value.toIntOrNull() == igra.steviloIgralcev)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ){ Text("Išči") }

        //filtriraj po rezultatih
        if (rezultatiIskanja.value.isEmpty()) {
            Text("0 najdenih iger", modifier = Modifier.padding(top = 16.dp))
        }
        else{
            rezultatiIskanja.value.forEach { game ->
                Text(
                    text = "${game.igra} - Žanr: ${game.zanr} - Zahtevnost: ${game.zahtevnost} - Max. St. Igralcev: ${game.steviloIgralcev} - \$${game.cena}",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
