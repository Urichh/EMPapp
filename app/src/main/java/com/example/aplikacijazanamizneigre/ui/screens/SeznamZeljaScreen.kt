package com.example.aplikacijazanamizneigre.ui

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
fun WishlistScreen(appViewModel: AppViewModel = viewModel()) {
    val gamesList by appViewModel.allGames.collectAsStateWithLifecycle(initialValue = emptyList())
    var selectedGame by remember { mutableStateOf<String?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Add to Wishlist", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown menu for selecting a game
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded }
        ) {
            TextField(
                value = selectedGame ?: "",
                onValueChange = {},
                label = { Text("Select Board Game") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                },
                readOnly = true,
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                gamesList.forEach { game ->
                    DropdownMenuItem(
                        text = { Text(text = game.igra) },
                        onClick = {
                            selectedGame = game.igra
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add to wishlist button
        Button(
            onClick = {
                selectedGame?.let { gameName ->
                    val game = gamesList.find { it.igra == gameName }
                    game?.let {
                        appViewModel.addGameToWishlist(it.id)
                    }
                }
            },
            enabled = selectedGame != null
        ) {
            Text("Add to Wishlist")
        }
    }
}
