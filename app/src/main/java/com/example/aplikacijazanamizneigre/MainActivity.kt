package com.example.aplikacijazanamizneigre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.aplikacijazanamizneigre.ui.screens.AppNavHost
import com.example.aplikacijazanamizneigre.ui.theme.AplikacijaZaNamizneIGreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikacijaZaNamizneIGreTheme {
                Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){
                    AppNavHost()
                }
            }
        }
    }
}

