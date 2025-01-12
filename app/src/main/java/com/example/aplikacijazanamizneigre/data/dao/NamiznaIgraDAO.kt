package com.example.aplikacijazanamizneigre.data.dao

import androidx.room.*
import com.example.aplikacijazanamizneigre.data.models.NamiznaIgra
import kotlinx.coroutines.flow.Flow

@Dao
interface NamiznaIgraDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun dodajIgro(game: NamiznaIgra)

    @Query("SELECT * FROM namizne_igre")
    fun getIgre(): Flow<List<NamiznaIgra>>

    @Query("SELECT * FROM namizne_igre WHERE id = :id LIMIT 1")
    suspend fun getIgraById(id: Int): NamiznaIgra?

    @Delete
    suspend fun izbrisiIgro(game: NamiznaIgra)

    @Update
    suspend fun posodobiIgro(game: NamiznaIgra)
}
