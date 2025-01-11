package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToIskanje: () -> Unit,
    onNavigateToSeznamZelja: () -> Unit,
    onNavigateToPriporocila: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dobrodošli!", modifier = Modifier.padding(bottom = 24.dp))

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
