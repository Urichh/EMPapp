package com.example.aplikacijazanamizneigre.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.aplikacijazanamizneigre.data.models.NamiznaIgra
import com.example.aplikacijazanamizneigre.ui.components.GumbNazaj
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Callback
import java.io.IOException

@Composable
fun IgraDetajliScreen(navController: NavHostController, igra: NamiznaIgra) {
    val scrollState = rememberScrollState()
    var bliznjeTrgovine by remember { mutableStateOf<List<Store>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        getLocation(context) { location ->
            location?.let {
                Log.d("Lokacija", "Pridobljena lokacija: Lat = ${it.latitude}, Long = ${it.longitude}")
                poisciBliznjeTrgovine(it.latitude, it.longitude) { trgovine ->
                    bliznjeTrgovine = trgovine
                }
            } ?: Log.e("Lokacija", "Lokacija ni na voljo")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GumbNazaj(navController = navController, title = "Priporočila")

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

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = " ${igra.igra} ",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Žanr: ${igra.zanr}")
                Text(text = "Zahtevnost: ${igra.zahtevnost}")
                Text(text = "Min igralci: ${igra.maxIgralcev}")
                Text(text = "Max igralci: ${igra.maxIgralcev}")
                Text(text = "Cena: $${igra.cena}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (bliznjeTrgovine.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Trgovine z igrami v radiusu 5km:")
            bliznjeTrgovine.forEach { trgovina ->
                Column {
                    Text(
                        text = "${trgovina.name}; Koordinate: (${trgovina.lat}, ${trgovina.lon})",
                        modifier = Modifier
                            .clickable {
                                odpriVGmaps(context, trgovina.lat, trgovina.lon)
                            }
                            .padding(8.dp)
                    )
                    HorizontalDivider()
                }
            }
        } else {
            Log.d("Trgovine", "0 najdenih bližnjih trgovin")
        }
    }
}

private fun getLocation(context: android.content.Context, callback: (LatLng?) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    //preverjam če je user appu dal pravice za lokacijo
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Log.e("Lokacija", "Pravice niso bile dodeljene")
        callback(null)
        return
    }

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            Log.d("Lokacija", "Lokacija: Lat = ${it.latitude}, Long = ${it.longitude}")
            callback(LatLng(it.latitude, it.longitude))
        } ?: run {
            Log.e("Lokacija", "Lokacija ni na voljo")
            callback(null)
        }
    }
}

private fun poisciBliznjeTrgovine(latitude: Double, longitude: Double, callback: (List<Store>) -> Unit) {
    val apiUrl = "https://nominatim.openstreetmap.org/search?q=games&lat=$latitude&lon=$longitude&format=json&radius=50000&countrycodes=SI"

    val client = OkHttpClient()
    val request = Request.Builder()
        .url(apiUrl)
        .header("User-Agent", "Igralko/1.0")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val jsonResponse = response.body?.string() ?: ""
                Log.d("API", "API odgovor: $jsonResponse")
                parseJsonResponse(jsonResponse, callback)
            } else {
                Log.e("API_ERROR", " ${response.code}")
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            Log.e("API_ERROR", " ${e.message}")
        }
    })
}

private fun parseJsonResponse(jsonResponse: String, callback: (List<Store>) -> Unit) {
    val gson = Gson()
    val storeListType = object : TypeToken<List<Store>>() {}.type
    val stores: List<Store> = gson.fromJson(jsonResponse, storeListType)

    if (stores.isNotEmpty()) {
        Log.d("API", "Najdene trgovine: ${stores.size}")
    } else {
        Log.d("API", "Ni bilo najdenih trgovin.")
    }
    callback(stores)
}

data class Store(
    val name: String,
    val lat: String,
    val lon: String
)

private fun odpriVGmaps(context: android.content.Context, lat: String, lon: String) {
    val uri = "geo:$lat,$lon"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
    else {
        Log.e("Maps", "Google Maps ni nameščen")
    }
}
