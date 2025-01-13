package com.example.aplikacijazanamizneigre.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikacijazanamizneigre.data.AppDatabase
import com.example.aplikacijazanamizneigre.data.dao.NamiznaIgraDAO
import com.example.aplikacijazanamizneigre.data.dao.SeznamZeljaDAO
import com.example.aplikacijazanamizneigre.data.models.NamiznaIgra
import com.example.aplikacijazanamizneigre.data.models.SeznamZelja
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application) //maybe kdaj rabm? also - odstranu sm argument "viewModelScope"
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

    private val _rezultatiIskanja = MutableStateFlow<List<NamiznaIgra>>(emptyList())
    val rezultatiIskanja: StateFlow<List<NamiznaIgra>> = _rezultatiIskanja

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

    suspend fun getGameById(gameId: Int): NamiznaIgra? {
        return igreDAO.getIgraById(gameId)
    }

    fun searchGames(query: String) {
        viewModelScope.launch {
            try {
                val allGames = igreDAO.getIgre().first()
                _rezultatiIskanja.value = allGames.filter {
                    it.igra.contains(query, ignoreCase = true)
                }
            } catch (e: Exception) {
                _rezultatiIskanja.value = emptyList()
                Log.e("AppViewModel", "Napaka pri iskanju iger", e)
            }
        }
    }
}
