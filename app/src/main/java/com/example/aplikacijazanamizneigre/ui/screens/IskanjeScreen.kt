package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aplikacijazanamizneigre.viewmodel.AppViewModel

@Composable
fun IskanjeScreen(navController: NavHostController, appViewModel: AppViewModel) {
    val igraQuery = remember { mutableStateOf("") }
    val rezultatiIskanja = appViewModel.rezultatiIskanja.collectAsState(emptyList())

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                appViewModel.searchGames(igraQuery.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Išči")
        }

        if (rezultatiIskanja.value.isEmpty()) {
            Text("0 najdenih iger", modifier = Modifier.padding(top = 16.dp))
        } else {
            rezultatiIskanja.value.forEach { igra ->
                Text(
                    text = igra.igra,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable {
                            navController.navigate("detajli/${igra.id}")
                        }
                )
            }
        }
    }
}
