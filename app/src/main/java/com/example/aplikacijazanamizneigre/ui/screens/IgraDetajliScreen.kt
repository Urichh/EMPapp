package com.example.aplikacijazanamizneigre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.aplikacijazanamizneigre.data.models.NamiznaIgra

@Composable
fun IgraDetajliScreen(igra: NamiznaIgra) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberAsyncImagePainter(igra.slikaURL)
        Image(
            painter = painter,
            contentDescription = "Slike igre",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Igra: ${igra.igra}", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Žanr: ${igra.zanr}")
        Text(text = "Zahtevnost: ${igra.zahtevnost}")
        Text(text = "Min igralci: ${igra.maxIgralcev}")
        Text(text = "Max igralci: ${igra.maxIgralcev}")
        Text(text = "Cena: $${igra.cena}")
    }
}
