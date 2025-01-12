package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikacijazanamizneigre.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeznamZeljaScreen(appViewModel: AppViewModel = viewModel()) {
    val seznamIger by appViewModel.vseIgre.collectAsStateWithLifecycle(initialValue = emptyList())
    val seznamZelijZImenom by appViewModel.zeljeneIgreZImenom.collectAsStateWithLifecycle(initialValue = emptyList())

    var izbranaIgra by remember { mutableStateOf<String?>(null) }
    var dropdownRazsirjen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Seznam želja", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = dropdownRazsirjen,
            onExpandedChange = { dropdownRazsirjen = !dropdownRazsirjen }
        ) {
            TextField(
                value = izbranaIgra ?: "",
                onValueChange = {},
                label = { Text("Select Board Game") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownRazsirjen)
                },
                readOnly = true,
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = dropdownRazsirjen,
                onDismissRequest = { dropdownRazsirjen = false }
            ) {
                seznamIger.forEach { game ->
                    DropdownMenuItem(
                        text = { Text(text = game.igra) },
                        onClick = {
                            izbranaIgra = game.igra
                            dropdownRazsirjen = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                izbranaIgra?.let { gameName ->
                    val game = seznamIger.find { it.igra == gameName }
                    game?.let {
                        appViewModel.dodajIgroNaSeznamZelja(it.id)
                    }
                }
            },
            enabled = izbranaIgra != null
        ) {
            Text("Dodaj")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display wishlist games with names
        Text(text = "Seznam:", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        seznamZelijZImenom.forEach { (wishlistItem, gameName) ->
            Text(
                text = "$gameName - Added on: ${wishlistItem.dodanDatum}",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Clear wishlist button
        Button(
            onClick = {
                appViewModel.izprazniZelje()
            }
        ) {
            Text("Izprazni seznam želja")
        }
    }
}
