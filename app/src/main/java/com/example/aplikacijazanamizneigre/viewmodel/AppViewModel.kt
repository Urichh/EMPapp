package com.example.aplikacijazanamizneigre.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikacijazanamizneigre.data.AppDatabase
import com.example.aplikacijazanamizneigre.data.dao.NamiznaIgraDAO
import com.example.aplikacijazanamizneigre.data.dao.SeznamZeljaDAO
import com.example.aplikacijazanamizneigre.data.models.SeznamZelja
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val igreDAO: NamiznaIgraDAO = AppDatabase.getDatabase(application).gameDao()
    private val zeljeDAO: SeznamZeljaDAO = AppDatabase.getDatabase(application).wishlistDao()

    val vseIgre = igreDAO.getIgre().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val zeljeneIgre = zeljeDAO.getZelje().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val zeljeneIgreZImenom = zeljeneIgre.map { wishlistItems ->
        wishlistItems.mapNotNull { wishlistItem ->
            val kaj = igreDAO.getIgraById(wishlistItem.idIgre)
            kaj?.let {
                wishlistItem to it.igra
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        viewModelScope.launch {
            val games = igreDAO.getIgre().first()
            Log.d("AppViewModel", "igre u db: $games")
        }
    }

    fun dodajIgroNaSeznamZelja(gameId: Int) {
        viewModelScope.launch {
            val game = igreDAO.getIgraById(gameId)
            if (game != null) {
                val seznamZelja = SeznamZelja(idIgre = game.id, dodanDatum = getCurrentDate())
                zeljeDAO.dodajZeljo(seznamZelja)
            }
        }
    }

    private fun getCurrentDate(): String {
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return currentDate.format(Date())
    }

    fun izprazniZelje() {
        viewModelScope.launch {
            zeljeDAO.izprazniZelje()
        }
    }
}
