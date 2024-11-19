package com.example.aplikacijazanamizneigre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplikacijazanamizneigre.ui.theme.AplikacijaZaNamizneIGreTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import android.content.Intent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iskanje")
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Priporočila")
    }
}
