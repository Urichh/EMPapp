package com.example.aplikacijazanamizneigre.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.aplikacijazanamizneigre.data.dao.NamiznaIgraDAO
import com.example.aplikacijazanamizneigre.data.dao.SeznamZeljaDAO
import com.example.aplikacijazanamizneigre.data.models.NamiznaIgra
import com.example.aplikacijazanamizneigre.data.models.SeznamZelja

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
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
