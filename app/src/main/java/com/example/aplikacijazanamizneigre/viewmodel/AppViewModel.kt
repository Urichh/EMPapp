package com.example.aplikacijazanamizneigre.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.aplikacijazanamizneigre.data.AppDatabase
import com.example.aplikacijazanamizneigre.data.dao.NamiznaIgraDAO
import com.example.aplikacijazanamizneigre.data.dao.SeznamZeljaDAO
import com.example.aplikacijazanamizneigre.data.models.NamiznaIgra
import com.example.aplikacijazanamizneigre.data.models.SeznamZelja
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val gameDao: NamiznaIgraDAO = AppDatabase.getDatabase(application).gameDao()
    private val wishlistDao: SeznamZeljaDAO = AppDatabase.getDatabase(application).wishlistDao()

    // Get all games
    val allGames = gameDao.getIgre().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Add game to wishlist
    fun addGameToWishlist(gameId: Int) {
        viewModelScope.launch {
            val game = gameDao.getIgraById(gameId) // Safe call inside coroutine
            if (game != null) {
                val seznamZelja = SeznamZelja(idIgre = game.id, dodanDatum = getCurrentDate())
                wishlistDao.dodajZeljo(seznamZelja) // Safe call inside coroutine
            }
        }
    }

    private fun getCurrentDate(): String {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return currentDate.format(Date())
    }
}
