package com.example.aplikacijazanamizneigre.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.aplikacijazanamizneigre.data.dao.NamiznaIgraDAO
import com.example.aplikacijazanamizneigre.data.dao.SeznamZeljaDAO
import com.example.aplikacijazanamizneigre.data.models.NamiznaIgra
import com.example.aplikacijazanamizneigre.data.models.SeznamZelja
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(
    entities = [NamiznaIgra::class, SeznamZelja::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameDao(): NamiznaIgraDAO
    abstract fun wishlistDao(): SeznamZeljaDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "igre_db"
                ).fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(CoroutineScope(Dispatchers.IO)))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val games = database.gameDao().getIgre().first()
                    if (games.isEmpty()) {
                        populateDatabase(database.gameDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(gameDao: NamiznaIgraDAO) {
            gameDao.dodajIgro(NamiznaIgra(
                id = 0,
                igra = "Catan",
                zanr = "Strategija",
                zahtevnost = "Srednja",
                cena = 35.99,
                minIgralcev = 2,
                maxIgralcev = 4,
                opis = "Catan is a popular strategy game where players collect resources to build roads, settlements, and cities to dominate the island of Catan.",
                slikaURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRJe6Wc_UmPWE3ag9bD_GWkOxoqOPcRucEZw&s"
            ))

            gameDao.dodajIgro(NamiznaIgra(
                id = 0,
                igra = "Ticket to Ride",
                zanr = "Strategija",
                zahtevnost = "Lahka",
                cena = 29.99,
                minIgralcev = 2,
                maxIgralcev = 5,
                opis = "Ticket to Ride is a railway-themed board game where players collect train cards to claim routes across a map of North America.",
                slikaURL = "https://www.pravijunak.si/cdn/shop/files/days-of-wonder-ticket-to-ride-europe-english-board-game-3d-cover_59d2e6aa-ea8b-4607-acf7-f70694d7c08c_1024x1024@2x.jpg?v=1699044526"
            ))

            gameDao.dodajIgro(NamiznaIgra(
                id = 0,
                igra = "Pandemic",
                zanr = "Sodelujoca",
                zahtevnost = "Tezka",
                cena = 39.99,
                minIgralcev = 2,
                maxIgralcev = 4,
                opis = "Pandemic is a cooperative board game where players must work together to stop the spread of deadly diseases across the globe.",
                slikaURL = "https://m.media-amazon.com/images/I/811YPz8YufL._AC_UF894,1000_QL80_.jpg"
            ))

            gameDao.dodajIgro(NamiznaIgra(
                id = 0,
                igra = "Carcassonne",
                zanr = "Postavitev polj",
                zahtevnost = "Srednja",
                cena = 24.99,
                minIgralcev = 2,
                maxIgralcev = 5,
                opis = "Carcassonne is a tile-placement game where players build cities, roads, and fields in medieval France and score points based on their completed features.",
                slikaURL = "https://cdn.svc.asmodee.net/production-zman/uploads/2024/08/ZM7810_box-right.png"
            ))

            gameDao.dodajIgro(NamiznaIgra(
                id = 0,
                igra = "Sah",
                zanr = "Strategija",
                zahtevnost = "Tezka",
                cena = 9.99,
                minIgralcev = 2,
                maxIgralcev = 2,
                opis = "Chess is a classic two-player strategy board game where players move their pieces to checkmate the opponent's king.",
                slikaURL = "https://mir-s3-cdn-cf.behance.net/projects/404/0aaf1859667123.Y3JvcCwxMTAyLDg2MywwLDI5.jpg"
            ))
        }
    }
}
