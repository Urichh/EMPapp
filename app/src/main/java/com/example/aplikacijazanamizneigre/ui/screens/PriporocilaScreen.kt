package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aplikacijazanamizneigre.data.models.NamiznaIgra
import com.example.aplikacijazanamizneigre.ui.components.DropdownMenuZNapisom

@Composable
fun PriporocilaScreen() {

    //hardcodane igre
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
    val rezultatiIskanja = remember { mutableStateOf(igre) }

    //dropdown moznosti
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
        TextField(
            value = cenaQuery.value,
            onValueChange = { cenaQuery.value = it },
            label = { Text("Max cena") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.DarkGray
            )
        )

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
                rezultatiIskanja.value = igre.filter { igra ->
                    (cenaQuery.value.isEmpty() || igra.cena <= (cenaQuery.value.toDoubleOrNull() ?: Double.MAX_VALUE)) &&
                            (izbranZanr.value == "Vse" || izbranZanr.value == igra.zanr) &&
                            (izbranaZahtevnost.value == "Vse" || izbranaZahtevnost.value == igra.zahtevnost) &&
                            (izbranoStIgralcev.value == "Vse" || izbranoStIgralcev.value.toIntOrNull() == igra.steviloIgralcev)
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
                    text = "${game.igra} - Žanr: ${game.zanr} - Zahtevnost: ${game.zahtevnost} - Max. St. Igralcev: ${game.steviloIgralcev} - \$${game.cena}",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

