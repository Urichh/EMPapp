package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplikacijazanamizneigre.viewmodel.AppViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {

    val appViewModel: AppViewModel = viewModel()

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
                PriporocilaScreen(appViewModel = appViewModel)
            }
        }
    }
}
