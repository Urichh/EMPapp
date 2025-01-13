package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aplikacijazanamizneigre.ui.components.DropdownMenuZNapisom

@Composable
fun PriporocilaScreen(
    navController: NavHostController,
    appViewModel: com.example.aplikacijazanamizneigre.viewmodel.AppViewModel
) {
    val seznamIger = appViewModel.vseIgre.collectAsState(initial = emptyList()).value

    val cenaQuery = remember { mutableStateOf("") }
    val izbranZanr = remember { mutableStateOf("") }
    val izbranaZahtevnost = remember { mutableStateOf("") }
    val izbranoStIgralcev = remember { mutableStateOf("") }
    val rezultatiIskanja = remember { mutableStateOf(seznamIger) }

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
        seznamIger.forEach { game ->
            Text(
                text = game.igra,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        navController.navigate("detajli/${game.id}")
                    }
            )
        }

        DropdownMenuZNapisom(
            label = "Žanr",
            options = zanri,
            izbrana = izbranZanr
        )

        DropdownMenuZNapisom(
            label = "Zahtevnost",
            options = tezavnosti,
            izbrana = izbranaZahtevnost
        )

        DropdownMenuZNapisom(
            label = "Število igralcev",
            options = steviloIgralcevs,
            izbrana = izbranoStIgralcev
        )

        Button(
            onClick = {
                rezultatiIskanja.value = seznamIger.filter { game ->
                    (cenaQuery.value.isEmpty() || game.cena <= (cenaQuery.value.toDoubleOrNull() ?: Double.MAX_VALUE)) &&
                            (izbranZanr.value == "Vse" || izbranZanr.value == game.zanr) &&
                            (izbranaZahtevnost.value == "Vse" || izbranaZahtevnost.value == game.zahtevnost) &&
                            (izbranoStIgralcev.value == "Vse" || izbranoStIgralcev.value.toInt() <= game.maxIgralcev)
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
                    text = "${game.igra} - Žanr: ${game.zanr} - Zahtevnost: ${game.zahtevnost} - Max. St. Igralcev: ${game.maxIgralcev} - \$${game.cena}",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}