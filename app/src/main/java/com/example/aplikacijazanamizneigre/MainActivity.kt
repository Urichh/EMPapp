package com.example.aplikacijazanamizneigre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.aplikacijazanamizneigre.ui.screens.AppNavHost
import com.example.aplikacijazanamizneigre.ui.theme.AplikacijaZaNamizneIGreTheme

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
