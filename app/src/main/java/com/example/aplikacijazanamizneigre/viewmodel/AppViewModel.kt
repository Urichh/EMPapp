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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val gameDao: NamiznaIgraDAO = AppDatabase.getDatabase(application).gameDao()
    private val wishlistDao: SeznamZeljaDAO = AppDatabase.getDatabase(application).wishlistDao()

    val allGames = gameDao.getIgre().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        viewModelScope.launch {
            val games = gameDao.getIgre().first()
            Log.d("AppViewModel", "Games in DB: $games")

            if (games.isEmpty()) {
                seedDatabase()
            }
        }
    }

    private fun seedDatabase() {
        viewModelScope.launch {
            val predefinedGames = listOf(
                NamiznaIgra(0, "Catan", "Strategija", "Srednja", 35.0, 2, 4, "igra Catan", "https://www.igraj.si/rails/active_storage/blobs/proxy/eyJfcmFpbHMiOnsiZGF0YSI6NjQ2LCJwdXIiOiJibG9iX2lkIn19--b00d8ed3c7715c5aea19a8b9c397775a5dbcb721/CATAN.jpg"),
                NamiznaIgra(0, "Carcassonne", "Postavitev polj", "Lahka", 50.0, 3, 6, "whatever", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8_eW_ICMVsVuZiR-gvQMpJ84qnL64_fLNMQ&s")
            )
            predefinedGames.forEach { game ->
                gameDao.dodajIgro(game)
            }
            Log.d("AppViewModel", "Database seeded with games: $predefinedGames")
        }
    }

    // Add game to wishlist
    fun addGameToWishlist(gameId: Int) {
        viewModelScope.launch {
            val game = gameDao.getIgraById(gameId)
            if (game != null) {
                val seznamZelja = SeznamZelja(idIgre = game.id, dodanDatum = getCurrentDate())
                wishlistDao.dodajZeljo(seznamZelja)
            }
        }
    }

    private fun getCurrentDate(): String {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return currentDate.format(Date())
    }
}
