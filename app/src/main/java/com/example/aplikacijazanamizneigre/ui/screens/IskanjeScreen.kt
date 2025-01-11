package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
