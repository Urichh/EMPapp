package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(
                    onNavigateToIskanje = { navController.navigate("iskanje") },
                    onNavigateToSeznamZelja = { navController.navigate("seznamZelja") },
                    onNavigateToPriporocila = { navController.navigate("priporocila") }
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
}
